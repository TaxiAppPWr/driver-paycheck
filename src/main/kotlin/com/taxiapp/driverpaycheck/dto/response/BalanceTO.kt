package com.taxiapp.driverpaycheck.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

data class BalanceTO(
    val balance: Double,
    val currency: String,

    @JsonIgnore
    override val httpStatus: HttpStatus = HttpStatus.OK,
    @JsonIgnore
    override val messages: List<String>? = null,
) : ResultInterface