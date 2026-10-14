package it.pagopa.posgw.common.domain.v1

data class DebtPosition(

    // The unique identifier of the debt position
    val iupd: Iupd,
    // Unique identifier of the debt position: concatenation of fiscal code and notice code:
    // <fiscalCode><noticeCode>
    val rptId: RptId,
    // Fiscal code of the debtor: must be obfuscated
    val debtorFiscalCode: DebtorFiscalCode
)
