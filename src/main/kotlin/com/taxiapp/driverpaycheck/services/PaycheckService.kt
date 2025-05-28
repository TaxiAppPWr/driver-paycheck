package com.taxiapp.driverpaycheck.services

import com.taxiapp.driverpaycheck.dto.event.DriverCreatedEvent
import com.taxiapp.driverpaycheck.dto.event.RideFinishedEvent
import com.taxiapp.driverpaycheck.dto.response.ResultInterface

interface PaycheckService {
    fun getDriverBalance(username: String): ResultInterface

    fun getDriverPaycheckHistory(username: String, page: Int): ResultInterface

    fun handleDriverCreatedEvent(event: DriverCreatedEvent)

    fun handleRideFinishedEvent(event: RideFinishedEvent)
}