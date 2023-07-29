package com.mercadolivro.controller.response

data class ErrorResponse(var httpStatus: Int, var message: String, var internalCode: String, var errors: List<FieldErrorResponse>?)