package app.global.converter

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

class ApiResponseConverter {
    companion object {
        inline fun <T> ok(f: () -> T?): ResponseEntity<T> = ok(HttpStatus.OK, makeBodyByType(f()))

        inline fun <T> created(f: () -> T?): ResponseEntity<T> = ok(HttpStatus.CREATED, makeBodyByType(f()))

        inline fun <T, R> makeBodyByType(result: T): R? {
            logger.info("response data: $result")

            if (result == null) return null

            return when (result) {
                is Pair<*, *> -> {
                    val list = (result as Pair<*, *>).toList()
                    AffectedRowsBody(id = toLong(list[0]), rows = toLong(list[1])) as R
                }
                is Number -> AffectedRowsBody(rows = toLong(result)) as R
                else -> result as R
            }
        }

        fun toLong(result: Any?): Long =
            if (result is Long)
                result
            else (result as Int).toLong()

        fun <T> ok(status: HttpStatus, data: T?): ResponseEntity<T> = ResponseEntity
            .status(status)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(data)

        fun fail(errorType: ErrorType, message: String): ResponseEntity<ErrorBody> =
            fail(status = errorType.status, errorResponse = ErrorBody(errorType.code, message))

        fun fail(status: HttpStatus, errorResponse: ErrorBody): ResponseEntity<ErrorBody> =
            ResponseEntity.status(status).body(errorResponse)
    }
}

data class AffectedRowsBody(
    @JsonInclude(JsonInclude.Include.CUSTOM, valueFilter = JsonIdIgnoreFilter::class)
    val id: Long = -1,
    val rows: Long
)

data class ErrorBody(
    val code: Int,
    val message: String
)

class JsonIdIgnoreFilter {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return true
        }
        return (other as Long) < 0
    }
}

fun <T> ResponseEntity<T>.isFail(): Boolean = !this.statusCode.is2xxSuccessful

fun <T> ResponseEntity<T>.getErrorMessage(): String = (this.body as ErrorBody).message
