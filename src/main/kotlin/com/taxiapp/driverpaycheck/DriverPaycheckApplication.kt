package com.taxiapp.driverpaycheck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class DriverPaycheckApplication

fun main(args: Array<String>) {
    runApplication<DriverPaycheckApplication>(*args)
}
