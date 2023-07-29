package com.mercadolivro.security

import com.mercadolivro.exceptions.AuthenticationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private var userDetailsService: UserDetailsService,
        var jwtUtils: JWTUtils
):
        BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        if(authorization != null && authorization.startsWith("Bearer")){
            val auth =  getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }
            chain.doFilter(request,response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtils.validToken(token)){
            throw AuthenticationException("Token invalido")
        }
        val subject = jwtUtils.getSubject(token)
        val customer = userDetailsService.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(customer,null,customer.authorities)
    }

}