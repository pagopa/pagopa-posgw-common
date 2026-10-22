package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Identifies a single payment notice issued by a creditor institution.
 *
 * The value must contain exactly 29 digits.
 *
 * @param value RPT id value
 */
@ValueObject
@JvmInline
value class RptId(val value: String) {
    init {
        require(RPT_ID_REGEX.matches(value)) {
            "Ill-formed RPT id: $value. Doesn't match format: ${RPT_ID_REGEX.pattern}"
        }
    }

    /** Fiscal code in the first 11 digits of the RPT identifier. */
    val fiscalCode: String
        get() = value.substring(0, 11)

    /** Notice identifier following the fiscal code. */
    val noticeId: String
        get() = value.substring(11)

    /** Auxiliary digit at the start of the notice identifier. */
    val auxDigit: String
        get() = noticeId.substring(0, 1)

    /** Application code present when [auxDigit] is `0`, otherwise `null`. */
    val applicationCode: String?
        get() = if (auxDigit == "0") noticeId.substring(1, 3) else null

    /** IUV portion of the notice identifier. */
    val iuv: String
        get() = noticeId.substring(if (applicationCode != null) 3 else 1, 18)

    companion object {
        private val RPT_ID_REGEX = Regex("""\d{29}""")
    }
}
