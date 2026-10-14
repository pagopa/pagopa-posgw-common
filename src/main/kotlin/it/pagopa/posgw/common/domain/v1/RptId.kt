package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * An <abbr>RPT</abbr> id.
 *
 * This identifies a single notice given to a citizen inside the pagoPA platform.
 *
 * It <b>MUST</b> adhere to the following format: {@code \d{29})}
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

    val fiscalCode: String
        get() = value.substring(0, 11)

    val noticeId: String
        get() = value.substring(11)

    val auxDigit: String
        get() = noticeId.substring(0, 1)

    val applicationCode: String?
        get() = if (auxDigit == "0") noticeId.substring(1, 3) else null

    val iuv: String
        get() = noticeId.substring(if (applicationCode != null) 3 else 1, 18)

    companion object {
        private val RPT_ID_REGEX = Regex("""\d{29}""")
    }
}
