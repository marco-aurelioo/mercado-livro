package com.mercadolivro.controller.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class PutBookRequest(
        @field:NotEmpty(message = "Nomw não pode ser vazio")
        var name: String,

        @field:Positive(message = "Valor deve ser positivo.")
        var price: Double,
)