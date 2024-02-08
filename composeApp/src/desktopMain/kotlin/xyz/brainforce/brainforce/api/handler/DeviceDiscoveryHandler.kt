package xyz.brainforce.brainforce.api.handler

import brainflow.BoardShim
import brainflow.BrainFlowError
import kotlinx.coroutines.delay
import xyz.brainforce.brainforce.api.action.DeviceDiscovery
import xyz.brainforce.brainforce.api.util.Logger
import kotlin.time.Duration

class DeviceDiscoveryHandler(
    private val lifeCycle: DeviceLifeCycleHandlers = DeviceLifeCycleHandlers(),
) : DeviceDiscovery {

    override suspend fun discover(board: BoardShim, time: Duration): Boolean = try {
        // Disconnect all existing devices
        BoardShim.release_all_sessions()
        // Prepare new stream from interface
        Logger.info("Attempting connection...")
        board.prepare_session()
        board.start_stream()
        // If no exception is thrown, run connection event
        lifeCycle.runConnectEvent("Connected to neural interface (wait ${time.inWholeSeconds}s)")
        delay(time)
        // Run tracking started event after warp-up period
        lifeCycle.runTrackingStartedEvent()
    } catch (e: BrainFlowError) {
        lifeCycle.runErrorEvent("Connection could not be established...")
    }
}