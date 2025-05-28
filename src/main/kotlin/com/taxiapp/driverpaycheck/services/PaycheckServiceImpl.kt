package com.taxiapp.driverpaycheck.services

import com.taxiapp.driverpaycheck.dto.event.DriverCreatedEvent
import com.taxiapp.driverpaycheck.dto.event.RideFinishedEvent
import com.taxiapp.driverpaycheck.dto.response.HistoryItemTO
import com.taxiapp.driverpaycheck.dto.response.HistoryTO
import com.taxiapp.driverpaycheck.dto.response.ResultInterface
import com.taxiapp.driverpaycheck.dto.response.ResultTO
import com.taxiapp.driverpaycheck.entity.BalanceEntity
import com.taxiapp.driverpaycheck.entity.OperationHistoryEntity
import com.taxiapp.driverpaycheck.entity.OperationType
import com.taxiapp.driverpaycheck.repository.BalanceRepository
import com.taxiapp.driverpaycheck.repository.OperationHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
open class PaycheckServiceImpl(
    private val balanceRepository: BalanceRepository,
    private val operationHistoryRepository: OperationHistoryRepository
) : PaycheckService {

    @Value("\${api.page.size}")
    private var pageSize: Int = 0

    private fun getNumberOfPages(totalNumber: Long): Int {
        return (totalNumber / pageSize + if (totalNumber % pageSize > 0) 1 else 0).toInt()
    }


    override fun getDriverBalance(username: String): ResultInterface {
        val balanceEntity = balanceRepository.findByDriverUsername(username)
            ?: return ResultTO(
                messages = listOf("Balance not found for driver: $username"),
                httpStatus = HttpStatus.NOT_FOUND
            )

        return com.taxiapp.driverpaycheck.dto.response.BalanceTO(
            balance = balanceEntity.amount,
            currency = balanceEntity.currency.name
        )
    }

    override fun getDriverPaycheckHistory(username: String, page: Int): ResultInterface {
        val operationHistory = operationHistoryRepository.findByDriverUsername_OrderByDateDesc(
            username, Pageable.ofSize(pageSize).withPage(page))

        val totalElements = operationHistoryRepository.countByDriverUsername(username)

        return HistoryTO(
            history = operationHistory.map {
                HistoryItemTO(
                    id = it.id,
                    date = it.date,
                    amount = it.amount,
                    currency = it.currency.name,
                    operationType = it.operationType.name
                )
            },
            totalPages = getNumberOfPages(totalElements),
            totalElements = totalElements,
            pageNumber = page,
            pageSize = pageSize,
        )
    }

    override fun handleDriverCreatedEvent(event: DriverCreatedEvent) {
        val balance = BalanceEntity(
            driverUsername = event.username,
            amount = 0.0
        )

        balanceRepository.save(balance)
    }

    @Transactional
    override fun handleRideFinishedEvent(event: RideFinishedEvent) {
        val driverUsername = event.driverUsername

        requireNotNull(balanceRepository.findByDriverUsername(driverUsername)) {
            "Balance not found for driver: $driverUsername"
        }

        val operationHistory = OperationHistoryEntity(
            driverUsername = driverUsername,
            amount = event.driverEarning,
            currency = event.currency,
            operationType = OperationType.EARNING,
            date = event.endDate
        )
        operationHistoryRepository.save(operationHistory)

        balanceRepository.addAmountToDriverBalance(
            driverUsername = driverUsername,
            amount = event.driverEarning
        )
    }
}