package com.taxiapp.driverpaycheck.configuration

import com.taxiapp.driverpaycheck.filters.JwtFilter
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SecurityConfig {

    @Bean
    open fun jwtAuthFilterRegistration(jwtAuthFilter: JwtFilter): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = jwtAuthFilter
        registration.addUrlPatterns("*")
        registration.order = 1
        return registration
    }
}