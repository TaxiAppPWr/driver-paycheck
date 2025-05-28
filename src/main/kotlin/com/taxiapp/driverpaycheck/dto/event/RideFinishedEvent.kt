package com.taxiapp.driverpaycheck.dto.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.taxiapp.driverpaycheck.entity.Currency
import java.util.Date

// TODO: Add another fields if needed
@JsonIgnoreProperties(ignoreUnknown = true)
data class RideFinishedEvent(
    val rideFinishedEventId: Long,
    val driverUsername: String,
    val rideId: Long,
    val startDate: Date,
    val endDate: Date,
    val currency: Currency,
    val driverEarning: Double
)