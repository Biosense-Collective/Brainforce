package xyz.brainforce.brainforce.provider

import com.illposed.osc.transport.OSCPortOut
import org.koin.core.annotation.Singleton
import xyz.avalonxr.osc.driver.type.parameter.BiFloatParameter
import xyz.avalonxr.osc.driver.type.parameter.BoolParameter
import xyz.avalonxr.osc.driver.type.parameter.FloatParameter
import xyz.avalonxr.osc.driver.type.parameter.IntParameter
import xyz.avalonxr.osc.driver.type.parameter.Parameter
import xyz.brainforce.brainforce.api.schema.BfiEndpoint
import xyz.brainforce.brainforce.api.data.ParameterType

@Singleton
class OscParameterProvider(
    private val portProvider: PortProvider,
) {

    private val cache = mutableMapOf<OSCPortOut, MutableMap<String, Parameter<*>>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Comparable<T>> provide(endpoint: BfiEndpoint): Parameter<T> {
        val port = portProvider.provide()
        val portCache = cache
            .computeIfAbsent(port) { mutableMapOf() }

        return portCache
            .computeIfAbsent(endpoint.endpoint) { generate(port, endpoint) } as? Parameter<T>
            ?: throw IllegalArgumentException("Invalid provider type mapping!")
    }

    private fun generate(port: OSCPortOut, endpoint: BfiEndpoint): Parameter<*> = when (endpoint.type) {
        ParameterType.BOOL -> BoolParameter(port, endpoint.endpoint)
        ParameterType.INT -> IntParameter(port, endpoint.endpoint)
        ParameterType.FLOAT -> BiFloatParameter(port, endpoint.endpoint)
        ParameterType.FLOAT_POS -> FloatParameter(port, endpoint.endpoint)
    }
}