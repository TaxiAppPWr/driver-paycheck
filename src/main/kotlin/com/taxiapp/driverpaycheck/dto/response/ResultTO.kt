package com.taxiapp.driverpaycheck.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

data class ResultTO(
    @JsonIgnore
    override val httpStatus: HttpStatus,
    @JsonIgnore
    override val messages: List<String>? = null,
) : ResultInterface
