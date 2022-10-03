package app.domain.druid.configuration

import app.domain.druid.configuration.ConfigurationPropertiesKeys.ITZEL_DRUID
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.ConfigurationProperties

@ConstructorBinding
@ConfigurationProperties(prefix = "$ITZEL_DRUID.config")
data class DruidConfiguration(
    val host: String,
    val url: String,
    val coordinator: Int,
    val broker: Int,
    val historical: Int,
    val middlemanager: Int,
    val router: Int,
    val ingestionApi: String
) {
    val INGEST_URL: String = "$host:$coordinator$ingestionApi"
}
