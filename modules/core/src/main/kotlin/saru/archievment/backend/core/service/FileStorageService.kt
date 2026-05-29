package saru.archievment.backend.core.service

import org.springframework.stereotype.Service
import saru.archievment.backend.core.config.FileStorageProperties
import saru.archievment.backend.core.dto.FileDto
import saru.archievment.backend.core.extension.toDto
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FileStorageService(
    fileStorageProperties: FileStorageProperties,
) {
    val basePath: Path = Paths.get(fileStorageProperties.basePath).toAbsolutePath().normalize()

    private val ignoredFiles = setOf(
        "desktop.ini",
        "thumbs.db",
        ".ds_store",
    )

    fun listFiles(relativePath: String): List<FileDto> {
        val targetPath = resolveAndValidate(relativePath)
        return targetPath.toFile().listFiles()
            ?.filter { !it.isHidden && it.name.lowercase() !in ignoredFiles }
            ?.map { it.toDto(relativePath) }
            ?.toList()
            ?: throw IllegalArgumentException("Path '$relativePath' does not exist or is not a directory")
    }

    fun resolveAndValidate(relativePath: String): Path {
        val resolved = basePath.resolve(relativePath.trimStart('/')).normalize()
        if (!resolved.startsWith(basePath)) {
            throw SecurityException("Invalid path: '$relativePath' is outside of the base directory $basePath")
        }
        return resolved
    }
}