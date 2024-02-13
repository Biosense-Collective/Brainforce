package xyz.brainforce.brainforce.api.manager

import org.koin.core.annotation.Singleton
import xyz.brainforce.brainforce.api.data.file.BrainforceConfig
import xyz.brainforce.brainforce.api.data.file.FileReference
import xyz.brainforce.brainforce.api.service.FileService

@Singleton
class BrainforceConfigManager(
    private val fileService: FileService,
) {

    fun getConfig(): BrainforceConfig = fileService.loadFile(
        FileReference.MainConfig,
        BrainforceConfig.serializer(),
        defaultConfig
    )

    fun update(config: BrainforceConfig.() -> Unit) {
        val bfiConfig = getConfig()
            .apply(config)

        fileService.saveFile(
            FileReference.MainConfig,
            BrainforceConfig.serializer(),
            bfiConfig
        )
    }

    companion object {

        private val defaultConfig: BrainforceConfig =
            BrainforceConfig()
    }
}