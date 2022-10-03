package app.global.util

import org.springframework.dao.DataAccessException
import org.springframework.jdbc.InvalidResultSetAccessException
import org.springframework.jdbc.core.JdbcTemplate

class JdbcTemplateWithException {
    companion object {
        @Throws(RuntimeException::class)
        fun queryForList(jdbcTemplate: JdbcTemplate, query: String): List<Map<String, Any>> =
            try {
                jdbcTemplate.queryForList(query)
            } catch (e: InvalidResultSetAccessException) {
                throw RuntimeException(e)
            } catch (e: DataAccessException) {
                throw RuntimeException(e)
            }

        @Throws(RuntimeException::class)
        fun execute(jdbcTemplate: JdbcTemplate, query: String) =
            try {
                jdbcTemplate.execute(query)
            } catch (e: InvalidResultSetAccessException) {
                throw RuntimeException(e)
            } catch (e: DataAccessException) {
                throw RuntimeException(e)
            }

        @Throws(RuntimeException::class)
        fun update(jdbcTemplate: JdbcTemplate, query: String): Int =
            try {
                jdbcTemplate.update(query)
            } catch (e: InvalidResultSetAccessException) {
                throw RuntimeException(e)
            } catch (e: DataAccessException) {
                throw RuntimeException(e)
            }
    }
}