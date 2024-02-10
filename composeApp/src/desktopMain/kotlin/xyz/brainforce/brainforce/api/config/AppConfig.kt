package xyz.brainforce.brainforce.api.config

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataConfig::class])
@ComponentScan("xyz.brainforce.brainforce")
class AppConfig