package xyz.brainforce.brainforce.api.service

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton
import org.slf4j.LoggerFactory
import xyz.brainforce.brainforce.api.data.file.FileLayout
import xyz.brainforce.brainforce.api.data.file.FileReference
import java.io.IOException

@Singleton
class FileService(
    private val json: Json
) {
    private val cache = mutableMapOf<FileReference<*>, FileLayout>()

    @Suppress("UNCHECKED_CAST")
    fun <T : FileLayout> loadFile(reference: FileReference<T>, serializer: KSerializer<T>, default: T): T {
        createFileIfMissing(reference, serializer, default)

        if (reference in cache) {
            return cache[reference] as T
        }

        val content = reference
            .asFile()
            .readText()
        val name = reference.asFile().name
        val file = json
            .runCatching { decodeFromString(serializer, content) }
            .onFailure { logger.error("Failed to read $name, file is likely corrupted: ${it.message}") }
            .getOrDefault(default)

        cache[reference] = file
        return file
    }

    fun <T : FileLayout> saveFile(reference: FileReference<T>, serializer: KSerializer<T>, data: T) {
        createFileIfMissing(reference, serializer, data)

        if (cache[reference] == data) {
            return
        }

        cache[reference] = data

        val content = json
            .encodeToString(serializer, data)

        reference
            .asFile()
            .writeText(content)
    }

    private fun <T> createFileIfMissing(reference: FileReference<T>, serializer: KSerializer<T>, data: T): Boolean {
        val file = reference.asFile()

        if (file.exists()) {
            return false
        }

        try {
            file.parentFile?.mkdirs()
            file.createNewFile()
            json
                .encodeToString(serializer, data)
                .let(file::writeText)
        } catch (e: IOException) {
            logger.error("Failed to create configuration file: ${e.message}")
            return false
        }

        logger.info("Created file ${file.path}")
        return true
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(FileService::class.java)
    }
}
