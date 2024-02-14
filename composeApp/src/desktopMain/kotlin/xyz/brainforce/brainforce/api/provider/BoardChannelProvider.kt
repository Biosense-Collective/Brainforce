package xyz.brainforce.brainforce.api.provider

import brainflow.BoardShim
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.manager.BrainforceConfigManager

@Singleton
class BoardChannelProvider(
    private val bfiConfigManager: BrainforceConfigManager,
) {

    private val boardId: Int
        get() = bfiConfigManager
            .getConfig()
            .boardId

    fun timestampChannel(): Int = BoardShim
        .get_timestamp_channel(boardId)

    fun ecgChannels(): IntArray = BoardShim
        .get_ecg_channels(boardId)

    fun eegChannels(): IntArray = BoardShim
        .get_eeg_channels(boardId)

    fun edaChannels(): IntArray = BoardShim
        .get_eda_channels(boardId)

    fun accelChannels(): IntArray = BoardShim
        .get_accel_channels(boardId)

    fun gyroChannels(): IntArray = BoardShim
        .get_gyro_channels(boardId)

    fun ppgChannels(): IntArray = BoardShim
        .get_ppg_channels(boardId)

    fun batteryChannel(): Int = BoardShim
        .get_battery_channel(boardId)

    fun samplingRate(): Int = BoardShim
        .get_sampling_rate(boardId)
}