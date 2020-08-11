/**
 * Constants copied from android.util.Patterns
 */
package com.phelat.tedu.sdkextensions

private const val PROTOCOL = "(?i:http|https)://"
private const val WORD_BOUNDARY = "(?:\\b|$|^)"
private const val USER_INFO = ("(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)" +
        "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_" +
        "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@")
private const val UCS_CHAR = "[" +
        "\u00A0-\uD7FF" +
        "\uF900-\uFDCF" +
        "\uFDF0-\uFFEF" +
        "\uD800\uDC00-\uD83F\uDFFD" +
        "\uD840\uDC00-\uD87F\uDFFD" +
        "\uD880\uDC00-\uD8BF\uDFFD" +
        "\uD8C0\uDC00-\uD8FF\uDFFD" +
        "\uD900\uDC00-\uD93F\uDFFD" +
        "\uD940\uDC00-\uD97F\uDFFD" +
        "\uD980\uDC00-\uD9BF\uDFFD" +
        "\uD9C0\uDC00-\uD9FF\uDFFD" +
        "\uDA00\uDC00-\uDA3F\uDFFD" +
        "\uDA40\uDC00-\uDA7F\uDFFD" +
        "\uDA80\uDC00-\uDABF\uDFFD" +
        "\uDAC0\uDC00-\uDAFF\uDFFD" +
        "\uDB00\uDC00-\uDB3F\uDFFD" +
        "\uDB44\uDC00-\uDB7F\uDFFD" +
        "&&[^\u00A0[\u2000-\u200A]\u2028\u2029\u202F\u3000]]"
private const val LABEL_CHAR = "a-zA-Z0-9$UCS_CHAR"
private const val IRI_LABEL = "[$LABEL_CHAR](?:[${LABEL_CHAR}_\\-]{0,61}[$LABEL_CHAR]){0,1}"
private const val IP_ADDRESS_STRING = (
        "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]" +
                "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]" +
                "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}" +
                "|[1-9][0-9]|[0-9]))"
        )
private const val RELAXED_DOMAIN_NAME = "(?:(?:$IRI_LABEL(?:\\.(?=\\S))?)+|$IP_ADDRESS_STRING)"
private const val PORT_NUMBER = "\\:\\d{1,5}"
private const val PATH_AND_QUERY = "[/\\?](?:(?:[$LABEL_CHAR;/\\?:@&=#~\\-\\.\\+!\\*" +
        "'\\(\\),_\\$])|(?:%[a-fA-F0-9]{2}))*"
private const val WEB_URL_WITH_PROTOCOL = "($WORD_BOUNDARY(?:(?:$PROTOCOL(?:$USER_INFO)?)" +
        "(?:$RELAXED_DOMAIN_NAME)?(?:$PORT_NUMBER)?)(?:$PATH_AND_QUERY)?$WORD_BOUNDARY)"

fun String.isValidUrlWithProtocol(): Boolean {
    return this.matches(WEB_URL_WITH_PROTOCOL.toRegex())
}