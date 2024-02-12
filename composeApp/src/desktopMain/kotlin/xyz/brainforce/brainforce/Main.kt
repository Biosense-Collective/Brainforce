package xyz.brainforce.brainforce

import org.koin.ksp.generated.module
import xyz.brainforce.brainforce.api.config.AppConfig
import xyz.brainforce.brainforce.framework.Start.runApplication

fun main() {
    runApplication<Brainforce>(AppConfig().module)
}
