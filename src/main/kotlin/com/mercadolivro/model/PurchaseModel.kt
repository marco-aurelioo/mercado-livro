package com.mercadolivro.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "purchase")
data class PurchaseModel(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "customer_id")
        var customer: CustomerModel,

        @ManyToMany
        @JoinTable(name = "purchase_book",
                joinColumns = [JoinColumn(name = "purchase_id")],
                inverseJoinColumns = [JoinColumn(name = "book_id")])
        var books: MutableList<BookModel>,

        @Column
        val nfe: String? = null,

        @Column
        val price: Double,

        @Column(name = "create_at")
        val creatdAt: LocalDateTime = LocalDateTime.now()

)