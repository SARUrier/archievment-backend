package saru.archievment.backend.core.controller

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.MediaTypeFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import saru.archievment.backend.core.service.FileStorageService
import java.io.File

@RestController
@RequestMapping("/api/files")
class FileStorageController(
    private val fileStorageService: FileStorageService,
) {

    @GetMapping
    fun listFiles(
        @RequestParam(required = true)
        path: String,
    ): List<File> {
        return fileStorageService.listFiles(path)
    }

    @GetMapping("/download")
    fun downloadFile(
        @RequestParam(required = true)
        path: String,
    ): ResponseEntity<Resource> {
        val resource = UrlResource(fileStorageService.resolveAndValidate(path).toUri())
        return ResponseEntity.ok()
            .contentType(MediaTypeFactory.getMediaType(resource).orElseGet { MediaType.APPLICATION_OCTET_STREAM })
            .body(resource)
    }

}