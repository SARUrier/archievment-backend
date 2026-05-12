package saru.archievment.backend.core.service

import org.springframework.stereotype.Service
import saru.archievment.backend.core.config.FileStorageProperties
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FileStorageService(
    fileStorageProperties: FileStorageProperties,
) {
    val basePath: Path = Paths.get(fileStorageProperties.basePath).toAbsolutePath().normalize()

    fun listFiles(relativePath: String): List<File> {
        val targetPath = resolveAndValidate(relativePath)
        return targetPath.toFile().listFiles()?.toList()
            ?: throw IllegalArgumentException("Path '$relativePath' does not exist or is not a directory")
    }

    fun resolveAndValidate(relativePath: String): Path {
        val resolved = basePath.resolve(relativePath).normalize()
        if (!resolved.startsWith(basePath)) {
            throw SecurityException("Base path '$relativePath' does not start with '$basePath'")
        }
        return resolved
    }
}