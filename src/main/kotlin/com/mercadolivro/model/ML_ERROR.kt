package com.mercadolivro.model

enum class ML_ERROR(var message: String, var code: String) {
    ML101 ("CUSTOMER NOT FOUND (ID=%s)","101"),
    ML102 ("INVALID CUSTOMER","102"),
    ML201 ("BOOK NOT FOUND (ID=%s)","201"),
}