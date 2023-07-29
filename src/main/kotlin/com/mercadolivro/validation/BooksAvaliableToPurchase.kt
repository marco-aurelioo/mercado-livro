package com.mercadolivro.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [BooksAvaliableToPurchaseValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class BooksAvaliableToPurchase (
        val message: String = "Book não disponivel para venda.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
        )