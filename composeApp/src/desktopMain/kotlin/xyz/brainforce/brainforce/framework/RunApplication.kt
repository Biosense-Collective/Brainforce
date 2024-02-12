package xyz.brainforce.brainforce.framework

import org.koin.core.annotation.ComponentScan
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import xyz.brainforce.brainforce.framework.lifecycle.AppException

@ComponentScan
object Start {

    inline fun <reified T : Application> runApplication(vararg modules: Module): T {
        val koin = startKoin {
            modules(*modules)
        }

        return koin.koin
            .getOrNull<T>()
            ?.also { it.initialize() }
            ?: throw AppException.FailedToFindApp()
    }
}
