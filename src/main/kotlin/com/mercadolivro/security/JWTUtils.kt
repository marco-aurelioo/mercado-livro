package com.mercadolivro.security

import com.mercadolivro.exceptions.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtils {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generationToken(id: String): String {
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(Date(System.currentTimeMillis() + expiration!!))
                .signWith(SignatureAlgorithm.HS512,secret!!.toByteArray())
                .compact()
    }

    fun validToken(token: String): Boolean {
        var clains = getClains(token)
        if(clains.subject == null || clains.expiration == null || Date().after(clains.expiration)){
            return false
        }
        return true
    }

    private fun getClains(token: String): Claims {
        try{
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        }catch(ex: Exception){
            throw AuthenticationException("Erro validando token.")
        }
    }

    fun getSubject(token: String): String {
        return getClains(token).subject
    }
}