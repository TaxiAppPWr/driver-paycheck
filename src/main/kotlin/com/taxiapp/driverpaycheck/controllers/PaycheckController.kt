package com.taxiapp.driverpaycheck.controllers

import com.taxiapp.driverpaycheck.services.PaycheckService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/paycheck")
class PaycheckController(
    private val paycheckService: PaycheckService
) {
    @GetMapping("/balance")
    fun getBalance(): ResponseEntity<Any> {
        val principal = SecurityContextHolder.getContext().authentication
        return paycheckService.getDriverBalance(principal.name).let { result ->
            if (result.isSuccess()) {
                return ResponseEntity.ok(result)
            }
            ResponseEntity.status(result.httpStatus).body(result.messages)
        }
    }

    @GetMapping("/history")
    fun getPaycheckHistory(@RequestParam page: Int) : ResponseEntity<Any> {
        val principal = SecurityContextHolder.getContext().authentication
        return paycheckService.getDriverPaycheckHistory(principal.name, page).let { result ->
            if (result.isSuccess()) {
                return ResponseEntity.ok(result)
            }
            ResponseEntity.status(result.httpStatus).body(result.messages)
        }
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "status" to "UP",
            "service" to "driver-paycheck-service"
        ))
    }
}