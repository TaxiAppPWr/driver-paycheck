package com.taxiapp.driverpaycheck.controllers

import com.taxiapp.driverpaycheck.services.PaycheckService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller("/paycheck")
class PaycheckController(
    private val paycheckService: PaycheckService
) {
    @GetMapping("/balance")
    fun getBalance(principal: Principal): ResponseEntity<Any> {
        return paycheckService.getDriverBalance(principal.name).let { result ->
            if (result.isSuccess()) {
                return ResponseEntity.ok(result)
            }
            ResponseEntity.status(result.httpStatus).body(result.messages)
        }
    }

    @GetMapping("/history")
    fun getPaycheckHistory(@RequestParam page: Int, principal: Principal) : ResponseEntity<Any> {
        return paycheckService.getDriverPaycheckHistory(principal.name, page).let { result ->
            if (result.isSuccess()) {
                return ResponseEntity.ok(result)
            }
            ResponseEntity.status(result.httpStatus).body(result.messages)
        }
    }
}