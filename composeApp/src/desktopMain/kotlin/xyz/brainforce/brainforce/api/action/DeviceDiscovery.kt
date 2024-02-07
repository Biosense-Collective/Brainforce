package xyz.brainforce.brainforce.api.action

import brainflow.BoardShim
import kotlin.time.Duration

fun interface DeviceDiscovery {

    suspend fun discover(board: BoardShim, time: Duration): Boolean
}