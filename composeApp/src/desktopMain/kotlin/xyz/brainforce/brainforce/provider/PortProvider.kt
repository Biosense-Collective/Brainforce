package xyz.brainforce.brainforce.provider

import com.illposed.osc.transport.OSCPortOut
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.manager.BrainforceConfigManager
import java.net.InetAddress

@Singleton
class PortProvider(
    private val brainforceConfig: BrainforceConfigManager,
) {

    fun provide(): OSCPortOut {
        val config = brainforceConfig.getConfig()
        val address = InetAddress.getByName(config.ipAddress)
        return OSCPortOut(address, config.oscPort)
    }
}