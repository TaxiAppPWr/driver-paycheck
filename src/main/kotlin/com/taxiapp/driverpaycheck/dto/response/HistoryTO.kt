package com.taxiapp.driverpaycheck.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.util.*

data class HistoryItemTO(
    val id: Long,
    val amount: Double,
    val currency: String,
    val date: Date,
    val operationType: String
)

data class HistoryTO(
    val history: List<HistoryItemTO>,
    val pageNumber: Number,
    val pageSize: Number,
    val totalElements: Number,
    val totalPages: Number,

    @JsonIgnore
    override val httpStatus: HttpStatus = HttpStatus.OK,
    @JsonIgnore
    override val messages: List<String>? = null,
) : ResultInterface
