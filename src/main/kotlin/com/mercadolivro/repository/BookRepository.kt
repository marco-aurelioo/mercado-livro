package com.mercadolivro.repository

import com.mercadolivro.model.BookModel
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<BookModel, Int> {

}
