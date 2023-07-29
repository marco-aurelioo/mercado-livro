package com.mercadolivro.config

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.JWTUtils
import com.mercadolivro.service.UserDetailsCustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
       private var userDetailsService: UserDetailsCustomerService,
       private var customerRepository: CustomerRepository,
       private val jwtUtils: JWTUtils
): WebSecurityConfigurerAdapter() {

    @Bean
    fun bcCrypt(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

    private val PUBLIC_POST_MATCHERS = arrayOf(
            "/customer"
    )

    private val PUBLIC_URLS = arrayOf(
            "/swagger/**"
    )

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder){
        auth.userDetailsService(userDetailsService).passwordEncoder(bcCrypt())
    }

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .csrf()
                .disable()

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS)
                .permitAll()
                .anyRequest().authenticated()

        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtils ))
        http.addFilter(AuthorizationFilter(authenticationManager(),userDetailsService,jwtUtils))

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }




}