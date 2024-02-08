package xyz.brainforce.brainforce.api.util

import brainflow.BoardShim
import brainflow.LogLevels

object Logger {

    fun info(message: String) = BoardShim.log_message(LogLevels.LEVEL_INFO, message)

    fun warn(message: String) = BoardShim.log_message(LogLevels.LEVEL_WARN, message)

    fun debug(message: String) = BoardShim.log_message(LogLevels.LEVEL_DEBUG, message)
}