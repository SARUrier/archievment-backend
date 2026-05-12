package saru.archievment.backend.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("file-storage")
@Configuration
class FileStorageProperties {

    var basePath: String = "/app/files"

}
