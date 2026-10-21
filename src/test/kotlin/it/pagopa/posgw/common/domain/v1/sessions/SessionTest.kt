package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CardDetails
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
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
import it.pagopa.posgw.common.domain.v1.BrokerId
import it.pagopa.posgw.common.domain.v1.ChannelId
import it.pagopa.posgw.common.domain.v1.CorrelationId
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertSame
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class SessionTest {
    @Test
    fun `empty session initializes and ignores events received before initialization`() {
        val empty = EmptySession()
        assertSame(empty, empty.applyEvent(transactionActivatedEvent()))

        val event = initializedEvent()
        val actual = assertIs<Initialized>(empty.applyEvent(event))

        assertEquals(SessionStatus.INITIALIZED, actual.status)
        assertEquals(SessionId(SESSION_UUID), actual.sessionId)
        assertEquals(CorrelationId.of(CORRELATION_ID), actual.correlationId)
        assertEquals(event.data, actual.initializeData)
        assertEquals(event.timestamp, actual.createdAt)
        assertEquals(event.timestamp, actual.updatedAt)
    }

    @Test
    fun `initialized session handles debt transaction error and expiration events`() {
        val initialized = initializedState()

        val debtEvent = debtPositionCreatedEvent()
        val debtState = assertIs<DebtPositionCreated>(initialized.applyEvent(debtEvent))
        assertEquals(SessionStatus.DEBT_POSITION_CREATED, debtState.status)
        assertEquals(debtEvent.data, debtState.debtPositionData)
        assertEquals(debtEvent.timestamp, debtState.updatedAt)

        val transactionEvent = transactionActivatedEvent()
        val transactionState =
            assertIs<TransactionActivated>(initialized.applyEvent(transactionEvent))
        assertEquals(SessionStatus.TRANSACTION_ACTIVATED, transactionState.status)
        assertEquals(transactionEvent.data, transactionState.transactionActivateData)
        assertEquals(transactionEvent.timestamp, transactionState.updatedAt)

        val errorEvent = errorEvent(SessionStatus.INITIALIZED)
        val errorState = assertIs<Error>(initialized.applyEvent(errorEvent))
        assertSame(initialized, errorState.sessionBeforeError)
        assertEquals(SessionStatus.ERROR, errorState.status)
        assertEquals(errorEvent.data, errorState.data)
        assertEquals(errorEvent.data, errorState.errorData)
        assertEquals(errorEvent.timestamp, errorState.updatedAt)
        assertEquals(initialized.sessionId, errorState.sessionId)
        assertEquals(initialized.createdAt, errorState.createdAt)

        val expiredEvent = expiredEvent(SessionStatus.INITIALIZED)
        val expiredState = assertIs<Expired>(initialized.applyEvent(expiredEvent))
        assertSame(initialized, expiredState.sessionBeforeExpired)
        assertEquals(SessionStatus.EXPIRED, expiredState.status)
        assertEquals(expiredEvent.data, expiredState.data)
        assertEquals(expiredEvent.data, expiredState.expiredData)
        assertEquals(expiredEvent.timestamp, expiredState.updatedAt)

        assertSame(initialized, initialized.applyEvent(initializedEvent()))
        assertSame(initialized, initialized.applyEvent(authRequestedEvent()))
    }

    @Test
    fun `initialization rejects a malformed correlation id`() {
        val event = initializedEvent(correlationId = "not-a-uuid")

        kotlin.test.assertFailsWith<IllegalArgumentException> {
            EmptySession().applyEvent(event)
        }
    }

    @Test
    fun `transaction activated from debt position retains previous state and data`() {
        val debtEvent = debtPositionCreatedEvent()
        val debtState = assertIs<DebtPositionCreated>(initializedState().applyEvent(debtEvent))
        val transactionEvent = transactionActivatedEvent()
        val transactionState =
            assertIs<TransactionActivated>(debtState.applyEvent(transactionEvent))
        assertEquals(SessionStatus.TRANSACTION_ACTIVATED, transactionState.status)
        assertEquals(transactionEvent.data, transactionState.transactionActivateData)
        assertSame(debtState, transactionState.sessionBeforeTransactionAct)
        val previousState =
            assertIs<DebtPositionCreated>(transactionState.sessionBeforeTransactionAct)
        assertEquals(debtEvent.data, previousState.debtPositionData)
        assertEquals(debtState.sessionId, transactionState.sessionId)
        assertEquals(debtState.createdAt, transactionState.createdAt)
        assertEquals(transactionEvent.timestamp, transactionState.updatedAt)
    }

    @Test
    fun `debt position created session handles error or expiration and ignores other events`() {
        val debtState = debtPositionCreatedState()

        val errorEvent = errorEvent(SessionStatus.DEBT_POSITION_CREATED)
        val errorState = assertIs<Error>(debtState.applyEvent(errorEvent))
        assertSame(debtState, errorState.sessionBeforeError)
        assertEquals(errorEvent.data, errorState.data)
        assertEquals(errorEvent.timestamp, errorState.updatedAt)

        val expiredEvent = expiredEvent(SessionStatus.DEBT_POSITION_CREATED)
        val expiredState = assertIs<Expired>(debtState.applyEvent(expiredEvent))
        assertSame(debtState, expiredState.sessionBeforeExpired)
        assertEquals(expiredEvent.data, expiredState.data)
        assertEquals(expiredEvent.timestamp, expiredState.updatedAt)

        assertSame(debtState, debtState.applyEvent(debtPositionCreatedEvent()))
        assertSame(debtState, debtState.applyEvent(authRequestedEvent()))
    }

    @Test
    fun `transaction activated session accepts authorization and terminal events`() {
        val transactionState = transactionActivatedState()
        assertIs<Initialized>(transactionState.sessionBeforeTransactionAct)

        val requestedEvent = authRequestedEvent()
        val requestedState =
            assertIs<AuthorizationRequested>(transactionState.applyEvent(requestedEvent))
        assertPreviousSession(requestedState, transactionState)
        assertEquals(SessionStatus.AUTH_REQUESTED, requestedState.status)
        assertEquals(requestedEvent.data, requestedState.authorizationRequestData)
        assertSame(transactionState, requestedState.sessionBeforeAuthReq)
        assertEquals(requestedEvent.timestamp, requestedState.updatedAt)
        assertEquals(
            transactionState.transactionActivateData, requestedState.transactionActivateData)

        val requestErrorEvent = authRequestErrorEvent()
        val requestErrorState =
            assertIs<AuthorizationRequestError>(transactionState.applyEvent(requestErrorEvent))
        assertPreviousSession(requestErrorState, transactionState)
        assertEquals(SessionStatus.AUTH_REQUEST_ERROR, requestErrorState.status)
        assertEquals(requestErrorEvent.timestamp, requestErrorState.updatedAt)
        assertEquals(
            transactionState.transactionActivateData, requestErrorState.transactionActivateData)

        val errorEvent = errorEvent(SessionStatus.TRANSACTION_ACTIVATED)
        val errorState = assertIs<Error>(transactionState.applyEvent(errorEvent))
        assertSame(transactionState, errorState.sessionBeforeError)
        assertEquals(errorEvent.data, errorState.data)

        val expiredEvent = expiredEvent(SessionStatus.TRANSACTION_ACTIVATED)
        val expiredState = assertIs<Expired>(transactionState.applyEvent(expiredEvent))
        assertSame(transactionState, expiredState.sessionBeforeExpired)
        assertEquals(expiredEvent.data, expiredState.data)

        assertSame(transactionState, transactionState.applyEvent(transactionActivatedEvent()))
        assertSame(transactionState, transactionState.applyEvent(authOutcomeReceivedEvent()))
    }

    @Test
    fun `authorization requested session completes authorization or expires to reconcile`() {
        val requestedState = authorizationRequestedState()

        val authorizedEvent = authOutcomeReceivedEvent("AUTHORIZED")
        val completedState =
            assertIs<AuthorizationCompleted>(requestedState.applyEvent(authorizedEvent))
        assertPreviousSession(completedState, requestedState)
        assertEquals(SessionStatus.AUTH_COMPLETED, completedState.status)
        assertEquals(authorizedEvent.data, completedState.authorizationData)
        assertEquals(authorizedEvent.timestamp, completedState.updatedAt)
        assertEquals(
            requestedState.authorizationRequestData, completedState.authorizationRequestData)

        val refusedState =
            assertIs<AuthorizationCompleted>(
                requestedState.applyEvent(authOutcomeReceivedEvent("REFUSED")),
            )
        assertEquals("REFUSED", refusedState.authorizationData.authDetails.outcome)

        val expiredEvent = expiredEvent(SessionStatus.AUTH_REQUESTED)
        val expiredToReconcile =
            assertIs<ExpiredToReconcile>(requestedState.applyEvent(expiredEvent))
        assertPreviousSession(expiredToReconcile, requestedState)
        assertSame(requestedState, expiredToReconcile.sessionBeforeExpRecon)
        assertEquals(SessionStatus.EXPIRED_TO_RECONCILE, expiredToReconcile.status)
        assertEquals(expiredEvent.data, expiredToReconcile.data)
        assertEquals(expiredEvent.timestamp, expiredToReconcile.updatedAt)
        assertEquals(
            requestedState.transactionActivateData, expiredToReconcile.transactionActivateData)

        assertSame(requestedState, requestedState.applyEvent(authRequestedEvent()))
        assertSame(requestedState, requestedState.applyEvent(authRequestErrorEvent()))
    }

    @Test
    fun `authorization request error session accepts request retry error and expiration`() {
        val requestErrorState = authorizationRequestedErrorState()

        val requestedEvent = authRequestedEvent()
        val requestedState =
            assertIs<AuthorizationRequested>(requestErrorState.applyEvent(requestedEvent))
        assertPreviousSession(requestedState, requestErrorState)
        assertEquals(requestedEvent.data, requestedState.authorizationRequestData)
        assertEquals(requestedEvent.timestamp, requestedState.updatedAt)

        val retriedEvent = authRequestRetriedEvent()
        val retriedState =
            assertIs<AuthorizationRequestError>(requestErrorState.applyEvent(retriedEvent))
        assertPreviousSession(retriedState, requestErrorState)
        assertEquals(SessionStatus.AUTH_REQUEST_ERROR, retriedState.status)
        assertEquals(retriedEvent.data, retriedState.authRequestRetriedData)
        assertEquals(retriedEvent.timestamp, retriedState.updatedAt)
        assertEquals(
            requestErrorState.transactionActivateData, retriedState.transactionActivateData)

        val errorEvent = errorEvent(SessionStatus.AUTH_REQUEST_ERROR)
        val errorState = assertIs<Error>(requestErrorState.applyEvent(errorEvent))
        assertSame(requestErrorState, errorState.sessionBeforeError)
        assertEquals(errorEvent.data, errorState.data)

        val expiredEvent = expiredEvent(SessionStatus.AUTH_REQUEST_ERROR)
        val expiredToReconcile =
            assertIs<ExpiredToReconcile>(requestErrorState.applyEvent(expiredEvent))
        assertPreviousSession(expiredToReconcile, requestErrorState)
        assertEquals(expiredEvent.data, expiredToReconcile.data)
        assertEquals(expiredEvent.timestamp, expiredToReconcile.updatedAt)

        assertSame(requestErrorState, requestErrorState.applyEvent(authOutcomeReceivedEvent()))
    }

    @Test
    fun `authorization completed session notifies CI or stores CI notification error`() {
        val completedState = authorizationCompletedState()

        val notifiedEvent = CiNotifiedEvent(SESSION_UUID)
        val notifiedState = assertIs<CiNotified>(completedState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, completedState)
        assertSame(completedState, notifiedState.sessionBeforeCiNtf)
        assertEquals(SessionStatus.CI_NOTIFIED, notifiedState.status)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)
        assertEquals(completedState.authorizationData, notifiedState.authorizationData)

        val errorEvent = ciNtfErrorEvent()
        val errorState = assertIs<CiNotifyError>(completedState.applyEvent(errorEvent))
        assertPreviousSession(errorState, completedState)
        assertEquals(SessionStatus.CI_NTF_ERROR, errorState.status)
        assertEquals(errorEvent.data, errorState.ciNtfErrorData)
        assertNull(errorState.ciNtfRetriedData)
        assertEquals(errorEvent.timestamp, errorState.updatedAt)

        assertSame(completedState, completedState.applyEvent(authOutcomeReceivedEvent()))
    }

    @Test
    fun `CI notification error state handles notification retry and expiration variants`() {
        val errorState = ciNotifyErrorState()

        val notifiedEvent = CiNotifiedEvent(SESSION_UUID)
        val notifiedState = assertIs<CiNotified>(errorState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, errorState)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)

        val retriedEvent = ciNtfRetriedEvent()
        val retriedState = assertIs<CiNotifyError>(errorState.applyEvent(retriedEvent))
        assertPreviousSession(retriedState, errorState)
        assertEquals(SessionStatus.CI_NTF_ERROR, retriedState.status)
        assertEquals(errorState.ciNtfErrorData, retriedState.ciNtfErrorData)
        assertEquals(retriedEvent.data, retriedState.ciNtfRetriedData)
        assertEquals(retriedEvent.timestamp, retriedState.updatedAt)

        val expiredEvent = CiNotifyExpiredEvent(SESSION_UUID)
        val expiredState = assertIs<CiNotifyExpired>(errorState.applyEvent(expiredEvent))
        assertPreviousSession(expiredState, errorState)
        assertEquals(SessionStatus.CI_NTF_EXPIRED, expiredState.status)
        assertEquals(errorState.ciNtfErrorData, expiredState.ciNtfErrorData)
        assertNull(expiredState.ciExpRetriedData)
        assertEquals(expiredEvent.timestamp, expiredState.updatedAt)

        assertSame(errorState, errorState.applyEvent(ecommNotifyEvent()))
    }

    @Test
    fun `CI notification expired state handles notification retry and session expiration`() {
        val expiredState = ciNotifyExpiredState()

        val notifiedEvent = CiNotifiedEvent(SESSION_UUID)
        val notifiedState = assertIs<CiNotified>(expiredState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, expiredState)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)

        val retriedEvent = ciNtfRetriedEvent()
        val retriedState = assertIs<CiNotifyExpired>(expiredState.applyEvent(retriedEvent))
        assertPreviousSession(retriedState, expiredState)
        assertEquals(retriedEvent.data, retriedState.ciExpRetriedData)
        assertEquals(retriedEvent.timestamp, retriedState.updatedAt)

        val sessionExpiredEvent = expiredEvent(SessionStatus.CI_NTF_EXPIRED)
        val expiredToReconcile =
            assertIs<ExpiredToReconcile>(expiredState.applyEvent(sessionExpiredEvent))
        assertPreviousSession(expiredToReconcile, expiredState)
        assertEquals(sessionExpiredEvent.data, expiredToReconcile.data)
        assertEquals(sessionExpiredEvent.timestamp, expiredToReconcile.updatedAt)

        assertSame(expiredState, expiredState.applyEvent(ecommNotifyEvent()))
    }

    @Test
    fun `CI notified state starts ecommerce notification or stores its error`() {
        val ciNotifiedState = ciNotifiedState()

        val notifiedEvent = ecommNotifyEvent()
        val notifiedState = assertIs<EcommerceNotified>(ciNotifiedState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, ciNotifiedState)
        assertSame(ciNotifiedState, notifiedState.sessionBeforeEcommNtf)
        assertEquals(SessionStatus.ECOMM_NOTIFIED, notifiedState.status)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)
        assertEquals(ciNotifiedState.authorizationData, notifiedState.authorizationData)

        val errorEvent = ecommNtfErrorEvent()
        val errorState = assertIs<EcommerceNotifyError>(ciNotifiedState.applyEvent(errorEvent))
        assertPreviousSession(errorState, ciNotifiedState)
        assertEquals(SessionStatus.ECOMM_NTF_ERROR, errorState.status)
        assertEquals(errorEvent.data, errorState.ecommNotifyErrorData)
        assertNull(errorState.ecommNotifyRetriedData)
        assertEquals(errorEvent.timestamp, errorState.updatedAt)

        assertSame(ciNotifiedState, ciNotifiedState.applyEvent(CiNotifiedEvent(SESSION_UUID)))
    }

    @Test
    fun `ecommerce notification error state handles success retry and expiration`() {
        val errorState = ecommerceNotifyErrorState()

        val notifiedEvent = ecommNotifyEvent()
        val notifiedState = assertIs<EcommerceNotified>(errorState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, errorState)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)

        val retriedEvent = ecommNtfRetriedEvent()
        val retriedState = assertIs<EcommerceNotifyError>(errorState.applyEvent(retriedEvent))
        assertPreviousSession(retriedState, errorState)
        assertEquals(SessionStatus.ECOMM_NTF_ERROR, retriedState.status)
        assertEquals(errorState.ecommNotifyErrorData, retriedState.ecommNotifyErrorData)
        assertEquals(retriedEvent.data, retriedState.ecommNotifyRetriedData)
        assertEquals(retriedEvent.timestamp, retriedState.updatedAt)

        val expiredEvent = EcommNtfExpiredEvent(SESSION_UUID)
        val expiredState = assertIs<EcommerceNotifyExpired>(errorState.applyEvent(expiredEvent))
        assertPreviousSession(expiredState, errorState)
        assertEquals(SessionStatus.ECOMM_NTF_EXPIRED, expiredState.status)
        assertEquals(errorState.ecommNotifyErrorData, expiredState.ecommNotifyErrorData)
        assertNull(expiredState.ecommNtfExpiredRetriedData)
        assertEquals(expiredEvent.timestamp, expiredState.updatedAt)

        assertSame(errorState, errorState.applyEvent(CiNotifiedEvent(SESSION_UUID)))
    }

    @Test
    fun `ecommerce notification expired state handles success and retry`() {
        val expiredState = ecommerceNotifyExpiredState()

        val notifiedEvent = ecommNotifyEvent()
        val notifiedState = assertIs<EcommerceNotified>(expiredState.applyEvent(notifiedEvent))
        assertPreviousSession(notifiedState, expiredState)
        assertEquals(notifiedEvent.timestamp, notifiedState.updatedAt)

        val retriedEvent = ecommNtfRetriedEvent()
        val retriedState = assertIs<EcommerceNotifyExpired>(expiredState.applyEvent(retriedEvent))
        assertPreviousSession(retriedState, expiredState)
        assertEquals(retriedEvent.data, retriedState.ecommNtfExpiredRetriedData)
        assertEquals(retriedEvent.timestamp, retriedState.updatedAt)

        assertSame(expiredState, expiredState.applyEvent(CiNotifiedEvent(SESSION_UUID)))
    }

    @Test
    fun `ecommerce notified state ignores duplicate notifications and errors`() {
        val state = ecommerceNotifiedState()

        assertSame(state, state.applyEvent(ecommNotifyEvent()))
        assertSame(state, state.applyEvent(ecommNtfErrorEvent()))
        assertSame(state, state.applyEvent(ecommNtfRetriedEvent()))
    }

    @Test
    fun `error state is terminal for subsequent events`() {
        val state = errorState()

        assertSame(state, state.applyEvent(errorEvent(SessionStatus.ERROR)))
        assertSame(state, state.applyEvent(expiredEvent(SessionStatus.ERROR)))
        assertSame(state, state.applyEvent(ReconciledEvent(SESSION_UUID)))
    }

    @Test
    fun `expired state is terminal for subsequent events`() {
        val state = expiredState()

        assertSame(state, state.applyEvent(expiredEvent(SessionStatus.EXPIRED)))
        assertSame(state, state.applyEvent(ReconciledEvent(SESSION_UUID)))
    }

    @Test
    fun `expired to reconcile state reconciles and ignores unrelated events`() {
        val state = expiredToReconcileState()

        val event = ReconciledEvent(SESSION_UUID)
        val reconciled = assertIs<Reconciled>(state.applyEvent(event))
        assertEquals(SessionStatus.RECONCILED, reconciled.status)
        assertEquals(event.timestamp, reconciled.updatedAt)
        assertEquals(state.data, reconciled.data)
        assertEquals(state.sessionId, reconciled.sessionId)
        assertPreviousSession(reconciled, state)

        assertSame(state, state.applyEvent(authRequestedEvent()))
    }

    @Test
    fun `reconciled state ignores later events`() {
        val state = reconciledState()

        assertSame(state, state.applyEvent(ReconciledEvent(SESSION_UUID)))
        assertSame(state, state.applyEvent(errorEvent(SessionStatus.RECONCILED)))
    }

    @Test
    fun `replays an event stream into the expected session state`() {
        val initializedEvent = initializedEvent()
        val debtEvent = debtPositionCreatedEvent()
        val transactionEvent = transactionActivatedEvent()
        val authorizationRequestedEvent = authRequestedEvent()
        val authorizationOutcomeEvent = authOutcomeReceivedEvent()
        val ciNotifiedEvent = CiNotifiedEvent(SESSION_UUID)
        val ecommerceNotifiedEvent = ecommNotifyEvent()
        val initialSession: Session = EmptySession()
        val events: Flux<SessionEvent<*>> =
            Flux.fromIterable(
                listOf<SessionEvent<*>>(
                    initializedEvent,
                    debtEvent,
                    transactionEvent,
                    authorizationRequestedEvent,
                    authorizationOutcomeEvent,
                    ciNotifiedEvent,
                    ecommerceNotifiedEvent,
                ),
            )
        val actual: Mono<Session> =
            events.reduce(initialSession) { session, event ->
                session.applyEvent(event)
            }

        StepVerifier.create(actual)
            .assertNext { emitted ->
                val finalSession = assertIs<EcommerceNotified>(emitted)
                assertEquals(SessionStatus.ECOMM_NOTIFIED, finalSession.status)
                assertEquals(SessionId(SESSION_UUID), finalSession.sessionId)
                assertEquals(CorrelationId.of(CORRELATION_ID), finalSession.correlationId)
                assertEquals(initializedEvent.data, finalSession.initializeData)
                assertEquals(transactionEvent.data, finalSession.transactionActivateData)
                assertEquals(
                    authorizationRequestedEvent.data,
                    finalSession.authorizationRequestData,
                )
                assertEquals(authorizationOutcomeEvent.data, finalSession.authorizationData)
                val ciNotified = assertIs<CiNotified>(finalSession.sessionBeforeTransactionAct)
                val authorizationCompleted =
                    assertIs<AuthorizationCompleted>(ciNotified.sessionBeforeTransactionAct)
                val authorizationRequested =
                    assertIs<AuthorizationRequested>(
                        authorizationCompleted.sessionBeforeTransactionAct,
                    )
                val transactionActivated =
                    assertIs<TransactionActivated>(
                        authorizationRequested.sessionBeforeTransactionAct,
                    )
                val debtPositionCreated =
                    assertIs<DebtPositionCreated>(
                        transactionActivated.sessionBeforeTransactionAct,
                    )
                assertEquals(debtEvent.data, debtPositionCreated.debtPositionData)
                assertEquals(ecommerceNotifiedEvent.timestamp, finalSession.updatedAt)
            }
            .verifyComplete()
    }

    private fun initializedEvent(correlationId: String = CORRELATION_ID) =
        SessionInitializedEvent(
            sessionId = SESSION_UUID,
            data =
                SessionInitializedData(
                    psp =
                        SessionInitializedData.Psp(
                            "psp-1", BrokerId("broker-1"), ChannelId("channel-1")),
                    creditorInstitution =
                        SessionInitializedData.CreditorInstitution(
                            "fiscal-code",
                            "office-1",
                            "workstation-1",
                        ),
                    terminalIds = listOf("terminal-1", "terminal-2"),
                    correlationId = correlationId,
                ),
        )

    private fun debtPositionCreatedEvent() =
        DeptPositionCreatedEvent(
            sessionId = SESSION_UUID,
            data =
                DebtPositionCreatedData(
                    listOf(DebtPosition("iupd-1", "rpt-1", "hashed-fiscal-code")),
                ),
        )

    private fun transactionActivatedEvent() =
        TransactionActivatedEvent(
            sessionId = SESSION_UUID,
            data =
                TransactionActivatedData(
                    transactionId = "transaction-1",
                    activationInfo =
                        listOf(
                            TransactionActivatedData.ActivationInfo(
                                paymentToken = "payment-token-1",
                                rptId = "rpt-1",
                                amount = 1200L,
                                outcome = "OK",
                                reason = null,
                            ),
                        ),
                ),
        )

    private fun authRequestedEvent() =
        AuthRequestedEvent(
            sessionId = SESSION_UUID,
            data = AuthRequestedData("terminal-1", "authorization-1"),
        )

    private fun authRequestErrorEvent() =
        AuthRequestErrorEvent(
            sessionId = SESSION_UUID,
            data = AuthRequestErrorData("AUTH-001", "authorization request failed"),
        )

    private fun authRequestRetriedEvent() =
        AuthRequestRetriedEvent(
            sessionId = SESSION_UUID,
            data = AuthRequestRetriedData("AUTH-001", "retrying authorization", 2),
        )

    private fun authOutcomeReceivedEvent(outcome: String = "AUTHORIZED") =
        AuthOutcomeReceivedEvent(
            sessionId = SESSION_UUID,
            data =
                AuthOutcomeReceivedData(
                    authDetails =
                        AuthOutcomeReceivedData.AuthDetails(
                            outcome = outcome,
                            rrn = "rrn-1",
                            authorizationCode = "auth-code-1",
                        ),
                    instrumentDetails = CardDetails("VISA", "1234", "411111"),
                ),
        )

    private fun ciNtfErrorEvent() =
        CiNtfErrorEvent(
            sessionId = SESSION_UUID,
            data = CiNtfErrorData("CI-001", "CI notification failed"),
        )

    private fun ciNtfRetriedEvent() =
        CiNtfRetriedEvent(
            sessionId = SESSION_UUID,
            data = CiNtfRetriedData(2, "CI-001", "retrying CI notification"),
        )

    private fun ecommNtfErrorEvent() =
        EcommNtfErrorEvent(
            sessionId = SESSION_UUID,
            data = EcommNtfErrorData("ECOMM-001", "eCommerce notification failed"),
        )

    private fun ecommNtfRetriedEvent() =
        EcommNtfRetriedEvent(
            sessionId = SESSION_UUID,
            data = EcommNtfRetriedData(3, "ECOMM-001", "retrying eCommerce notification"),
        )

    private fun ecommNotifyEvent() = EcommNotifyEvent(SESSION_UUID)

    private fun errorEvent(status: SessionStatus) =
        ErrorEvent(
            sessionId = SESSION_UUID,
            data = ErrorData(status.name, "ERR-001", "session error"),
        )

    private fun expiredEvent(status: SessionStatus) =
        ExpiredEvent(sessionId = SESSION_UUID, data = ExpiredData(status.name))

    private fun initializedState() =
        assertIs<Initialized>(EmptySession().applyEvent(initializedEvent()))

    private fun debtPositionCreatedState() =
        assertIs<DebtPositionCreated>(initializedState().applyEvent(debtPositionCreatedEvent()))

    private fun transactionActivatedState() =
        assertIs<TransactionActivated>(initializedState().applyEvent(transactionActivatedEvent()))

    private fun authorizationRequestedState() =
        assertIs<AuthorizationRequested>(
            transactionActivatedState().applyEvent(authRequestedEvent()),
        )

    private fun authorizationRequestedErrorState() =
        assertIs<AuthorizationRequestError>(
            transactionActivatedState().applyEvent(authRequestErrorEvent()),
        )

    private fun authorizationCompletedState() =
        assertIs<AuthorizationCompleted>(
            authorizationRequestedState().applyEvent(authOutcomeReceivedEvent()),
        )

    private fun ciNotifiedState() =
        assertIs<CiNotified>(
            authorizationCompletedState().applyEvent(CiNotifiedEvent(SESSION_UUID)))

    private fun ciNotifyErrorState() =
        assertIs<CiNotifyError>(authorizationCompletedState().applyEvent(ciNtfErrorEvent()))

    private fun ciNotifyExpiredState() =
        assertIs<CiNotifyExpired>(
            ciNotifyErrorState().applyEvent(CiNotifyExpiredEvent(SESSION_UUID)))

    private fun ecommerceNotifiedState() =
        assertIs<EcommerceNotified>(ciNotifiedState().applyEvent(ecommNotifyEvent()))

    private fun ecommerceNotifyErrorState() =
        assertIs<EcommerceNotifyError>(ciNotifiedState().applyEvent(ecommNtfErrorEvent()))

    private fun ecommerceNotifyExpiredState() =
        assertIs<EcommerceNotifyExpired>(
            ecommerceNotifyErrorState().applyEvent(EcommNtfExpiredEvent(SESSION_UUID)),
        )

    private fun errorState() =
        assertIs<Error>(initializedState().applyEvent(errorEvent(SessionStatus.INITIALIZED)))

    private fun expiredState() =
        assertIs<Expired>(initializedState().applyEvent(expiredEvent(SessionStatus.INITIALIZED)))

    private fun expiredToReconcileState() =
        assertIs<ExpiredToReconcile>(
            authorizationRequestedState().applyEvent(expiredEvent(SessionStatus.AUTH_REQUESTED)),
        )

    private fun reconciledState() =
        assertIs<Reconciled>(
            expiredToReconcileState().applyEvent(ReconciledEvent(SESSION_UUID)),
        )

    private fun assertPreviousSession(
        actual: BaseSessionWithTransactionAct,
        expected: BaseSessionInitialized,
    ) {
        assertSame(expected, actual.sessionBeforeTransactionAct)
    }

    private companion object {
        val SESSION_UUID: UUID = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        const val CORRELATION_ID = "c0a8017e-89ab-4cde-8123-456789abcdef"
    }
}
