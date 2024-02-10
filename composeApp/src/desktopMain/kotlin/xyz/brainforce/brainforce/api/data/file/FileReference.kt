package xyz.brainforce.brainforce.api.data.file

import java.io.File

sealed class FileReference<T>(private val path: String) {

    data object MainConfig : FileReference<BrainforceConfig>(
        "$userHome/brainforce/brainforce.json"
    )

    fun asFile(): File = File(path)

    fun exists(): Boolean = asFile().exists()

    companion object {

        private val userHome = System.getProperty("user.home")
    }
}