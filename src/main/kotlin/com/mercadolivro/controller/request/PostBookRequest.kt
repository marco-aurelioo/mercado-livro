package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive


data class PostBookRequest(
        @field:NotEmpty
        var name: String,

        @field:Positive
        var price: Double,

        @JsonAlias("customer_id")
        var customerId: Int
)