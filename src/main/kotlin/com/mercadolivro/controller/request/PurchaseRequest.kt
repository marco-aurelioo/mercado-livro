package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.validation.BooksAvaliableToPurchase

data class PurchaseRequest(

        @JsonAlias("customer_id")
        var custormerId: Int,

        @JsonAlias("book_ids")
        @BooksAvaliableToPurchase
        var books: Set<Int>
)
