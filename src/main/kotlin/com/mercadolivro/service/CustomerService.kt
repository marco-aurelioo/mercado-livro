package com.mercadolivro.service

import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.CustomerStatus
import com.mercadolivro.model.ML_ERROR
import com.mercadolivro.model.ROLE
import com.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bcBCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun getAll(name: String?, pageable: Pageable): Page<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it, pageable)
        }
        return customerRepository.findAll(pageable)
    }

    fun create(customer: CustomerModel): CustomerModel {
        var newCustomer =
                customer.copy(
                    role = setOf(ROLE.CUSTOMER_ROLE),
                    password = bcBCryptPasswordEncoder.encode(customer.password),
                    status = CustomerStatus.ATIVO )
        return customerRepository.save(newCustomer)
    }

    fun getCustomer(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow {
            NotFoundException(
                    ML_ERROR.ML101.message.format(id),
                    ML_ERROR.ML101.code)
        }
    }

    fun update(customer: CustomerModel): CustomerModel {
        if(!customerRepository.existsById(customer.id!!)){
            throw  NotFoundException(
                    ML_ERROR.ML101.message.format(customer.id!!),
                    ML_ERROR.ML101.code)
        }

        return customerRepository.save(customer)
    }

    fun delete(id: Int) {
        var customerOpt = customerRepository.findById(id)
        if(!customerOpt.isEmpty){
            val customer = customerOpt.get()
            customer.status = CustomerStatus.INATIVO
            customerRepository.save(customer)
        }
    }

    fun existsByEmail(email: String): Boolean {
        return customerRepository.existsByEmail(email)
    }

}