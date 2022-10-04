/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.Logger;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.impl.LocationAware;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.layout.Encoder;
import org.apache.logging.log4j.core.layout.PatternSelector;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.Charset;
import java.util.*;

@Plugin(name = "CustomLayout", category = "Core", elementType = "layout", printObject = true)
public class CustomLayout extends AbstractStringLayout {
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %notEmpty{%x }- %m%n";
    public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
    public static final String KEY = "Converter";
    private final String conversionPattern;
    private final PatternSelector patternSelector;
    private final AbstractStringLayout.Serializer eventSerializer;

    private CustomLayout(Configuration config, RegexReplacement replace, String eventPattern,
        PatternSelector patternSelector, Charset charset, boolean alwaysWriteExceptions, boolean disableAnsi,
        boolean noConsoleNoAnsi, String headerPattern, String footerPattern) {
        super(config, charset,
            newSerializerBuilder().setConfiguration(config).setReplace(replace).setPatternSelector(patternSelector)
                .setAlwaysWriteExceptions(alwaysWriteExceptions).setDisableAnsi(disableAnsi)
                .setNoConsoleNoAnsi(noConsoleNoAnsi).setPattern(headerPattern).build(),
            newSerializerBuilder().setConfiguration(config).setReplace(replace).setPatternSelector(patternSelector)
                .setAlwaysWriteExceptions(alwaysWriteExceptions).setDisableAnsi(disableAnsi)
                .setNoConsoleNoAnsi(noConsoleNoAnsi).setPattern(footerPattern).build());
        this.conversionPattern = eventPattern;
        this.patternSelector = patternSelector;
        this.eventSerializer =
            newSerializerBuilder().setConfiguration(config).setReplace(replace).setPatternSelector(patternSelector)
                .setAlwaysWriteExceptions(alwaysWriteExceptions).setDisableAnsi(disableAnsi)
                .setNoConsoleNoAnsi(noConsoleNoAnsi).setPattern(eventPattern).setDefaultPattern("%m%n").build();
    }

    public static CustomLayout.SerializerBuilder newSerializerBuilder() {
        return new CustomLayout.SerializerBuilder();
    }

    public boolean requiresLocation() {
        return this.eventSerializer instanceof LocationAware
            && ((LocationAware) this.eventSerializer).requiresLocation();
    }

    /** @deprecated */
    @Deprecated
    public static AbstractStringLayout.Serializer createSerializer(Configuration configuration,
        RegexReplacement replace, String pattern, String defaultPattern, PatternSelector patternSelector,
        boolean alwaysWriteExceptions, boolean noConsoleNoAnsi) {
        CustomLayout.SerializerBuilder builder = newSerializerBuilder();
        builder.setAlwaysWriteExceptions(alwaysWriteExceptions);
        builder.setConfiguration(configuration);
        builder.setDefaultPattern(defaultPattern);
        builder.setNoConsoleNoAnsi(noConsoleNoAnsi);
        builder.setPattern(pattern);
        builder.setPatternSelector(patternSelector);
        builder.setReplace(replace);
        return builder.build();
    }

    public String getConversionPattern() {
        return this.conversionPattern;
    }

    public Map<String, String> getContentFormat() {
        Map<String, String> result = new HashMap();
        result.put("structured", "false");
        result.put("formatType", "conversion");
        result.put("format", this.conversionPattern);
        return result;
    }

    public String toSerializable(LogEvent event) {
        return this.eventSerializer.toSerializable(event);
    }

    public void serialize(LogEvent event, StringBuilder stringBuilder) {
        this.eventSerializer.toSerializable(event, stringBuilder);
    }

    public void encode(LogEvent event, ByteBufferDestination destination) {
        if (!(this.eventSerializer instanceof AbstractStringLayout.Serializer2)) {
            super.encode(event, destination);
        } else {
            StringBuilder text =
                this.toText((AbstractStringLayout.Serializer2) this.eventSerializer, event, getStringBuilder());
            Encoder<StringBuilder> encoder = this.getStringBuilderEncoder();
            encoder.encode(text, destination);
            trimToMaxSize(text);
        }
    }

    private StringBuilder toText(AbstractStringLayout.Serializer2 serializer, LogEvent event,
        StringBuilder destination) {
        return serializer.toSerializable(event, destination);
    }

    public static PatternParser createPatternParser(Configuration config) {
        if (config == null) {
            return new PatternParser(config, "Converter", LogEventPatternConverter.class);
        } else {
            PatternParser parser = (PatternParser) config.getComponent("Converter");
            if (parser == null) {
                parser = new PatternParser(config, "Converter", LogEventPatternConverter.class);
                config.addComponent("Converter", parser);
                parser = (PatternParser) config.getComponent("Converter");
            }

            return parser;
        }
    }

