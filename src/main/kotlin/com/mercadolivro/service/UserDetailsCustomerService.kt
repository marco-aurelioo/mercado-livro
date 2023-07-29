package com.mercadolivro.service

import com.mercadolivro.exceptions.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserCustomerDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomerService(
        private var customerRepository: CustomerRepository
) : UserDetailsService{
    override fun loadUserByUsername(id: String): UserDetails {
        var customer = customerRepository.findById( id.toInt())
                .orElseThrow() { AuthenticationException("Usuario invalido.")}
        return UserCustomerDetails(customer)
    }
}