import brainflow.BoardShim
import brainflow.LogLevels
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import xyz.brainforce.brainforce.api.config.AppConfig
import xyz.brainforce.brainforce.api.data.file.BrainforceConfig
import xyz.brainforce.brainforce.api.data.file.FileReference
import xyz.brainforce.brainforce.api.service.ConnectionService
import xyz.brainforce.brainforce.api.service.FileService
import kotlin.time.Duration.Companion.seconds

fun main() {
    val app = startKoin {
        modules(AppConfig().module)
    }

    val file = app.koin.get<FileService>()
    val config = BrainforceConfig()
    val bfiConfig = file
        .loadFile(FileReference.MainConfig, BrainforceConfig.serializer(), config)
    val shim = bfiConfig.asBoardShim()
    val service = app.koin.get<ConnectionService>()
    val debug = true

    if (debug) {
        BoardShim.set_log_level(LogLevels.LEVEL_DEBUG)
    }

    service.enable(shim, bfiConfig.timeout.seconds)
    Thread.currentThread().join()
}
