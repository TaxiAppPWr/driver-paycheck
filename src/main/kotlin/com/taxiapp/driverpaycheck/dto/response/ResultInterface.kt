package com.taxiapp.driverpaycheck.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

interface ResultInterface {
    val httpStatus: HttpStatus

    val messages: List<String>?

    @JsonIgnore
    fun isSuccess(): Boolean {
        return httpStatus.is2xxSuccessful
    }
}