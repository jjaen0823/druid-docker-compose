package app.domain.druid.service

import app.global.util.JdbcTemplateWithException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class DruidSqlService(
    @Qualifier("druidTemplate")
    private val jdbcTemplate: JdbcTemplate
) {
    fun getDatasourceStatus(statusSql: String): List<Map<String, Any>> =
        JdbcTemplateWithException.queryForList(jdbcTemplate, statusSql)

    @Throws(RuntimeException::class)
    fun executeSql(sql: String): List<Map<String, Any>> =
        JdbcTemplateWithException.queryForList(jdbcTemplate, sql)
}
