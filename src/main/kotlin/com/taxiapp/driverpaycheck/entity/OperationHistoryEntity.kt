package com.taxiapp.driverpaycheck.entity

import jakarta.persistence.*
import java.util.Date

@Entity(name = "operation_history")
@Table(indexes = [
    Index(columnList = "driverUsername")
])
class OperationHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var driverUsername: String = "",

    @Column
    var date: Date = Date(),

    @Column
    var amount: Double = 0.0,

    @Column
    var currency: Currency = Currency.PLN,

    @Column
    var operationType: OperationType = OperationType.EARNING,

    @Column(nullable = true)
    var rideId: Long? = null

)