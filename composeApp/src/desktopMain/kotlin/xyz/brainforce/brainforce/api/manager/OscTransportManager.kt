package xyz.brainforce.brainforce.api.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.data.osc.BoolValue
import xyz.brainforce.brainforce.api.data.osc.FloatValue
import xyz.brainforce.brainforce.api.data.osc.IntValue
import xyz.brainforce.brainforce.api.data.osc.OSCValue
import xyz.brainforce.brainforce.api.provider.OscParameterProvider
import xyz.brainforce.brainforce.api.schema.BfiEndpoint
import kotlin.math.min

@Singleton
class OscTransportManager(
    private val bfiConfigManager: BrainforceConfigManager,
    private val brainDataManager: BrainDataManager,
    private val parameterProvider: OscParameterProvider,
) {

    private var transport: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun enableTransport() {
        if (transport?.isActive == true) {
            return
        }

        transport = scope.launch { initialize() }
    }

    suspend fun disableTransport() {
        if (transport?.isActive == false) {
            return
        }

        transport?.cancelAndJoin()
        brainDataManager.reset()
    }

    private suspend fun initialize() {
        val board = bfiConfigManager
            .getConfig()
            .asBoardShim()
        val delay = bfiConfigManager
            .getConfig()
            .refreshRate
            .let { getHzDelay(it, 240) }

        while (true) {
            brainDataManager
                .process(board)
                .forEach { drive(it.key, it.value) }

            delay(delay)
        }
    }

    private fun getHzDelay(hz: Int, max: Int): Long =
        1000L / hz.coerceIn(1, max)

    private fun drive(endpoint: BfiEndpoint, value: OSCValue<*>) = when (value) {
        is BoolValue -> parameterProvider
            .provide<Boolean>(endpoint)
            .drive(value.value)
        is IntValue -> parameterProvider
            .provide<Int>(endpoint)
            .drive(value.value)
        is FloatValue -> parameterProvider
            .provide<Float>(endpoint)
            .drive(value.value)
    }
}