package saru.archievment.backend.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("jellyfin")
@Configuration
class JellyfinProperties {

    var apiKey: String? = null
    var username: String? = null
    var password: String? = null

}
