package com.mercadolivro.validation

import com.mercadolivro.model.STATUS
import com.mercadolivro.service.BookService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class BooksAvaliableToPurchaseValidator(
        var bookService: BookService
): ConstraintValidator<BooksAvaliableToPurchase, Set<Int>> {

    override fun isValid(value: Set<Int>?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty())
            return false
        bookService.findByIds(value).forEach(){
            if(it.status != STATUS.ATIVO){
                return false
            }
        }
        return true
    }

}
