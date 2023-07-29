package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvaliable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


data class PostCustomerRequest(
        @field:NotEmpty
        var name: String,

        @field:Email
        @field:EmailAvaliable
        var email: String,

        @field:NotEmpty
        var password: String?
)