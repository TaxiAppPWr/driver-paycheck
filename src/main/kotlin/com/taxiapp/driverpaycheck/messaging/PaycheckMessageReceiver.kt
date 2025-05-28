package com.taxiapp.driverpaycheck.messaging

import com.taxiapp.driverpaycheck.dto.event.DriverCreatedEvent
import com.taxiapp.driverpaycheck.dto.event.RideFinishedEvent
import com.taxiapp.driverpaycheck.services.PaycheckService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@RabbitListener(queues = ["\${rabbit.queue.driver-paycheck.name}"])
@Component
class PaycheckMessageReceiver(
    private val paycheckService: PaycheckService
) {
    @RabbitHandler
    fun receiveRideFinishedEvent(event: RideFinishedEvent) {
        paycheckService.handleRideFinishedEvent(event)
    }

    @RabbitHandler
    fun receiveDriverCreatedEvent(event: DriverCreatedEvent) {
        paycheckService.handleDriverCreatedEvent(event)
    }

    @RabbitHandler(isDefault = true)
    fun receiveDefault(event: Any) {
        // Do nothing
    }
}