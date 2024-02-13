package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class NeuroFeedback(endpoint: String, type: ParameterType) : BfiEndpoint("NeuroFB/$endpoint", type) {

    data object FocusLeft : NeuroFeedback("FocusLeft", ParameterType.FLOAT)

    data object FocusLeftPos : NeuroFeedback("FocusLeftPos", ParameterType.FLOAT_POS)

    data object FocusRight : NeuroFeedback("FocusRight", ParameterType.FLOAT)

    data object FocusRightPos : NeuroFeedback("FocusRightPos", ParameterType.FLOAT_POS)

    data object FocusAvg : NeuroFeedback("FocusAvg", ParameterType.FLOAT)

    data object FocusAvgPos : NeuroFeedback("FocusAvgPos", ParameterType.FLOAT_POS)

    data object RelaxLeft : NeuroFeedback("RelaxLeft", ParameterType.FLOAT)

    data object RelaxLeftPos : NeuroFeedback("RelaxLeftPos", ParameterType.FLOAT_POS)

    data object RelaxRight : NeuroFeedback("RelaxRight", ParameterType.FLOAT)

    data object RelaxRightPos : NeuroFeedback("RelaxRightPos", ParameterType.FLOAT_POS)

    data object RelaxAvg : NeuroFeedback("RelaxAvg", ParameterType.FLOAT)

    data object RelaxAvgPos : NeuroFeedback("RelaxAvgPos", ParameterType.FLOAT_POS)
}
