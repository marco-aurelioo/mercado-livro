package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.request.PurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper (
        val customerService: CustomerService,
        val bookService: BookService
        ){


    fun toPurchaseModel(purchaseRequest: PurchaseRequest): PurchaseModel {
        var customer = customerService.getCustomer(purchaseRequest.custormerId)
        var books = bookService.findByIds(purchaseRequest.books)

        return PurchaseModel(
                customer = customer,
                books = books,
                price = books.sumOf { it.price }
        )
    }

}