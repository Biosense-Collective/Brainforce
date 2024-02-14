package xyz.brainforce.brainforce.api.service

import brainflow.BoardShim
import brainflow.BrainFlowError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.action.DeviceDiscovery
import xyz.brainforce.brainforce.api.action.DeviceHealthCheck
import xyz.brainforce.brainforce.api.handler.DeviceLifeCycleHandlers
import xyz.brainforce.brainforce.api.manager.BrainforceConfigManager
import xyz.brainforce.brainforce.api.util.Logger
import kotlin.time.Duration.Companion.seconds

@Singleton
class ConnectionService(
    private val discovery: DeviceDiscovery,
    private val deviceHealth: DeviceHealthCheck,
    private val deviceLifeCycleHandler: DeviceLifeCycleHandlers,
    private val bfiConfigManager: BrainforceConfigManager,
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var main: Job? = null
    private var connected: Boolean = false

    fun enable() {
        if (main != null) {
            Logger.warn("Connection service already active!")
            return
        }

        main = scope.launch { initialize() }
    }

    fun disable() {
        if (main?.isCompleted != false) {
            Logger.warn("Connection service is not active!")
            return
        }
        // Cancel main
        scope.launch {
            connected = try {
                main?.cancelAndJoin()
                deviceLifeCycleHandler.runDisconnectEvent()
            } catch (e: BrainFlowError) {
                deviceLifeCycleHandler.runErrorEvent("Failed to release sessions!")
            }
        }
    }

    fun isConnected(): Boolean = connected

    private suspend fun initialize() {
        while (true) {
            val config = bfiConfigManager.getConfig()
            val board = config.asBoardShim()

            if (!connected) {
                Logger.debug("Running device discovery")
                connected = discovery.discover(board, config.timeout.seconds)
            }

            if (connected) {
                Logger.debug("Running device health check")
                connected = deviceHealth.healthCheck(board)
            }

            delay(1000)
        }
    }
}
