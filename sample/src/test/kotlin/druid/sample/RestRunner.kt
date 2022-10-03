package druid.sample

import java.time.ZonedDateTime
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity


class RestRunner{
    private val restTemplateBuilder = RestTemplateBuilder()
    private val DRUID_COORDINATOR_PORT = 10053

    private fun createRequest(body: Any): HttpEntity<Any>{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(body, headers)
    }

    data class RunTaskResponse(
        val task: String = ""
    )

    fun runTask(spec: String): String {
        val res:ResponseEntity<RunTaskResponse> = restTemplateBuilder
            .build()
            .postForEntity(
                "http://localhost:$DRUID_COORDINATOR_PORT/druid/indexer/v1/task",
                createRequest(spec),
                RunTaskResponse::class.java
            )

        if (res.statusCode == HttpStatus.OK)
            return res.body?.task ?: throw Exception("Body is null")
        else
            throw Exception("ERROR ${res.statusCode}")
    }

    data class TaskStatus(
        val id: String,
        val groupId: String,
        val type: String,
        val createdTime: ZonedDateTime,
        val queueInsertionTime: ZonedDateTime,
        val statusCode: String,
        val status: String,
        val runnerStatusCode: String,
        val duration: Long,
        val location: Map<String, Any>,
        val dataSource: String,
        val errorMsg: String?
    )

    data class GetStatusResponse(
        val task: String ="",
        val status: TaskStatus
    )

    fun getTaskStatus(taskId: String): String{
        val res = restTemplateBuilder
            .build()
            .getForEntity(
                "http://localhost:$DRUID_COORDINATOR_PORT/druid/indexer/v1/task/$taskId/status",
                GetStatusResponse::class.java
            )

        if (res.statusCode == HttpStatus.OK)
            return res.body?.status?.status ?: throw Exception("Body is null")
        else
            throw Exception("ERROR ${res.statusCode}")
    }
}
