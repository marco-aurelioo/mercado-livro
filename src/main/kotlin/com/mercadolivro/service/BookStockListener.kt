package com.mercadolivro.service

import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.model.STATUS
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BookStockListener(
        var bookService: BookService
) {

    @EventListener
    fun stopUpdate( purchaseEvent: PurchaseEvent){
        println("Atualizando estoque.")
        var books = purchaseEvent.purchaseModel.copy().books
        books.forEach(){
            it.status = STATUS.VENDIDO
        }
        bookService.updateAll(books)
    }
}