package com.mercadolivro.exceptions

class NotFoundException(override var message: String, var code: String): Exception(message) {
}
