package com.mercadolivro.helper

import com.mercadolivro.model.*
import java.util.*
import javax.persistence.*

public fun buildCustomer(
        id: Int? = null,
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        role = setOf(ROLE.CUSTOMER_ROLE)
)


public fun buildBook(
        id: Int? = null,
        name: String = "book name",
        price: Double = 10.0,
        status: STATUS = STATUS.ATIVO,
        customer: CustomerModel = buildCustomer()
) = BookModel(
    id = id,
    name = name,
    price = price,
    status= status,
    customer = customer)

