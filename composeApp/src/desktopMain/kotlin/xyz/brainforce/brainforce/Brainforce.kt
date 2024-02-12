package xyz.brainforce.brainforce

import brainflow.BoardShim
import brainflow.LogLevels
import org.koin.core.annotation.Singleton
import org.slf4j.LoggerFactory
import xyz.brainforce.brainforce.api.data.file.BrainforceConfig
import xyz.brainforce.brainforce.api.data.file.FileReference
import xyz.brainforce.brainforce.api.service.ConnectionService
import xyz.brainforce.brainforce.api.service.FileService
import xyz.brainforce.brainforce.framework.Application
import kotlin.time.Duration.Companion.seconds

@Singleton
class Brainforce(
    private val fileService: FileService,
    private val connectionService: ConnectionService,
) : Application {

    override fun initialize() {
        logger.info("Starting Brainforce...")

        val bfiConfig = getBrainforceConfig()

        if (bfiConfig.debug) {
            BoardShim.set_log_level(LogLevels.LEVEL_DEBUG)
        }

        enableConnectionService(bfiConfig)
        logger.info("Brainforce started!")
        Thread.currentThread().join()
    }

    private fun getBrainforceConfig(): BrainforceConfig = fileService
        .loadFile(FileReference.MainConfig, BrainforceConfig.serializer(), defaultConfig)

    private fun enableConnectionService(config: BrainforceConfig) = connectionService
        .enable(config.asBoardShim(), config.timeout.seconds)

    companion object {

        private val defaultConfig: BrainforceConfig =
            BrainforceConfig()

        private val logger = LoggerFactory
            .getLogger(Brainforce::class.java)
    }
}
