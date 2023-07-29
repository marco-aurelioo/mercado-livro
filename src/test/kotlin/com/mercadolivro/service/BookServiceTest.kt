package com.mercadolivro.service

import com.mercadolivro.helper.buildBook
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

@ExtendWith(MockKExtension::class)
internal class BookServiceTest {


    @MockK
    private lateinit var repository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    @Test
    fun `create book with success`(){
        var fakeBook = buildBook()

        every { repository.save(fakeBook) } returns fakeBook

        bookService.create(fakeBook)

        verify(exactly = 1) { repository.save(fakeBook)  }
    }


    @Test
    fun `should find all books`(){
        var pageable = Pageable.unpaged()
        var fakeResult = PageImpl<BookModel>(listOf(buildBook(id = Random().nextInt())))

        every { repository.findAll(pageable) } returns fakeResult
        var pageResult = bookService.findAll(pageable)

        assertEquals(fakeResult,pageResult)

        verify(exactly = 1 ) { repository.findAll(pageable)  }

    }


    @Test
    fun `should find especific book by id`(){
        

    }

    fun findById() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun update() {
    }

    @Test
    fun findByIds() {
    }

    @Test
    fun updateAll() {
    }

    @Test
    fun getRepository() {
    }
}