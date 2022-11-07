/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.logging;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * A layout for LOG messages in the correct format and also for logging ByteArrays with good
 * performance. The ExtendedPatternLayout is mostly copied from {@link
 * org.apache.logging.log4j.core.layout.PatternLayout}.
 */
@Plugin(
        name = "ExtendedPatternLayout",
        category = "Core",
        elementType = "layout",
        printObject = true)
public class ExtendedPatternLayout extends AbstractStringLayout {
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %notEmpty{%x }- %m%n";
    public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
    public static final String KEY = "Converter";
    private final String conversionPattern;
    private final PatternSelector patternSelector;
    private final AbstractStringLayout.Serializer eventSerializer;

    private ExtendedPatternLayout(
            Configuration config,
            RegexReplacement replace,
            String eventPattern,
            PatternSelector patternSelector,
            Charset charset,
            boolean alwaysWriteExceptions,
            boolean disableAnsi,
            boolean noConsoleNoAnsi,
            String headerPattern,
            String footerPattern) {
        super(
                config,
                charset,
                newSerializerBuilder()
                        .setConfiguration(config)
                        .setReplace(replace)
                        .setPatternSelector(patternSelector)
                        .setAlwaysWriteExceptions(alwaysWriteExceptions)
                        .setDisableAnsi(disableAnsi)
                        .setNoConsoleNoAnsi(noConsoleNoAnsi)
                        .setPattern(headerPattern)
                        .build(),
                newSerializerBuilder()
                        .setConfiguration(config)
                        .setReplace(replace)
                        .setPatternSelector(patternSelector)
                        .setAlwaysWriteExceptions(alwaysWriteExceptions)
                        .setDisableAnsi(disableAnsi)
                        .setNoConsoleNoAnsi(noConsoleNoAnsi)
                        .setPattern(footerPattern)
                        .build());
        this.conversionPattern = eventPattern;
        this.patternSelector = patternSelector;
        this.eventSerializer =
                newSerializerBuilder()
                        .setConfiguration(config)
                        .setReplace(replace)
                        .setPatternSelector(patternSelector)
                        .setAlwaysWriteExceptions(alwaysWriteExceptions)
                        .setDisableAnsi(disableAnsi)
                        .setNoConsoleNoAnsi(noConsoleNoAnsi)
                        .setPattern(eventPattern)
                        .setDefaultPattern("%m%n")
                        .build();
    }

    public static ExtendedPatternLayout.SerializerBuilder newSerializerBuilder() {
        return new ExtendedPatternLayout.SerializerBuilder();
    }

    @Override
    public boolean requiresLocation() {
        return this.eventSerializer instanceof LocationAware
                && ((LocationAware) this.eventSerializer).requiresLocation();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static AbstractStringLayout.Serializer createSerializer(
            Configuration configuration,
            RegexReplacement replace,
            String pattern,
            String defaultPattern,
            PatternSelector patternSelector,
            boolean alwaysWriteExceptions,
            boolean noConsoleNoAnsi) {
        ExtendedPatternLayout.SerializerBuilder builder = newSerializerBuilder();
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
                    this.toText(
                            (AbstractStringLayout.Serializer2) this.eventSerializer,
                            event,
                            getStringBuilder());
            Encoder<StringBuilder> encoder = this.getStringBuilderEncoder();
            encoder.encode(text, destination);
            trimToMaxSize(text);
        }
    }

