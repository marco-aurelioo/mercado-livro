package com.mercadolivro.controller.response

import com.mercadolivro.model.STATUS

data class BookResponse(
        val id: Int? = null,

        val name: String,

        val price: Double,

        var status: STATUS? = null,

        var custmer: CustomerResponse? = null
)