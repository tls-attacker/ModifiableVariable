/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
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
public final class ExtendedPatternLayout extends AbstractStringLayout {
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
        conversionPattern = eventPattern;
        this.patternSelector = patternSelector;
        eventSerializer =
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
        return eventSerializer instanceof LocationAware
                && ((LocationAware) eventSerializer).requiresLocation();
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
        return conversionPattern;
    }

    @Override
    public Map<String, String> getContentFormat() {
        Map<String, String> result = new HashMap<>();
        result.put("structured", "false");
        result.put("formatType", "conversion");
        result.put("format", conversionPattern);
        return result;
    }

    @Override
    public String toSerializable(LogEvent event) {
        return eventSerializer.toSerializable(event);
    }

    public void serialize(LogEvent event, StringBuilder stringBuilder) {
        eventSerializer.toSerializable(event, stringBuilder);
    }

    @Override
    public void encode(LogEvent event, ByteBufferDestination destination) {
        if (eventSerializer == null) {
            super.encode(event, destination);
        } else {
            StringBuilder text = toText(eventSerializer, event, getStringBuilder());
            Encoder<StringBuilder> encoder = getStringBuilderEncoder();
            encoder.encode(text, destination);
            trimToMaxSize(text);
        }
    }

    private static StringBuilder toText(
            AbstractStringLayout.Serializer2 serializer,
            LogEvent event,
            StringBuilder destination) {
        return serializer.toSerializable(event, destination);
    }

    public static PatternParser createPatternParser(Configuration config) {
        if (config == null) {
            return new PatternParser(config, "Converter", LogEventPatternConverter.class);
        } else {
            PatternParser parser = config.getComponent("Converter");
            if (parser == null) {
                parser = new PatternParser(config, "Converter", LogEventPatternConverter.class);
                config.addComponent("Converter", parser);
                parser = config.getComponent("Converter");
            }

            return parser;
        }
    }

    @Override
    public String toString() {
        return patternSelector == null ? conversionPattern : patternSelector.toString();
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
        return eventSerializer;
    }

    public static final class Builder
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
            super();
            pattern = "%m%n";
            charset = Charset.defaultCharset();
            alwaysWriteExceptions = true;
            disableAnsi = !useAnsiEscapeCodes();
        }

        private static boolean useAnsiEscapeCodes() {
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

        @Override
        public ExtendedPatternLayout build() {
            if (configuration == null) {
                configuration = new DefaultConfiguration();
            }

            return new ExtendedPatternLayout(
                    configuration,
                    regexReplacement,
                    pattern,
                    patternSelector,
                    charset,
                    alwaysWriteExceptions,
                    disableAnsi,
                    noConsoleNoAnsi,
                    header,
                    footer);
        }
    }

    private static final class PatternSelectorSerializer
            implements AbstractStringLayout.Serializer, LocationAware {
        private final PatternSelector patternSelector;
        private final RegexReplacement replace;

        private PatternSelectorSerializer(
                PatternSelector patternSelector, RegexReplacement replace) {
            super();
            this.patternSelector = patternSelector;
            this.replace = replace;
        }

        @Override
        public String toSerializable(LogEvent event) {
            StringBuilder sb = getStringBuilder();

            String var3;
            try {
                var3 = toSerializable(event, sb).toString();
            } finally {
                trimToMaxSize(sb);
            }

            return var3;
        }

        @Override
        public StringBuilder toSerializable(LogEvent event, StringBuilder builder) {
            PatternFormatter[] formatters = patternSelector.getFormatters(event);
            Arrays.stream(formatters).forEachOrdered(formatter -> formatter.format(event, builder));

            if (replace != null) {
                String str = builder.toString();
                str = replace.format(str);
                builder.setLength(0);
                builder.append(str);
            }

            return builder;
        }

        @Override
        public boolean requiresLocation() {
            return patternSelector instanceof LocationAware
                    && ((LocationAware) patternSelector).requiresLocation();
        }

        @Override
        public String toString() {
            return super.toString()
                    + "[patternSelector="
                    + patternSelector.toString()
                    + ", replace="
                    + replace.toString()
                    + "]";
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

        @Override
        public AbstractStringLayout.Serializer build() {
            if (Strings.isEmpty(pattern) && Strings.isEmpty(defaultPattern)) {
                return null;
            } else if (patternSelector == null) {
                try {
                    PatternParser parser = createPatternParser(configuration);
                    List<PatternFormatter> list =
                            parser.parse(
                                    pattern == null ? defaultPattern : pattern,
                                    alwaysWriteExceptions,
                                    disableAnsi,
                                    noConsoleNoAnsi);
                    PatternFormatter[] formatters = list.toArray(new PatternFormatter[0]);
                    return new ExtendedPatternLayout.ExtendedPatternLayoutSerializer(
                            formatters, replace);
                } catch (RuntimeException var4) {
                    throw new IllegalArgumentException(
                            "Cannot parse pattern '" + pattern + "'", var4);
                }
            } else {
                return new ExtendedPatternLayout.PatternSelectorSerializer(
                        patternSelector, replace);
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

    private static final class ExtendedPatternLayoutSerializer
            implements AbstractStringLayout.Serializer, LocationAware {
        private final PatternFormatter[] formatters;
        private final RegexReplacement replace;

        private ExtendedPatternLayoutSerializer(
                PatternFormatter[] formatters, RegexReplacement replace) {
            super();
            this.formatters = formatters;
            this.replace = replace;
        }

        @Override
        public String toSerializable(LogEvent event) {
            StringBuilder sb = getStringBuilder();
            String var3;
            try {
                var3 = toSerializable(event, sb).toString();
            } finally {
                trimToMaxSize(sb);
            }

            return var3;
        }

        @Override
        public StringBuilder toSerializable(LogEvent event, StringBuilder builder) {
            Arrays.stream(formatters).forEachOrdered(formatter -> formatter.format(event, builder));

            if (replace != null) {
                String str = builder.toString();
                str = replace.format(str);
                builder.setLength(0);
                builder.append(str);
            }

            // Added section to parse ByteArrays to the correct output format.
            Class<byte[]> bArrayClass = byte[].class;
            if (event != null
                    && event.getMessage() != null
                    && event.getMessage().getParameters() != null) {

                // Iterate over each parameter of a {@Link LogEvent} to find all ByteArrays
                for (Object param : event.getMessage().getParameters()) {

                    // Replace all ByteArrays with the String representation of the ByteArray
                    // calculated
                    // by the ArrayConverter.
                    if (param != null && bArrayClass.equals(param.getClass())) {
                        builder.replace(
                                builder.indexOf(Arrays.toString((byte[]) param)),
                                builder.indexOf(Arrays.toString((byte[]) param))
                                        + Arrays.toString((byte[]) param).length(),
                                ArrayConverter.bytesToHexString(
                                        (byte[]) param,
                                        Builder.prettyPrinting,
                                        Builder.initNewLine));
                    }
                }
            }
            return builder;
        }

        public boolean requiresLocation() {
            return Arrays.stream(formatters).anyMatch(PatternFormatter::requiresLocation);
        }

        @Override
        public String toString() {
            return super.toString()
                    + "[formatters="
                    + Arrays.toString(formatters)
                    + ", replace="
                    + replace.toString()
                    + "]";
        }
    }
}
