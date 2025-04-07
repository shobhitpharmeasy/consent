package com.pharmeasy.consent.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Builder
import lombok.extern.slf4j.Slf4j
import java.time.LocalDateTime

@Slf4j
@Builder
@JvmRecord
data class ApiErrorDto(
    @field:JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") @param:JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") val timestamp: LocalDateTime?,

    val path: String?, val method: String?, val status: Int?, val error: String?, val message: String?,

    @field:JsonInclude(JsonInclude.Include.NON_NULL) @param:JsonInclude(JsonInclude.Include.NON_NULL) val fields: Map<String, String>?,

    @field:JsonInclude(JsonInclude.Include.NON_NULL) @param:JsonInclude(JsonInclude.Include.NON_NULL) val stakeTrace: Any?

) {
    constructor(
        timestamp: LocalDateTime?, path: String?, method: String?, status: Int?,
        error: String?, message: String?
    ) : this(timestamp, path, method, status, error, message, null, null) {
        ApiErrorDto.log.error(
            "ApiErrorDto created: [status={}, error={}, message={}, path={}, method={}]", status, error, message,
            path, method
        )
    }
}
