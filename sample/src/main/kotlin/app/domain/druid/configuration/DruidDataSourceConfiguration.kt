package app.domain.druid.configuration

import app.domain.druid.configuration.ConfigurationPropertiesKeys.ITZEL_DRUID
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class DruidDataSourceConfiguration {
    @Bean(name = ["druidProperties"])
    @ConfigurationProperties(ITZEL_DRUID)
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["druidDataSource"])
    @ConfigurationProperties("$ITZEL_DRUID.hikari")
    fun dataSource(@Qualifier("druidProperties") properties: DataSourceProperties): HikariDataSource {
        return properties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean("druidTemplate")
    fun jdbcTemplate(@Qualifier("druidDataSource") dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}