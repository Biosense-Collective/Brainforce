package xyz.brainforce.brainforce.api.handler

import brainflow.BoardShim
import kotlinx.coroutines.cancelAndJoin
import xyz.brainforce.brainforce.api.util.Logger

data class DeviceLifeCycleHandlers(
    private val onError: suspend () -> Unit = {},
    private val onConnect: suspend () -> Unit = {},
    private val onTrackingStarted: suspend () -> Unit = {},
    private val onDisconnect: suspend () -> Unit = {},
) {
    suspend fun runConnectEvent(message: String): Boolean {
        Logger.info(message)
        onConnect()
        return true
    }

    suspend fun runTrackingStartedEvent(): Boolean {
        Logger.info("Tracking started!")
        onTrackingStarted()
        return true
    }

    suspend fun runDisconnectEvent(): Boolean {
        BoardShim.release_all_sessions()
        Logger.info("Disconnected from neural interface")
        onDisconnect()
        return false
    }

    suspend fun runErrorEvent(message: String): Boolean {
        Logger.warn("Encountered an error: $message")
        onError()
        return false
    }
}
