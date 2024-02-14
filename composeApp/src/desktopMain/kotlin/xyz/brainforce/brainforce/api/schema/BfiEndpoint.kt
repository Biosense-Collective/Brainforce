package xyz.brainforce.brainforce.api.schema

import xyz.brainforce.brainforce.api.data.ParameterType

sealed class BfiEndpoint(
    endpoint: String,
    val type: ParameterType,
) {
    val endpoint: String = "/avatar/parameters/BFI/$endpoint"
}