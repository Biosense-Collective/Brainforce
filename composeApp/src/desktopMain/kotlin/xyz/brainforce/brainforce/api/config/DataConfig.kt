package xyz.brainforce.brainforce.api.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DataConfig {

    @Singleton
    @OptIn(ExperimentalSerializationApi::class)
    fun serialization(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        explicitNulls = false
        prettyPrint = true
    }
}