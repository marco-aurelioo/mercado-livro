package com.mercadolivro.extension

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.controller.response.PageResponse
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.CustomerStatus
import com.mercadolivro.model.STATUS
import org.springframework.data.domain.Page

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email, status = CustomerStatus.ATIVO, password = this.password)
}

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email, status = CustomerStatus.ATIVO, password = null)
}


fun PostBookRequest.toBookModel(customerModel: CustomerModel): BookModel {
    return BookModel(name = this.name, price = this.price, status = STATUS.ATIVO, customer = customerModel)
}

fun PutBookRequest.toBookModel(previusValue: BookModel): BookModel {
    return BookModel(id = previusValue.id, name = this.name ?: previusValue.name, price = this.price
            ?: previusValue.price, status = previusValue.status, customer = previusValue.customer)
}

fun BookModel.toBookResponse(): BookResponse {
    return BookResponse(id = this.id, name = this.name, price = this.price, status = this.status,
            custmer = this.customer.let { it?.toCustomerResponse() }
    )
}

fun CustomerModel.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(id = this.id, name = this.name, email = this.email, status = this.status)
}

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
            itens = this.content,
            currentPage = this.number,
            totalPages = this.totalPages,
            totalItens = this.totalElements)
}