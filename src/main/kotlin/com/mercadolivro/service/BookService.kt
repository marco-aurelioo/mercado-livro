package com.mercadolivro.service

import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.ML_ERROR
import com.mercadolivro.model.STATUS
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService( val repository: BookRepository) {

    fun create(bookModel: BookModel): BookModel{
        return repository.save(bookModel)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return repository.findAll(pageable)
    }

    fun findById(id: Int): BookModel {
        return repository.findById(id).orElseThrow {
            NotFoundException(
                    ML_ERROR.ML201.message.format(id),
                    ML_ERROR.ML201.code)
        }
    }

    fun delete(id: Int){
        var book = repository.findById(id).get()
        book.status = STATUS.INATIVO
        repository.save(book)
    }

    fun update(book: BookModel): BookModel {
        return repository.save(book)
    }

    fun findByIds(books: Set<Int>): MutableList<BookModel> {
        return repository.findAllById(books).toMutableList()
    }

    fun updateAll(books: List<BookModel>) {
        repository.saveAll(books)
    }

}
