package xyz.brainforce.brainforce.api.handler

import brainflow.BoardShim
import brainflow.BrainFlowError
import xyz.brainforce.brainforce.api.action.DeviceHealthCheck
import xyz.brainforce.brainforce.api.util.Logger

class DeviceHealthHandler(
    private val lifeCycle: DeviceLifeCycleHandlers = DeviceLifeCycleHandlers()
) : DeviceHealthCheck {

    private var timestamp = 0.0
    private var failureCount = 0

    override suspend fun healthCheck(board: BoardShim): Boolean {
        if (failureCount >= 5) {
            lifeCycle.runErrorEvent("Device timed out...")
            failureCount = 0
            return false
        }

        return try {
            val idx = BoardShim.get_timestamp_channel(board.board_id)
            val newTimestamp = board.get_current_board_data(1)[idx][0]

            if (newTimestamp == timestamp) {
                Logger.debug("Timestamp delay detected!")
                failureCount++
            } else {
                Logger.debug("Device health OK")
                timestamp = newTimestamp
            }
            true
        } catch (e: BrainFlowError) {
            lifeCycle.runErrorEvent("Connection failed...")
        }
    }
}