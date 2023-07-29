package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.PageResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toBookResponse
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/book")
class BookController(val customerService: CustomerService, val bookService: BookService) {


    @PostMapping()
    fun create(@Valid @RequestBody book: PostBookRequest): BookResponse {
        var customer = customerService.getCustomer(book.customerId)
        return bookService.create(book.toBookModel(customer)).toBookResponse()
    }

    @GetMapping()
    fun findAll(@PageableDefault(page = 0, size = 20) pageable: Pageable):
            PageResponse<BookResponse> {
        return bookService.findAll(pageable).map { it.toBookResponse() }
                .toPageResponse()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Int): BookResponse {
        return bookService.findById(id).toBookResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int, @Valid @RequestBody putBookRequest: PutBookRequest): BookResponse {
        return bookService.update(putBookRequest.toBookModel(bookService.findById(id))).toBookResponse()
    }


}