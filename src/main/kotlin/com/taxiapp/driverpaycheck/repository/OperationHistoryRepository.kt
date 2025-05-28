package com.taxiapp.driverpaycheck.repository

import com.taxiapp.driverpaycheck.entity.BalanceEntity
import com.taxiapp.driverpaycheck.entity.OperationHistoryEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface OperationHistoryRepository : PagingAndSortingRepository<OperationHistoryEntity, Long>, JpaRepository<OperationHistoryEntity, Long> {
    fun countByDriverUsername(driverUsername: String): Long

    fun findByDriverUsername_OrderByDateDesc(
        driverUsername: String, pageable: Pageable
    ): List<OperationHistoryEntity>

    @Query("SELECT SUM(o.amount) FROM operation_history o WHERE o.driverUsername = :driverUsername")
    fun sumAmountByDriverUsername(driverUsername: String): Double
}