    public String toString() {
        return this.patternSelector == null ? this.conversionPattern : this.patternSelector.toString();
    }

    /** @deprecated */
    @PluginFactory
    @Deprecated
    public static CustomLayout createLayout(@PluginAttribute(value = "pattern", defaultString = "%m%n") String pattern,
        @PluginElement("PatternSelector") PatternSelector patternSelector, @PluginConfiguration Configuration config,
        @PluginElement("Replace") RegexReplacement replace, @PluginAttribute("charset") Charset charset,
        @PluginAttribute(value = "alwaysWriteExceptions", defaultBoolean = true) boolean alwaysWriteExceptions,
        @PluginAttribute("noConsoleNoAnsi") boolean noConsoleNoAnsi, @PluginAttribute("header") String headerPattern,
        @PluginAttribute("footer") String footerPattern) {
        return newBuilder().withPattern(pattern).withPatternSelector(patternSelector).withConfiguration(config)
            .withRegexReplacement(replace).withCharset(charset).withAlwaysWriteExceptions(alwaysWriteExceptions)
            .withNoConsoleNoAnsi(noConsoleNoAnsi).withHeader(headerPattern).withFooter(footerPattern).build();
    }

    public static CustomLayout createDefaultLayout() {
        return newBuilder().build();
    }

    public static CustomLayout createDefaultLayout(Configuration configuration) {
        return newBuilder().withConfiguration(configuration).build();
    }

    @PluginBuilderFactory
    public static CustomLayout.Builder newBuilder() {
        return new CustomLayout.Builder();
    }

    public AbstractStringLayout.Serializer getEventSerializer() {
        return this.eventSerializer;
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<CustomLayout> {
        @PluginBuilderAttribute
        private String pattern;
        @PluginElement("PatternSelector")
        private PatternSelector patternSelector;
        @PluginConfiguration
        private Configuration configuration;
        @PluginElement("Replace")
        private RegexReplacement regexReplacement;
        @PluginBuilderAttribute
        private Charset charset;
        @PluginBuilderAttribute
        private boolean alwaysWriteExceptions;
        @PluginBuilderAttribute
        private boolean disableAnsi;
        @PluginBuilderAttribute
        private boolean noConsoleNoAnsi;
        @PluginBuilderAttribute
        private String header;
        @PluginBuilderAttribute
        private String footer;

        private Builder() {
            this.pattern = "%m%n";
            this.charset = Charset.defaultCharset();
            this.alwaysWriteExceptions = true;
            this.disableAnsi = !this.useAnsiEscapeCodes();
        }

        private boolean useAnsiEscapeCodes() {
            PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
            boolean isPlatformSupportsAnsi = !propertiesUtil.isOsWindows();
            boolean isJansiRequested = !propertiesUtil.getBooleanProperty("log4j.skipJansi", true);
            return isPlatformSupportsAnsi || isJansiRequested;
        }

        public CustomLayout.Builder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public CustomLayout.Builder withPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public CustomLayout.Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public CustomLayout.Builder withRegexReplacement(RegexReplacement regexReplacement) {
            this.regexReplacement = regexReplacement;
            return this;
        }

        public CustomLayout.Builder withCharset(Charset charset) {
            if (charset != null) {
                this.charset = charset;
            }

            return this;
        }

        public CustomLayout.Builder withAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        public CustomLayout.Builder withDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        public CustomLayout.Builder withNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }

        public CustomLayout.Builder withHeader(String header) {
            this.header = header;
            return this;
        }

        public CustomLayout.Builder withFooter(String footer) {
            this.footer = footer;
            return this;
        }

        public CustomLayout build() {
            if (this.configuration == null) {
                this.configuration = new DefaultConfiguration();
            }

            return new CustomLayout(this.configuration, this.regexReplacement, this.pattern, this.patternSelector,
                this.charset, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi, this.header,
                this.footer);
        }
    }

    private static class PatternSelectorSerializer
        implements AbstractStringLayout.Serializer, AbstractStringLayout.Serializer2, LocationAware {
        private final PatternSelector patternSelector;
        private final RegexReplacement replace;

        private PatternSelectorSerializer(PatternSelector patternSelector, RegexReplacement replace) {
            this.patternSelector = patternSelector;
            this.replace = replace;
        }

        public String toSerializable(LogEvent event) {
            StringBuilder sb = AbstractStringLayout.getStringBuilder();

            String var3;
            try {
                var3 = this.toSerializable(event, sb).toString();
            } finally {
                AbstractStringLayout.trimToMaxSize(sb);
            }

            return var3;
        }

        public StringBuilder toSerializable(LogEvent event, StringBuilder buffer) {
            PatternFormatter[] formatters = this.patternSelector.getFormatters(event);
            int len = formatters.length;

            for (int i = 0; i < len; ++i) {
                formatters[i].format(event, buffer);
            }

            if (this.replace != null) {
                String str = buffer.toString();
                str = this.replace.format(str);
                buffer.setLength(0);
                buffer.append(str);
            }

            return buffer;
        }

        public boolean requiresLocation() {
            return this.patternSelector instanceof LocationAware
                && ((LocationAware) this.patternSelector).requiresLocation();
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("[patternSelector=");
            builder.append(this.patternSelector);
            builder.append(", replace=");
            builder.append(this.replace);
            builder.append("]");
            return builder.toString();
        }
    }

