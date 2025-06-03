package com.taxiapp.driverpaycheck.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MessagingConfig {
    @Value("\${rabbit.exchange.ride.name}")
    private val rideExchangeName: String? = null

    @Value("\${rabbit.exchange.users.name}")
    private val usersExchangeName: String? = null

    @Value("\${rabbit.queue.driver-paycheck.name}")
    private val driverPaycheckQueueName: String? = null

    @Value("\${rabbit.topic.ride.finished}")
    private val rideFinishedTopic: String? = null

    @Value("\${rabbit.topic.drivers.created}")
    private val driverCreatedTopic: String? = null

    @Bean
    open fun rideExchange(): DirectExchange {
        return DirectExchange(rideExchangeName)
    }

    @Bean open fun usersExchange(): TopicExchange {
        return TopicExchange(usersExchangeName)
    }

    @Bean
    open fun driverPaycheckQueue(): Queue {
        return QueueBuilder.durable("driver-paycheck").build()
    }

    @Bean
    open fun rideFinishedBinding(rideExchange: DirectExchange, driverPaycheckQueue: Queue): Binding {
        return BindingBuilder.bind(driverPaycheckQueue).to(rideExchange).with(rideFinishedTopic)
    }

    @Bean
    open fun driverCreatedBinding(usersExchange: TopicExchange, driverPaycheckQueue: Queue): Binding {
        return BindingBuilder.bind(driverPaycheckQueue).to(usersExchange).with(driverCreatedTopic)
    }

}