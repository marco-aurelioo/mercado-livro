package com.mercadolivro.security

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.CustomerStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomerDetails(var customer: CustomerModel) : UserDetails {

    var id = customer.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            customer.role!!.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String? = customer.password

    override fun getUsername(): String = customer.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = customer.status == CustomerStatus.ATIVO
}