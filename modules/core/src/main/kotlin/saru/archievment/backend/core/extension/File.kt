package saru.archievment.backend.core.extension

import saru.archievment.backend.core.dto.FileDto
import java.io.File

fun File.toDto(path: String): FileDto {
    return FileDto(
        name = name,
        path = "${path.trimEnd('/')}/$name",
        directory = isDirectory,
        size = length(),
        lastModified = lastModified(),
    )
}