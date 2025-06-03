package com.taxiapp.driverpaycheck.entity

import jakarta.persistence.*
import java.util.Date

@Entity(name = "operation_history")
@Table(indexes = [
    Index(columnList = "driverUsername", unique = true)
])
class OperationHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var driverUsername: String,

    @Column
    var date: Date,

    @Column
    var amount: Double,

    @Column
    var currency: Currency = Currency.PLN,

    @Column
    var operationType: OperationType,

    @Column(nullable = true)
    var rideId: Long? = null

)