package com.w2m.heroestest.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * The Class StringUtils.
 */
public final class StringUtils {

    /**
     * The Constant EMPTY.
     */
    public static final String EMPTY = org.apache.commons.lang3.StringUtils.EMPTY;

    /**
     * The Constant SPACE.
     */
    public static final String SPACE = org.apache.commons.lang3.StringUtils.SPACE;

    /**
     * The Constant DASH.
     */
    public static final String DASH = "-";

    /**
     * The Constant UNDERSCORE.
     */
    public static final String UNDERSCORE = "_";

    /**
     * The Constant COMMA.
     */
    public static final String COMMA = ",";

    /**
     * The Constant DOT.
     */
    public static final String DOT = ".";

    /**
     * The Constant SEMICOLON.
     */
    public static final String SEMICOLON = ";";

    /**
     * The Constant COLON.
     */
    public static final String COLON = ":";

    /**
     * The Constant DOUBLE_QUOTE.
     */
    public static final String DOUBLE_QUOTE = "\"";

    /**
     * The Constant END_LINE.
     */
    public static final String END_LINE = "\n";

    /**
     * The Constant SLASH.
     */
    public static final String SLASH = "/";

    /**
     * The Constant BACK_SLASH.
     */
    public static final String BACK_SLASH = "\\";

    /**
     * The Constant PIPE.
     */
    public static final String PIPE = "|";

    /**
     * The Constant OPEN_PARENTHESIS.
     */
    public static final String OPEN_PARENTHESIS = "(";

    /**
     * The Constant CLOSE_PARENTHESIS.
     */
    public static final String CLOSE_PARENTHESIS = ")";

    /**
     * The Constant OPEN_BRAQUET.
     */
    public static final String OPEN_BRAQUET = "{";

    /**
     * The Constant CLOSE_BRAQUET.
     */
    public static final String CLOSE_BRAQUET = "}";

    /**
     * The Constant L_TRIM.
     */
    private static final Pattern L_TRIM = Pattern.compile("^\\s+");

    /**
     * The Constant R_TRIM.
     */
    private static final Pattern R_TRIM = Pattern.compile("\\s+$");

    /**
     * Constructor to avoid instantiation of the class.
     */
    private StringUtils() {
    }

    /**
     * Gets the empty or value.
     *
     * @param value the value
     * @return the empty or value
     */
    public static String getEmptyOrValue(final String value) {
        String result = EMPTY;
        if (ParamUtils.paramInformed(value)) {
            result = value;
        }

        return result;
    }

    /**
     * Gets the null or value.
     *
     * @param value the value
     * @return the null or value
     */
    public static String getNullOrValue(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = value;
        }

