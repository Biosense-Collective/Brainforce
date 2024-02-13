package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class Addons(
    endpoint: String,
    type: ParameterType
) : BfiEndpoint("Addons/$endpoint", type) {

    data object Hueshift : Addons("Hueshift", ParameterType.FLOAT_POS)
}