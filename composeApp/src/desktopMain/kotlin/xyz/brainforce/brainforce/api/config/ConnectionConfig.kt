package xyz.brainforce.brainforce.api.config

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.action.DeviceDiscovery
import xyz.brainforce.brainforce.api.action.DeviceHealthCheck
import xyz.brainforce.brainforce.api.handler.DeviceDiscoveryHandler
import xyz.brainforce.brainforce.api.handler.DeviceHealthHandler
import xyz.brainforce.brainforce.api.handler.DeviceLifeCycleHandlers


@Module
class ConnectionConfig {

    @Singleton
    fun lifecycleHandlers(): DeviceLifeCycleHandlers =
        DeviceLifeCycleHandlers()

    @Factory
    fun discovery(handlers: DeviceLifeCycleHandlers): DeviceDiscovery =
        DeviceDiscoveryHandler(handlers)

    @Factory
    fun healthCheck(handlers: DeviceLifeCycleHandlers): DeviceHealthCheck =
        DeviceHealthHandler(handlers)
}