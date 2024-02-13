package xyz.brainforce.brainforce.api.util

import brainflow.BoardShim
import brainflow.LogLevels

object Logger {

    fun debug(message: String) = BoardShim.log_message(LogLevels.LEVEL_DEBUG, message)

    fun info(message: String) = BoardShim.log_message(LogLevels.LEVEL_INFO, message)

    fun warn(message: String) = BoardShim.log_message(LogLevels.LEVEL_WARN, message)

    fun error(message: String) = BoardShim.log_message(LogLevels.LEVEL_ERROR, message)

    fun fatal(message: String) = BoardShim.log_message(LogLevels.LEVEL_CRITICAL, message)

    fun setLevel(level: LogLevels) = BoardShim.set_log_level(level)
}