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
 * A specialized layout for formatting log messages with enhanced byte array handling.
 *
 * <p>This class extends the functionality of Log4j2's standard PatternLayout by adding specialized
 * handling for byte array parameters in log messages. While the standard PatternLayout would format
 * byte arrays using {@link Arrays#toString(byte[])}, which produces output like "[B@1a2b3c4]", this
 * layout intercepts byte array parameters and formats them as readable hexadecimal strings using
 * {@link ArrayConverter#bytesToHexString(byte[])}.
 *
 * <p>The layout supports all standard PatternLayout features including:
 *
 * <ul>
 *   <li>Pattern-based formatting with conversion patterns
 *   <li>Header and footer support
 *   <li>ANSI color support (platform-dependent)
 *   <li>Regex-based text replacement
 *   <li>Pattern selectors for context-based formatting
 * </ul>
 *
 * <p>Additionally, it provides configuration options specific to byte array formatting:
 *
 * <ul>
 *   <li>{@code prettyPrinting} - Whether to add spacing between bytes for readability
 *   <li>{@code initNewLine} - Whether to start byte array output on a new line
 * </ul>
 *
 * <p>The implementation is based on {@link org.apache.logging.log4j.core.layout.PatternLayout} with
 * modifications for byte array handling.
 */
@Plugin(
        name = "ExtendedPatternLayout",
        category = "Core",
        elementType = "layout",
        printObject = true)
public final class ExtendedPatternLayout extends AbstractStringLayout {
    /** The default conversion pattern: "%m%n" (message followed by a newline) */
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";

    /**
     * The TTCC conversion pattern includes thread, time, category and context information: "%r [%t]
     * %p %c %notEmpty{%x }- %m%n"
     */
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %notEmpty{%x }- %m%n";

    /**
     * A simple conversion pattern with date, thread, priority, category and message: "%d [%t] %p %c
     * - %m%n"
     */
    public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";

    /** The key for the pattern converter in the configuration */
    public static final String KEY = "Converter";

    /** The pattern string used to format log events */
    private final String conversionPattern;

    /** Pattern selector for dynamic pattern selection based on log event properties */
    private final PatternSelector patternSelector;

    /** Serializer used to format log events into strings */
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

    /**
     * Creates a new serializer builder for configuring a serializer.
     *
     * @return A new serializer builder instance
     */
    public static ExtendedPatternLayout.SerializerBuilder newSerializerBuilder() {
        return new ExtendedPatternLayout.SerializerBuilder();
    }

    /**
     * Determines if this layout requires location information (i.e., class name, method name, line
     * number).
     *
     * <p>Location information can be expensive to generate, so this method allows the logging
     * framework to determine whether it needs to capture stack traces for log events processed by
     * this layout.
     *
     * @return {@code true} if the configured event serializer requires location information, {@code
     *     false} otherwise
     */
    @Override
    public boolean requiresLocation() {
        return eventSerializer instanceof LocationAware
                && ((LocationAware) eventSerializer).requiresLocation();
    }

    /**
     * Creates a serializer with the specified parameters.
     *
     * @param configuration The configuration to use
     * @param replace The regex replacement to apply to the formatted output
     * @param pattern The conversion pattern to use for formatting
     * @param defaultPattern The default pattern to use if the primary pattern is not available
     * @param patternSelector Optional pattern selector for dynamic pattern selection
     * @param alwaysWriteExceptions Whether to always include exception information in the output
     * @param noConsoleNoAnsi Whether to disable ANSI escapes when output is not to a console
     * @return A serializer configured with the specified parameters
     * @deprecated Use the builder pattern with {@link #newSerializerBuilder()} instead
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

    /**
     * Returns the conversion pattern used by this layout.
     *
     * @return The pattern string used to format log events
     */
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

    /**
     * Converts a LogEvent to a serialized string representation.
     *
     * <p>This method delegates to the configured event serializer to format the log event according
     * to the pattern and configuration settings.
     *
     * @param event The LogEvent to serialize
     * @return The formatted string representation of the log event
     */
    @Override
    public String toSerializable(LogEvent event) {
        return eventSerializer.toSerializable(event);
    }

    /**
     * Serializes a LogEvent into the provided StringBuilder.
     *
     * <p>This method is more efficient than {@link #toSerializable(LogEvent)} when the caller
     * already has a StringBuilder, as it avoids creating intermediate string objects.
     *
     * @param event The LogEvent to serialize
     * @param stringBuilder The StringBuilder to append the formatted event to
     */
    public void serialize(LogEvent event, StringBuilder stringBuilder) {
        eventSerializer.toSerializable(event, stringBuilder);
    }

    /**
     * Encodes a LogEvent to a byte buffer destination.
     *
     * <p>This method handles the complete serialization and encoding process for writing a log
     * event to a destination. It first formats the event using the configured serializer, then
     * encodes the resulting text using the configured charset.
     *
     * @param event The LogEvent to encode
     * @param destination The destination to write the encoded event to
     */
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

    /**
     * Creates a pattern parser for the given configuration.
     *
     * <p>If the configuration already contains a pattern parser component with the key "Converter",
     * that parser is returned. Otherwise, a new parser is created and registered with the
     * configuration.
     *
     * @param config The configuration to create or retrieve a pattern parser for
     * @return A pattern parser for the specified configuration
     */
    public static PatternParser createPatternParser(Configuration config) {
        if (config == null) {
            return new PatternParser(null, "Converter", LogEventPatternConverter.class);
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
     * Creates a layout with the specified parameters.
     *
     * @param pattern The conversion pattern to use for formatting
     * @param patternSelector Optional pattern selector for dynamic pattern selection
     * @param config The configuration to use
     * @param replace The regex replacement to apply to the formatted output
     * @param charset The character set to use for encoding the output
     * @param alwaysWriteExceptions Whether to always include exception information in the output
     * @param noConsoleNoAnsi Whether to disable ANSI escapes when output is not to a console
     * @param headerPattern The pattern to use for the header
     * @param footerPattern The pattern to use for the footer
     * @return A layout configured with the specified parameters
     * @deprecated Use the builder pattern with {@link #newBuilder()} instead
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

    /**
     * Creates a layout with default settings.
     *
     * @return A layout configured with default settings
     */
    public static ExtendedPatternLayout createDefaultLayout() {
        return newBuilder().build();
    }

    /**
     * Creates a layout with default settings and the specified configuration.
     *
     * @param configuration The configuration to use
     * @return A layout configured with default settings and the specified configuration
     */
    public static ExtendedPatternLayout createDefaultLayout(Configuration configuration) {
        return newBuilder().withConfiguration(configuration).build();
    }

    /**
     * Creates a new builder for configuring a layout.
     *
     * @return A new builder instance
     */
    @PluginBuilderFactory
    public static ExtendedPatternLayout.Builder newBuilder() {
        return new ExtendedPatternLayout.Builder();
    }

    /**
     * Returns the serializer used to format log events.
     *
     * @return The event serializer
     */
    public AbstractStringLayout.Serializer getEventSerializer() {
        return eventSerializer;
    }

    /**
     * Builder for creating and configuring ExtendedPatternLayout instances.
     *
     * <p>This builder provides a fluent API for configuring all aspects of the layout, including
     * patterns, formatters, character encoding, and display options.
     */
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

        /**
         * Sets the conversion pattern to use for formatting log events.
         *
         * @param pattern The pattern string
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        /**
         * Sets the pattern selector for dynamic pattern selection based on log event properties.
         *
         * @param patternSelector The pattern selector to use
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        /**
         * Sets the configuration to use for this layout.
         *
         * @param configuration The configuration
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Sets the regex replacement to apply to the formatted output.
         *
         * @param regexReplacement The regex replacement
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withRegexReplacement(
                RegexReplacement regexReplacement) {
            this.regexReplacement = regexReplacement;
            return this;
        }

        /**
         * Sets the character set to use for encoding the output.
         *
         * @param charset The character set
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withCharset(Charset charset) {
            if (charset != null) {
                this.charset = charset;
            }

            return this;
        }

        /**
         * Sets whether to always include exception information in the output.
         *
         * @param alwaysWriteExceptions Whether to always write exceptions
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withAlwaysWriteExceptions(
                boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        /**
         * Sets whether to disable ANSI escape codes in the output.
         *
         * @param disableAnsi Whether to disable ANSI escape codes
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        /**
         * Sets whether to disable ANSI escapes when output is not to a console.
         *
         * @param noConsoleNoAnsi Whether to disable ANSI when not writing to console
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withNoConsoleNoAnsi(boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }

        /**
         * Sets the pattern to use for the header.
         *
         * @param header The header pattern
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withHeader(String header) {
            this.header = header;
            return this;
        }

        /**
         * Sets the pattern to use for the footer.
         *
         * @param footer The footer pattern
         * @return This builder instance
         */
        public ExtendedPatternLayout.Builder withFooter(String footer) {
            this.footer = footer;
            return this;
        }

        /**
         * Builds a new ExtendedPatternLayout instance with the configured settings.
         *
         * <p>This method creates a new layout using all the settings configured on this builder. If
         * no configuration has been explicitly set, a default configuration will be used.
         *
         * @return A new ExtendedPatternLayout instance configured with the specified settings
         */
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

    /**
     * Builder class for creating serializers for formatting log events.
     *
     * <p>This builder provides a fluent API for configuring all aspects of serialization, including
     * patterns, pattern selectors, and formatting options.
     */
    public static class SerializerBuilder
            implements org.apache.logging.log4j.core.util.Builder<AbstractStringLayout.Serializer> {

        /**
         * Creates a new SerializerBuilder with default settings.
         *
         * <p>This constructor initializes a builder with no configuration, pattern, pattern
         * selector, or formatting options. These must be set using the appropriate setter methods
         * before calling {@link #build()}.
         */
        public SerializerBuilder() {
            // Default constructor deliberately left empty
        }

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

        /**
         * Sets the configuration to use for this serializer.
         *
         * @param configuration The configuration
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setConfiguration(
                Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Sets the regex replacement to apply to the formatted output.
         *
         * @param replace The regex replacement
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setReplace(RegexReplacement replace) {
            this.replace = replace;
            return this;
        }

        /**
         * Sets the conversion pattern to use for formatting log events.
         *
         * @param pattern The pattern string
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        /**
         * Sets the default pattern to use if the primary pattern is not available.
         *
         * @param defaultPattern The default pattern string
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setDefaultPattern(String defaultPattern) {
            this.defaultPattern = defaultPattern;
            return this;
        }

        /**
         * Sets the pattern selector for dynamic pattern selection based on log event properties.
         *
         * @param patternSelector The pattern selector to use
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setPatternSelector(
                PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        /**
         * Sets whether to always include exception information in the output.
         *
         * @param alwaysWriteExceptions Whether to always write exceptions
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setAlwaysWriteExceptions(
                boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        /**
         * Sets whether to disable ANSI escape codes in the output.
         *
         * @param disableAnsi Whether to disable ANSI escape codes
         * @return This builder instance
         */
        public ExtendedPatternLayout.SerializerBuilder setDisableAnsi(boolean disableAnsi) {
            this.disableAnsi = disableAnsi;
            return this;
        }

        /**
         * Sets whether to disable ANSI escapes when output is not to a console.
         *
         * @param noConsoleNoAnsi Whether to disable ANSI when not writing to console
         * @return This builder instance
         */
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

        /**
         * Converts a LogEvent to a serialized string representation in the provided StringBuilder.
         *
         * <p>This method is the core implementation of the layout's formatting process:
         *
         * <ol>
         *   <li>First, it applies all pattern formatters to build the base formatted string
         *   <li>Then it applies any regex replacements if configured
         *   <li>Finally, it performs the specialized byte array handling that sets this layout
         *       apart:
         *       <ul>
         *         <li>It identifies any byte array parameters in the log message
         *         <li>It locates the default string representation of these byte arrays in the
         *             builder
         *         <li>It replaces them with formatted hexadecimal strings using ArrayConverter
         *       </ul>
         * </ol>
         *
         * <p>The byte array formatting is controlled by two static configuration options:
         *
         * <ul>
         *   <li>{@link Builder#prettyPrinting} - Whether to format with spaces between bytes
         *   <li>{@link Builder#initNewLine} - Whether to start byte arrays on a new line
         * </ul>
         *
         * @param event The LogEvent to serialize
         * @param builder The StringBuilder to append the formatted event to
         * @return The StringBuilder with the formatted event appended
         */
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
                    // calculated by the ArrayConverter.
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

        /**
         * Determines if any formatter requires location information.
         *
         * @return true if location information is required, false otherwise
         */
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
