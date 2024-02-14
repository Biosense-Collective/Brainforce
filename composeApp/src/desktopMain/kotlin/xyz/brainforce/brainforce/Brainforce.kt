package xyz.brainforce.brainforce

import brainflow.LogLevels
import org.koin.core.annotation.Singleton
import org.slf4j.LoggerFactory
import xyz.brainforce.brainforce.api.data.file.BrainforceConfig
import xyz.brainforce.brainforce.api.manager.BrainforceConfigManager
import xyz.brainforce.brainforce.api.service.ConnectionService
import xyz.brainforce.brainforce.api.util.Logger
import xyz.brainforce.brainforce.framework.Application
import kotlin.time.Duration.Companion.seconds

@Singleton
class Brainforce(
    private val bfiConfigManager: BrainforceConfigManager,
    private val connectionService: ConnectionService,
) : Application {

    override fun initialize() {
        logger.info("Starting Brainforce...")

        val bfiConfig = bfiConfigManager
            .getConfig()

        if (bfiConfig.debug) {
            Logger.setLevel(LogLevels.LEVEL_DEBUG)
        }

        connectionService.enable()
        logger.info("Brainforce started!")
        Thread.currentThread().join()
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(Brainforce::class.java)
    }
}
