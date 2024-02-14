package xyz.brainforce.brainforce.api.config

import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.handler.DeviceLifeCycleHandlers
import xyz.brainforce.brainforce.api.manager.OscTransportManager


@Module
class ConnectionConfig {

    @Singleton
    fun lifecycleHandlers(
        oscTransportManager: OscTransportManager,
    ): DeviceLifeCycleHandlers {
        val disconnect = oscTransportManager::disableTransport
        val connect = oscTransportManager::enableTransport

        return DeviceLifeCycleHandlers(
            onDisconnect = disconnect,
            onError = disconnect,
            onTrackingStarted = connect,
        )
    }
}