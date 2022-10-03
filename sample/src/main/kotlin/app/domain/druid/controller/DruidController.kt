package app.domain.druid.controller

import app.domain.druid.controller.request.ExecuteSqlRequest
import app.domain.druid.controller.request.IngestionRequest
import app.domain.druid.service.DruidIngestService
import app.domain.druid.service.DruidSqlService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    "/api/druid",
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DruidController(
    private val ingestService: DruidIngestService,
    private val sqlService: DruidSqlService
) {

    @PostMapping("ingestion")
    fun ingestion(
        @RequestBody payloads: IngestionRequest
    ) = ApiResponseConverter.ok { ingestService.ingestion(payloads) }

    @PostMapping("sql")
    fun executeSQL(
        @RequestBody payloads: ExecuteSqlRequest
    ) = ApiResponseConverter.ok { sqlService.executeSql(payloads.sql) }

    @PostMapping("status")
    fun getDatasourceStatus(
        @RequestBody payloads: ExecuteSqlRequest
    ) = ApiResponseConverter.ok { sqlService.getDatasourceStatus(payloads.sql) }
}