        return result;
    }

    /**
     * Removes the from right.
     *
     * @param value        the value
     * @param charToDelete the char to delete
     * @return the string
     */
    public static String removeFromRight(final String value, final char charToDelete) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            int valueLength = value.length();
            boolean stopRemove = false;
            result = value;

            while (valueLength > 1 && !stopRemove) {
                final int lastIdx = valueLength - 1;
                final char last = value.charAt(lastIdx);
                if (last == charToDelete) {
                    result = result.substring(0, result.length() - 1);
                    valueLength--;
                } else {
                    stopRemove = true;
                }
            }
        }

        return result;
    }

    /**
     * Capitalize.
     *
     * @param value the value
     * @return the string
     */
    public static String capitalize(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = org.apache.commons.lang3.StringUtils.capitalize(value.toLowerCase());
        }
        return result;
    }

    /**
     * Strip accents.
     *
     * @param value the value
     * @return the string
     */
    public static String stripAccents(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = org.apache.commons.lang3.StringUtils.stripAccents(value.toLowerCase());
        }
        return result;
    }

    /**
     * Clear special chars.
     *
     * @param value the value
     * @return the string
     */
    public static String clearSpecialChars(final String value) {
        final StringBuilder result = new StringBuilder();
        if (ParamUtils.paramInformed(value)) {
            final int size = value.length();
            final CharSequence charSequence = value;
            for (int i = 0; i < size; i++) {
                if (Character.isLetterOrDigit(charSequence.charAt(i))) {
                    result.append(charSequence.charAt(i));
                }
            }
        }
        return result.toString();
    }

    /**
     * Creates the enumerated.
     *
     * @param values the values
     * @return string with this format (entry value1 value2 ) 1. Value1 2. Value2
     */
    public static String createEnumerated(final List<String> values) {
        return createEnumerated(values, false, ". ", true);
    }

    /**
     * Creates the enumerated without number.
     *
     * @param values the values
     * @return string with this format (entry value1 value2) Value1 Value2
     */
    public static String createEnumeratedWithoutNumber(final List<String> values) {
        return createEnumerated(values, false, " ", false);
    }

    /**
     * Creates the enumerated.
     *
     * @param values      the values
     * @param stratInCero the strat in cero
     * @param separator   the separator
     * @param withNumber  the with number
     * @return the string
     */
    public static String createEnumerated(final List<String> values, final boolean stratInCero, final String separator,
            final boolean withNumber) {
        final StringBuilder buffer = new StringBuilder(EMPTY);
        if (ParamUtils.paramInformed(values)) {
            IntStream.range(0, values.size()).forEach(index -> {
                final String value = values.get(index);

                if (withNumber) {
                    final int numList = stratInCero ? 0 : 1;
                    buffer.append(String.format("%s%s%s", index + numList, separator, capitalize(value)));

                } else {
                    buffer.append(String.format("%s%s", separator, capitalize(value)));
                }
                if (index < values.size() - 1) {
                    buffer.append(END_LINE);
                }
            });
        }

        return buffer.toString();
    }

    /**
     * Parses the number with dot.
     *
     * @param value the value
     * @return the string
     */
    public static String parseNumberWithDot(final BigDecimal value) {
        return parseNumber(value, DOT);
    }

    /**
     * Parses the number with comma.
     *
     * @param value the value
     * @return the string
     */
    public static String parseNumberWithComma(final BigDecimal value) {
        return parseNumber(value, COMMA);
    }

    /**
     * Parses the number.
     *
     * @param value            the value
     * @param decimalSeparator the decimal separator
     * @return the string
     */
    public static String parseNumber(final BigDecimal value, final String decimalSeparator) {
        String result = null;
        if (value != null) {
            result = value.toString().replace(DOT, decimalSeparator);
        }

        return result;
    }

    /**
     * Find nth occur.
     *
     * @param str the str
     * @param ch  the ch
     * @param N   the n
     * @return the int
     */
    public static int findNthOccur(final String str, final char ch, final int N) {
        int result = -1;
        if (ParamUtils.paramInformed(str) && N > 0) {
            int index = 0;
            int occur = 0;
            boolean found = false;
            while (index < str.length() && !found) {
                if (str.charAt(index) == ch) {
                    occur += 1;
                }
                if (occur == N) {
                    result = index;
                    found = true;
                }
                index = index + 1;
            }
        }
        return result;
    }

    /**
     * Uncapitalize.
     *
     * @param str the str
     * @return the string
     */
    public static String uncapitalize(final String str) {
        String result = null;
        if (ParamUtils.paramInformed(str)) {
            result = org.apache.commons.lang3.StringUtils.uncapitalize(str.toLowerCase());
        }
        return result;
    }

    /**
     * Substring after last.
     *
     * @param str       the str
     * @param separator the separator
     * @return the string
     */
    public static String substringAfterLast(final String str, final String separator) {
        String result = null;
        if (ParamUtils.paramInformed(str)) {
            result = org.apache.commons.lang3.StringUtils.substringAfterLast(str.toLowerCase(), separator);
        }
        return result;
    }

    /**
     * Left trim to value.
     *
     * @param value the value
     * @return the string
     */
    public static String ltrim(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = L_TRIM.matcher(value).replaceAll("");
        }

        return result;
    }

    /**
     * Right trim to value.
     *
     * @param value the value
     * @return the string
     */
    public static String rtrim(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = R_TRIM.matcher(value).replaceAll("");
        }

        return result;
    }

    /**
     * Applies both Left and Right trim to a given String value.
     *
     * @param value the value
     * @return the string
     */
    public static String edgeTrim(final String value) {
        String result = null;
        if (ParamUtils.paramInformed(value)) {
            result = R_TRIM.matcher(L_TRIM.matcher(value).replaceAll("")).replaceAll("");
        }
        return result;
    }

}
