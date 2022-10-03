package app.domain.druid.service

import app.domain.druid.configuration.DruidConfiguration
import app.domain.druid.controller.request.IngestionRequest
import app.domain.druid.controller.response.IngestionResponse
import app.global.exception.CommonException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.naming.ServiceUnavailableException

@Service
class DruidIngestService(
    druidConfiguration: DruidConfiguration
) {
    private val INGEST_URL: String = druidConfiguration.INGEST_URL

    private fun createRequest(body: Any): HttpEntity<Any> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(body, headers)
    }

    fun ingestion(
        ingestionRequest: IngestionRequest
    ): IngestionResponse {
        // TODO s3 properties(minio, minio_admin)

        val restTemplate = RestTemplate()
        val response = restTemplate.postForEntity(
            INGEST_URL,
            createRequest(ingestionRequest),
            IngestionResponse::class.java
        )

        if (response.statusCode != HttpStatus.OK)
            throw ServiceUnavailableException("Ingestion ERROR ${response.statusCode}")

        return response.body?.task
            ?.let { IngestionResponse(task = it) }
            ?: throw CommonException.DataNotFoundException("Body is null")
    }
}