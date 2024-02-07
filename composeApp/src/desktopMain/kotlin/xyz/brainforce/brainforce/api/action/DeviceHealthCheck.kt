package xyz.brainforce.brainforce.api.action

import brainflow.BoardShim

fun interface DeviceHealthCheck {

    suspend fun healthCheck(board: BoardShim): Boolean
}