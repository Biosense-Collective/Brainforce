package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class Biometrics(endpoint: String, type: ParameterType) : BfiEndpoint("Biometrics/$endpoint", type) {

    data object Supported : Biometrics("Supported", ParameterType.BOOL)

    data object HeartBeatsPerSecond : Biometrics("HeartBeatsPerSecond", ParameterType.FLOAT)

    data object HeartBeatsPerMinute : Biometrics("HeartBeatsPerMinute", ParameterType.INT)

    data object OxygenPercent : Biometrics("OxygenPercent", ParameterType.FLOAT_POS)

    data object BreathsPerSecond : Biometrics("BreathsPerSecond", ParameterType.FLOAT_POS)

    data object BreathsPerMinute : Biometrics("BreathsPerMinute", ParameterType.INT)
}