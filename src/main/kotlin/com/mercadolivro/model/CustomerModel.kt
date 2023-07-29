package com.mercadolivro.model

import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,

        @Column var name: String,

        @Column var email: String,

        @Enumerated(EnumType.STRING)
        var status: CustomerStatus,

        @Column var password: String?,

        @Column @Enumerated(EnumType.STRING)
        @ElementCollection(targetClass = ROLE::class, fetch = FetchType.EAGER)
        @CollectionTable(name = "customer_role", joinColumns = [JoinColumn(name = "customer_id")])
        var role: Set<ROLE>? = setOf()

)