package xyz.brainforce.brainforce.api.service

import brainflow.BoardShim
import brainflow.BrainFlowError
import brainflow.BrainFlowInputParams
import brainflow.LogLevels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.brainforce.brainforce.api.action.DeviceDiscovery
import xyz.brainforce.brainforce.api.action.DeviceHealthCheck
import xyz.brainforce.brainforce.api.handler.DeviceDiscoveryHandler
import xyz.brainforce.brainforce.api.handler.DeviceHealthHandler
import xyz.brainforce.brainforce.api.handler.DeviceLifeCycleHandlers
import xyz.brainforce.brainforce.api.util.Logger
import kotlin.time.Duration



class ConnectionService(
    private val board: BoardShim,
    private val time: Duration,
    private val discovery: DeviceDiscovery,
    private val deviceHealth: DeviceHealthCheck,
    private val deviceLifeCycleHandler: DeviceLifeCycleHandlers = DeviceLifeCycleHandlers(),
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

    private suspend fun initialize() {
        while (true) {
            if (!connected) {
                Logger.debug("Running device discovery")
                connected = discovery.discover(board, time)
            }

            if (connected) {
                Logger.debug("Running device health check")
                connected = deviceHealth.healthCheck(board)
            }

            delay(1000)
        }
    }
}

fun runMain() {
    val debug = true

    if (debug) {
        BoardShim.set_log_level(LogLevels.LEVEL_DEBUG)
    }

    val boardId = 38
    val args = BrainFlowInputParams().apply {
        _ip_port = 0
        serial_port = ""
        mac_address = ""
        other_info = ""
        serial_number = ""
        ip_address = ""
        ip_protocol = 0
        timeout = 5
        file = ""
        master_board = boardId
    }

    val shim = BoardShim(boardId, args)
    val discovery = DeviceDiscoveryHandler()
    val health = DeviceHealthHandler()
    val service = ConnectionService(
        shim,
        Duration.parse("3s"),
        discovery,
        health,
    )

    service.enable()
    Thread.currentThread().join()
}