    private StringBuilder toText(
            AbstractStringLayout.Serializer2 serializer,
            LogEvent event,
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

    @Override
    public String toString() {
        return this.patternSelector == null
                ? this.conversionPattern
                : this.patternSelector.toString();
    }

    /**
     * @deprecated
     */
    @PluginFactory
    @Deprecated
    public static ExtendedPatternLayout createLayout(
            @PluginAttribute(value = "pattern", defaultString = "%m%n") String pattern,
            @PluginElement("PatternSelector") PatternSelector patternSelector,
            @PluginConfiguration Configuration config,
            @PluginElement("Replace") RegexReplacement replace,
            @PluginAttribute("charset") Charset charset,
            @PluginAttribute(value = "alwaysWriteExceptions", defaultBoolean = true)
                    boolean alwaysWriteExceptions,
            @PluginAttribute("noConsoleNoAnsi") boolean noConsoleNoAnsi,
            @PluginAttribute("header") String headerPattern,
            @PluginAttribute("footer") String footerPattern) {
        return newBuilder()
                .withPattern(pattern)
                .withPatternSelector(patternSelector)
                .withConfiguration(config)
                .withRegexReplacement(replace)
                .withCharset(charset)
                .withAlwaysWriteExceptions(alwaysWriteExceptions)
                .withNoConsoleNoAnsi(noConsoleNoAnsi)
                .withHeader(headerPattern)
                .withFooter(footerPattern)
                .build();
    }

    public static ExtendedPatternLayout createDefaultLayout() {
        return newBuilder().build();
    }

    public static ExtendedPatternLayout createDefaultLayout(Configuration configuration) {
        return newBuilder().withConfiguration(configuration).build();
    }

    @PluginBuilderFactory
    public static ExtendedPatternLayout.Builder newBuilder() {
        return new ExtendedPatternLayout.Builder();
    }

    public AbstractStringLayout.Serializer getEventSerializer() {
        return this.eventSerializer;
    }

    public static class Builder
            implements org.apache.logging.log4j.core.util.Builder<ExtendedPatternLayout> {
        @PluginBuilderAttribute private String pattern;

        @PluginElement("PatternSelector")
        private PatternSelector patternSelector;

        @PluginConfiguration private Configuration configuration;

        @PluginElement("Replace")
        private RegexReplacement regexReplacement;

        @PluginBuilderAttribute private Charset charset;
        @PluginBuilderAttribute private boolean alwaysWriteExceptions;
        @PluginBuilderAttribute private boolean disableAnsi;
        @PluginBuilderAttribute private boolean noConsoleNoAnsi;
        @PluginBuilderAttribute private String header;
        @PluginBuilderAttribute private String footer;


        @PluginBuilderAttribute("initNewLine")
        private static boolean initNewLine;

        @PluginBuilderAttribute("prettyPrinting")
        private static boolean prettyPrinting;

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

        public ExtendedPatternLayout.Builder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public ExtendedPatternLayout.Builder withPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public ExtendedPatternLayout.Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ExtendedPatternLayout.Builder withRegexReplacement(
                RegexReplacement regexReplacement) {
            this.regexReplacement = regexReplacement;
            return this;
        }

        public ExtendedPatternLayout.Builder withCharset(Charset charset) {
            if (charset != null) {
                this.charset = charset;
            }

            return this;
        }

        public ExtendedPatternLayout.Builder withAlwaysWriteExceptions(
                boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        public ExtendedPatternLayout.Builder withDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        public ExtendedPatternLayout.Builder withNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }

        public ExtendedPatternLayout.Builder withHeader(String header) {
            this.header = header;
            return this;
        }

        public ExtendedPatternLayout.Builder withFooter(String footer) {
            this.footer = footer;
            return this;
        }

        public ExtendedPatternLayout build() {
            if (this.configuration == null) {
                this.configuration = new DefaultConfiguration();
            }

            return new ExtendedPatternLayout(
                    this.configuration,
                    this.regexReplacement,
                    this.pattern,
                    this.patternSelector,
                    this.charset,
                    this.alwaysWriteExceptions,
                    this.disableAnsi,
                    this.noConsoleNoAnsi,
                    this.header,
                    this.footer);
        }
    }

    private static class PatternSelectorSerializer
            implements AbstractStringLayout.Serializer,
                    AbstractStringLayout.Serializer2,
                    LocationAware {
        private final PatternSelector patternSelector;
        private final RegexReplacement replace;

        private PatternSelectorSerializer(
                PatternSelector patternSelector, RegexReplacement replace) {
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

        @Override
        public String toString() {
            return super.toString()
                    .concat("[patternSelector=")
                    .concat(this.patternSelector.toString())
                    .concat(", replace=")
                    .concat(this.replace.toString())
                    .concat("]");
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

        public SerializerBuilder() {}

        public AbstractStringLayout.Serializer build() {
            if (Strings.isEmpty(this.pattern) && Strings.isEmpty(this.defaultPattern)) {
                return null;
            } else if (this.patternSelector == null) {
                try {
                    PatternParser parser =
                            ExtendedPatternLayout.createPatternParser(this.configuration);
                    List<PatternFormatter> list =
                            parser.parse(
                                    this.pattern == null ? this.defaultPattern : this.pattern,
                                    this.alwaysWriteExceptions,
                                    this.disableAnsi,
                                    this.noConsoleNoAnsi);
                    PatternFormatter[] formatters =
                            (PatternFormatter[]) list.toArray(new PatternFormatter[0]);
                    return new ExtendedPatternLayout.ExtendedPatternLayoutSerializer(formatters, this.replace);
                } catch (RuntimeException var4) {
                    throw new IllegalArgumentException(
                            "Cannot parse pattern '" + this.pattern + "'", var4);
                }
            } else {
                return new ExtendedPatternLayout.PatternSelectorSerializer(
                        this.patternSelector, this.replace);
            }
        }

        public ExtendedPatternLayout.SerializerBuilder setConfiguration(
                Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setReplace(RegexReplacement replace) {
            this.replace = replace;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setDefaultPattern(String defaultPattern) {
            this.defaultPattern = defaultPattern;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setPatternSelector(
                PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setAlwaysWriteExceptions(
                boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        public ExtendedPatternLayout.SerializerBuilder setNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }
    }

    private static class ExtendedPatternLayoutSerializer
            implements AbstractStringLayout.Serializer,
                    AbstractStringLayout.Serializer2,
                    LocationAware {
        private final PatternFormatter[] formatters;
        private final RegexReplacement replace;

        private ExtendedPatternLayoutSerializer(PatternFormatter[] formatters, RegexReplacement replace) {
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

            /** Added section to parse ByteArrays to the correct output format. */
            Class<?> bArrayClass = (new byte[1]).getClass();

            // Iterate over each parameter of a {@Link LogEvent} to find all ByteArrays
            for (Object param : event.getMessage().getParameters()) {

                // Replace all ByteArrays with the String representation of the ByteArray calculated
                // by the ArrayConverter.
                if (bArrayClass == param.getClass()) {
                    buffer.replace(
                            buffer.indexOf(Arrays.toString((byte[]) param)),
                            buffer.indexOf(Arrays.toString((byte[]) param))
                                    + Arrays.toString((byte[]) param).length(),
                            ArrayConverter.bytesToHexString(
                                    (byte[]) param, Builder.prettyPrinting, Builder.initNewLine));
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

        @Override
        public String toString() {
            return super.toString()
                    .concat("[formatters=")
                    .concat(Arrays.toString(this.formatters))
                    .concat(", replace=")
                    .concat(this.replace.toString())
                    .concat("]");
        }
    }
}
