package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.documents.BaseSessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import it.pagopa.posgw.common.documents.v1.sessions.data.InstrumentDetails
import it.pagopa.posgw.common.documents.v1.sessions.data.Psp
import it.pagopa.posgw.common.documents.v1.sessions.data.SessionInitializedData
import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifyExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ReconciledEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionInitializedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlin.test.assertEquals
import org.bson.Document
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

class MongoMappingTest {

    // same wiring Spring Boot applies, so types like Instant are treated as simple types
    private val conversions = MongoCustomConversions(emptyList<Any>())
    private val converter =
        MappingMongoConverter(
                NoOpDbRefResolver.INSTANCE,
                MongoMappingContext().apply {
                    setSimpleTypeHolder(conversions.simpleTypeHolder)
                    afterPropertiesSet()
                })
            .apply {
                setCustomConversions(conversions)
                afterPropertiesSet()
            }

    @ParameterizedTest
    @MethodSource("events")
    fun `reads back every event as written`(event: SessionEvent<*>) {
        val document = Document()
        converter.write(event, document)

        val read = converter.read(BaseSessionEvent::class.java, document)

        assertEquals(event::class, read::class)
        assertEquals(event, read)
        assertEquals(event.timestamp.truncatedTo(ChronoUnit.MILLIS), read.timestamp)
    }

    @Test
    fun `reads back the view with its events`() {
        val view =
            SessionView(
                id = SESSION_ID.toString(),
                correlationId = "correlation-1",
                status = "INITIALIZED",
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                pspList = listOf(PSP),
                events = events())
        val document = Document()
        converter.write(view, document)

        val read = converter.read(SessionView::class.java, document)

        assertEquals(view.pspList, read.pspList)
        assertEquals(view.events, read.events)
    }

    @Test
    fun `stores the psp identifier as id`() {
        val document = Document()
        converter.write(events().first(), document)

        assertEquals("psp-1", (document["data"] as Document).get("psp", Document::class.java)["id"])
    }

    companion object {
        private val SESSION_ID = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        private val PSP = Psp("psp-1", "broker-1", "channel-1")

        @JvmStatic
        fun events(): List<SessionEvent<*>> =
            listOf(
                SessionInitializedEvent(
                    SESSION_ID,
                    SessionInitializedData(
                        PSP,
                        SessionInitializedData.CreditorInstitution(
                            "fiscal-code", "office", "station"),
                        listOf("terminal-1"),
                        "correlation-1")),
                DeptPositionCreatedEvent(
                    SESSION_ID,
                    DebtPositionCreatedData(listOf(DebtPosition("iupd-1", "rpt-1", "hash-1")))),
                TransactionActivatedEvent(
                    SESSION_ID,
                    TransactionActivatedData(
                        "transaction-1",
                        listOf(
                            TransactionActivatedData.ActivationInfo(
                                "token-1", "rpt-1", 100, "OK", null)))),
                AuthRequestedEvent(SESSION_ID, AuthRequestedData("terminal-1", "authorization-1")),
                AuthRequestErrorEvent(SESSION_ID, AuthRequestErrorData("500", "error")),
                AuthRequestRetriedEvent(SESSION_ID, AuthRequestRetriedData("500", "error", 1)),
                AuthOutcomeReceivedEvent(
                    SESSION_ID,
                    AuthOutcomeReceivedData(
                        AuthOutcomeReceivedData.AuthDetails("AUTHORIZED", "rrn-1", "auth-code-1"),
                        InstrumentDetails.CardDetails("VISA", "1234", "411111"))),
                CiNotifiedEvent(SESSION_ID),
                CiNtfErrorEvent(SESSION_ID, CiNtfErrorData("500", "error")),
                CiNtfRetriedEvent(SESSION_ID, CiNtfRetriedData(1, "500", "error")),
                CiNotifyExpiredEvent(SESSION_ID),
                EcommNotifyEvent(SESSION_ID),
                EcommNtfErrorEvent(SESSION_ID, EcommNtfErrorData("500", "error")),
                EcommNtfRetriedEvent(SESSION_ID, EcommNtfRetriedData(1, "500", "error")),
                EcommNtfExpiredEvent(SESSION_ID),
                ErrorEvent(SESSION_ID, ErrorData("INITIALIZED", "500", "error")),
                ExpiredEvent(SESSION_ID, ExpiredData("INITIALIZED")),
                ReconciledEvent(SESSION_ID))
    }
}
