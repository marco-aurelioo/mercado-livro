package com.mercadolivro.model

import javax.persistence.*

@Entity
@Table(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val name: String,

    val price: Double,

    @Enumerated(EnumType.STRING)
    var status: STATUS? = null ,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
    )

