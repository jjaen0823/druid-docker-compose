package druid.sample

import app.domain.druid.configuration.DruidConfiguration
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.Properties

class JdbcRunner(
    private val druidConfiguration: DruidConfiguration,
) {
    private val DRUID_BROKCER_PORT = druidConfiguration.broker
    private fun createConnection(): Connection{
        val url = druidConfiguration.url
        val connectionProperties = Properties()
        return DriverManager.getConnection(url, connectionProperties)
    }

    fun runQuery(sql: String): List<Map<String, *>>{
        val conn = createConnection()
        val stat = conn.createStatement()
        val res: ResultSet = stat.executeQuery(sql)

        val resMeta = res.metaData
        val columns: List<Pair<String, String>> = (1..resMeta.columnCount).map {
            resMeta.getColumnName(it) to resMeta.getColumnTypeName(it)}

        val resList = mutableListOf<Map<String, *>>()
        while(res.next()) {
            val row = columns.associate { (name, type) ->
                when (type) {
                    in listOf("CHAR", "VARCHAR") -> name to res.getString(name)
                    "BIGINT" -> name to res.getBigDecimal(name).toLong()
                    "INT" -> name to res.getInt(name)
                    "BOOLEAN" -> name to res.getBoolean(name)
                    "DATE" -> name to res.getDate(name)
                    "TIME" -> name to res.getTime(name)
                    else -> name to null
                }
            }

            resList.add(row)
        }
        return resList
    }
}
