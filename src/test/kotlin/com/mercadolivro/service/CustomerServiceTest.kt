package com.mercadolivro.service

import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.model.*
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
internal class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService


    @Test
    fun `should return all customers`() {
        val fakeCustomers =   PageImpl(listOf<CustomerModel>(buildCustomer(), buildCustomer()))
        every { customerRepository.findAll(Pageable.unpaged()) } returns fakeCustomers

        val customerResult = customerService.getAll(null, Pageable.unpaged())

        assertEquals(customerResult, fakeCustomers)
        verify(exactly = 1) { customerRepository.findAll(Pageable.unpaged())  }
        verify(exactly = 0) { customerRepository.findByNameContaining(any(),any())  }

    }

    @Test
    fun `should return all customers with some name`() {
        val fakeCustomers = PageImpl( listOf<CustomerModel>(buildCustomer().copy(name = "someName")))

        every { customerRepository.findByNameContaining("someName", any()) } returns fakeCustomers

        val customerResult = customerService.getAll("someName", Pageable.unpaged())

        assertEquals(customerResult, fakeCustomers)

        verify(exactly = 0) { customerRepository.findAll()  }
        verify(exactly = 1) { customerRepository.findByNameContaining("someName",any())  }

    }




    @Test
    fun `should get an customer`(){
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)
        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        val customer = customerService.getCustomer(id)

        assertEquals(customer, fakeCustomer)

        verify(exactly = 1) { customerRepository.findById(id) }

    }

    @Test
    fun `should get an customer not found`(){
        val id = Random().nextInt()
        every { customerRepository.findById(id) } returns Optional.empty()


        val error = assertThrows(NotFoundException::class.java){
            customerService.getCustomer(id)
        }

        assertEquals(error.message, String.format(ML_ERROR.ML101.message,id))
        assertEquals(error.code, ML_ERROR.ML101.code)

        verify(exactly = 1) { customerRepository.findById(id) }

    }

    @Test
    fun `should update customer`(){
        val id = Random().nextInt()
        val fakeCustomerModel = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCustomerModel) } returns fakeCustomerModel

        val customer = customerService.update(fakeCustomerModel)

        assertEquals(customer, fakeCustomerModel)

        verify(exactly = 1) { customerRepository.existsById(id)  }
        verify(exactly = 1) { customerRepository.save(fakeCustomerModel)  }

    }

    @Test
    fun `should update customer error notfound`(){
        val id = Random().nextInt()
        val fakeCustomerModel = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns false
        every { customerRepository.save(fakeCustomerModel) } returns fakeCustomerModel

        val error = assertThrows(NotFoundException::class.java){
            customerService.update(fakeCustomerModel)
        }

        assertEquals(error.message, String.format(ML_ERROR.ML101.message,id))
        assertEquals(error.code, ML_ERROR.ML101.code)

        verify(exactly = 1) { customerRepository.existsById(id)  }
        verify(exactly = 0) { customerRepository.save(fakeCustomerModel)  }
    }


    @Test
    fun `should delete a customer`(){
        val id = Random().nextInt()
        val fakeCustomer =  buildCustomer()

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        customerService.delete(id)

        assertEquals(fakeCustomer.status, CustomerStatus.INATIVO)
        verify(exactly = 1) { customerRepository.findById(id) }
        verify(exactly = 1) { customerRepository.save(any())  }


    }

    @Test
    fun `should delete a customer not found`(){
        val id = Random().nextInt()
        val fakeCustomer =  buildCustomer()

        every { customerRepository.findById(id) } returns Optional.empty()
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        customerService.delete(id)

        verify(exactly = 1) { customerRepository.findById(id) }
        verify(exactly = 0) { customerRepository.save(any())  }


    }


    @Test
    fun `should verify customer email exists`(){
        val email = "${UUID.randomUUID().toString()}@teste.com"

        every { customerRepository.existsByEmail(email) } returns true

        val exist = customerService.existsByEmail(email)
        assertEquals(exist, true)
        verify(exactly = 1) { customerRepository.existsByEmail(email)  }

    }


    @Test
    fun `should verify customer email not exists`(){
        val email = "${UUID.randomUUID().toString()}@teste.com"

        every { customerRepository.existsByEmail(email) } returns false

        val exist = customerService.existsByEmail(email)
        assertEquals(exist, false)
        verify(exactly = 1) { customerRepository.existsByEmail(email)  }

    }

    @Test
    fun `should create customer`(){
        val id = Random().nextInt()
        val fakePass = "XPTO"
        val fakeCustomer =  buildCustomer(id = id)

        every { bCrypt.encode(any()) } returns fakePass
        every { customerRepository.save(any()) } returns fakeCustomer

        customerService.create(fakeCustomer)

        verify(exactly = 1) { bCrypt.encode(fakeCustomer.password)  }
        verify(exactly = 1) { customerRepository.save(any())  }

    }


}