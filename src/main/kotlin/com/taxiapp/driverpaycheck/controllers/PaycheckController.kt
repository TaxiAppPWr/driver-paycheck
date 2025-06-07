package com.taxiapp.driverpaycheck.controllers

import com.taxiapp.driverpaycheck.services.PaycheckService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/paycheck")
class PaycheckController(
    private val paycheckService: PaycheckService
) {
    @GetMapping("/balance")
    fun getBalance(@RequestHeader username: String): ResponseEntity<Any> {
        return paycheckService.getDriverBalance(username).let { result ->
            if (result.isSuccess()) {
                return ResponseEntity.ok(result)
            }
            ResponseEntity.status(result.httpStatus).body(result.messages)
        }
    }

    @GetMapping("/history")
    fun getPaycheckHistory(@RequestParam page: Int, @RequestHeader username: String) : ResponseEntity<Any> {
        return paycheckService.getDriverPaycheckHistory(username, page).let { result ->
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