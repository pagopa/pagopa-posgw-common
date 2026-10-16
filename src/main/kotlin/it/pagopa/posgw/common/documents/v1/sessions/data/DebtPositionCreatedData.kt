package it.pagopa.posgw.common.documents.v1.sessions.data


data class DebtPositionCreatedData(val depPositions: List<DebtPosition>){

    data class DebtPosition(
        val iupd: String,
        val rptId: String,
        val hashedDebtorFiscalCode: String
    )
}
