package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class Info(endpoint: String, type: ParameterType) : BfiEndpoint("Info/$endpoint", type) {

    data object VersionMajor : Info("VersionMajor", ParameterType.INT)

    data object VersionMinor : Info("VersionMinor", ParameterType.INT)

    data object SecondsSinceLastUpdate : Info("SecondsSinceLastUpdate", ParameterType.FLOAT_POS)

    data object DeviceConnected : Info("DeviceConnected", ParameterType.BOOL)

    data object BatterySupported : Info("BatterySupported", ParameterType.BOOL)

    data object BatteryLevel : Info("BatteryLevel", ParameterType.FLOAT_POS)
}