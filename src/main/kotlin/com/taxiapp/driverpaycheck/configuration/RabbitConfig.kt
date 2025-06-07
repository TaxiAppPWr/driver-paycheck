package com.taxiapp.driverpaycheck.configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.taxiapp.driverpaycheck.dto.event.DriverCreatedEvent
import com.taxiapp.driverpaycheck.dto.event.RideFinishedEvent
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper

@Configuration
open class RabbitConfig {

    @Bean
    open fun jsonMessageConverter(): MessageConverter {
        val mapper = ObjectMapper().apply {
            registerKotlinModule()
            registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }

        val typeMapper = DefaultJackson2JavaTypeMapper().apply {
            setTrustedPackages("*")
            idClassMapping = mapOf(
                "taxiapp.ride.dto.event.RideFinishedEvent" to RideFinishedEvent::class.java,
                "taxiapp.driver.dto.event.DriverCreatedEvent" to DriverCreatedEvent::class.java
            )
        }

        return Jackson2JsonMessageConverter(mapper).apply {
            javaTypeMapper = typeMapper
        }
    }

    @Bean
    open fun rabbitTemplate(connectionFactory: ConnectionFactory, jsonMessageConverter: MessageConverter): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter
        return rabbitTemplate
    }
}