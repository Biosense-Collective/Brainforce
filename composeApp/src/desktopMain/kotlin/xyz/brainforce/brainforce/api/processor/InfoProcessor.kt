package xyz.brainforce.brainforce.api.processor

import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.data.DataSegment
import xyz.brainforce.brainforce.api.data.osc.BoolValue
import xyz.brainforce.brainforce.api.data.osc.FloatValue
import xyz.brainforce.brainforce.api.data.osc.IntValue
import xyz.brainforce.brainforce.api.provider.BoardChannelProvider
import xyz.brainforce.brainforce.api.schema.Info

@Singleton
class InfoProcessor(
    private val channelProvider: BoardChannelProvider,
) : Processor {

    private val majorVersion: IntValue = IntValue(1)

    private val minorVersion: IntValue = IntValue(0)

    private val secondsSinceLastUpdate: IntValue = IntValue(0)

    private val batterySupported: BoolValue
        get() = channelProvider
            .batterySupported()
            .let(::BoolValue)

    override fun process(data: Array<DoubleArray>): DataSegment = DataSegment(
        Info.VersionMajor to majorVersion,
        Info.VersionMinor to minorVersion,
        Info.SecondsSinceLastUpdate to secondsSinceLastUpdate,
        Info.DeviceConnected to deviceConnected(true),
        Info.BatterySupported to batterySupported,
        Info.BatteryLevel to batteryLevel(data)
    )

    override fun reset(): DataSegment = DataSegment(
        Info.DeviceConnected to deviceConnected(false)
    )

    private fun deviceConnected(connected: Boolean): BoolValue =
        BoolValue(connected)

    private fun batteryLevel(data: Array<DoubleArray>): FloatValue {
        val batteryChannel = channelProvider
            .batteryChannel()
            ?: return FloatValue(1f)

        return data[batteryChannel][0]
            .toFloat()
            .let(::FloatValue)
    }
}