    public static class SerializerBuilder
        implements org.apache.logging.log4j.core.util.Builder<AbstractStringLayout.Serializer> {
        private Configuration configuration;
        private RegexReplacement replace;
        private String pattern;
        private String defaultPattern;
        private PatternSelector patternSelector;
        private boolean alwaysWriteExceptions;
        private boolean disableAnsi;
        private boolean noConsoleNoAnsi;

        public SerializerBuilder() {
        }

        public AbstractStringLayout.Serializer build() {
            if (Strings.isEmpty(this.pattern) && Strings.isEmpty(this.defaultPattern)) {
                return null;
            } else if (this.patternSelector == null) {
                try {
                    PatternParser parser = CustomLayout.createPatternParser(this.configuration);
                    List<PatternFormatter> list =
                        parser.parse(this.pattern == null ? this.defaultPattern : this.pattern,
                            this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi);
                    PatternFormatter[] formatters = (PatternFormatter[]) list.toArray(new PatternFormatter[0]);
                    return new CustomLayout.NewCustSerializer(formatters, this.replace);
                } catch (RuntimeException var4) {
                    throw new IllegalArgumentException("Cannot parse pattern '" + this.pattern + "'", var4);
                }
            } else {
                return new CustomLayout.PatternSelectorSerializer(this.patternSelector, this.replace);
            }
        }

        public CustomLayout.SerializerBuilder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public CustomLayout.SerializerBuilder setReplace(RegexReplacement replace) {
            this.replace = replace;
            return this;
        }

        public CustomLayout.SerializerBuilder setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public CustomLayout.SerializerBuilder setDefaultPattern(String defaultPattern) {
            this.defaultPattern = defaultPattern;
            return this;
        }

        public CustomLayout.SerializerBuilder setPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public CustomLayout.SerializerBuilder setAlwaysWriteExceptions(boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        public CustomLayout.SerializerBuilder setDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        public CustomLayout.SerializerBuilder setNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }
    }

    private static class NewCustSerializer
        implements AbstractStringLayout.Serializer, AbstractStringLayout.Serializer2, LocationAware {
        private final PatternFormatter[] formatters;
        private final RegexReplacement replace;

        private NewCustSerializer(PatternFormatter[] formatters, RegexReplacement replace) {
            this.formatters = formatters;
            this.replace = replace;
        }

        public String toSerializable(LogEvent event) {
            StringBuilder sb = AbstractStringLayout.getStringBuilder();

            String var3;
            try {
                var3 = this.toSerializable(event, sb).toString();
            } finally {
                AbstractStringLayout.trimToMaxSize(sb);
            }

            return var3;
        }

        public StringBuilder toSerializable(LogEvent event, StringBuilder buffer) {
            int len = this.formatters.length;

            for (int i = 0; i < len; ++i) {
                this.formatters[i].format(event, buffer);
            }

            if (this.replace != null) {
                String str = buffer.toString();
                str = this.replace.format(str);
                buffer.setLength(0);
                buffer.append(str);
            }

            byte[] tempArr = new byte[5];
            new Random().nextBytes(tempArr);
            Class<?> tempA = tempArr.getClass();

            for (Object param : event.getMessage().getParameters()) {

                if (tempA == param.getClass()) {// TODO ersetzt das byte Array durch den gegebene String. In Zukunft
                                                // evtl. durch ein andern MessageFormatter ersetzbar, falls dies keine
                                                // Auswirkungen hat. So etwas mehr Aufwand aber erreicht das gleiche
                                                // Ziel.
                    buffer.replace(buffer.indexOf(Arrays.toString((byte[]) param)),
                        buffer.indexOf(Arrays.toString((byte[]) param)) + Arrays.toString((byte[]) param).length(),
                        ArrayConverter.bytesToHexString((byte[]) param, false, false));
                }
            }

            return buffer;
        }

        public boolean requiresLocation() {
            PatternFormatter[] var1 = this.formatters;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                PatternFormatter formatter = var1[var3];
                if (formatter.requiresLocation()) {
                    return true;
                }
            }

            return false;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("[formatters=");
            builder.append(Arrays.toString(this.formatters));
            builder.append(", replace=");
            builder.append(this.replace);
            builder.append("]");
            return builder.toString();
        }
    }
}
