package com.mercadolivro.controller

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.controller.response.FieldErrorResponse
import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.model.ML_ERROR
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.sql.SQLIntegrityConstraintViolationException

@ControllerAdvice
class MercadoLivroControllerAdvide {

    @ExceptionHandler(NotFoundException::class)
    fun advideNotFoundException(ex: NotFoundException, resp: WebRequest): ResponseEntity<ErrorResponse> {
        var error = ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                ex.code,
                null
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun advideMethodArgumentNotValidException(ex: MethodArgumentNotValidException, resp: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ML_ERROR.ML102.message,
                ML_ERROR.ML102.code,
                ex.bindingResult.fieldErrors.map { FieldErrorResponse( it.defaultMessage ?: "INVALID" , it.field ) }
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
    fun advideSQLIntegrityConstraintViolationException(ex: SQLIntegrityConstraintViolationException, resp: WebRequest): ResponseEntity<ErrorResponse> {
        var error = ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.message ?: "ERROR",
                ex.errorCode.toString(),
                null
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

}