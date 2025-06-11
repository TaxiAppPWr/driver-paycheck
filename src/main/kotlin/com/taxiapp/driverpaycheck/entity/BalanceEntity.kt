package com.taxiapp.driverpaycheck.entity

import jakarta.persistence.*

@Entity(name = "driver_balance")
@Table(indexes = [
    Index(columnList = "driverUsername", unique = true)
])
class BalanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true)
    var driverUsername: String = "",

    @Column
    var amount: Double = 0.0,

    @Column
    var currency: Currency = Currency.PLN,
)