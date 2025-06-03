package com.taxiapp.driverpaycheck.repository

import com.taxiapp.driverpaycheck.entity.BalanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BalanceRepository : JpaRepository<BalanceEntity, Long> {
    fun findByDriverUsername(driverUsername: String) : BalanceEntity?

    @Modifying
    @Transactional
    @Query("UPDATE driver_balance b SET b.amount = b.amount + :amount WHERE b.driverUsername = :driverUsername")
    fun addAmountToDriverBalance(driverUsername: String, amount: Double): Int
}