package xyz.brainforce.brainforce.api.provider

import com.illposed.osc.transport.OSCPortOut
import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.manager.BrainforceConfigManager
import xyz.brainforce.brainforce.api.util.Logger
import java.net.InetAddress
import java.util.Objects

@Singleton
class PortProvider(
    private val brainforceConfig: BrainforceConfigManager,
) {
    private val oscOutCache = mutableMapOf<Int, OSCPortOut>()

    fun provide(): OSCPortOut {
        val config = brainforceConfig.getConfig()
        val port = config.oscPort
        val ip = config.ipAddress
        val hash = Objects.hash(port, ip)

        return oscOutCache.computeIfAbsent(hash) {
            Logger.info("Generating fresh OSC transport point at '$ip' port $port")
            val address = InetAddress.getByName(ip)
            OSCPortOut(address, port)
        }
    }
}