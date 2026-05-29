package saru.archievment.backend.core.dto

class FileDto(
    val name: String,
    val path: String,
    val directory: Boolean,
    val size: Long,
    val lastModified: Long,
)