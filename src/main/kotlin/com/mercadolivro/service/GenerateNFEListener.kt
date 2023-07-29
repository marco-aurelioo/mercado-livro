package com.mercadolivro.service

import com.mercadolivro.event.PurchaseEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNFEListener(
        var purchaseService: PurchaseService
) {

    @EventListener
    fun readPurchaseModelEvent(purchaseEvent : PurchaseEvent){
        var purchase = purchaseEvent.purchaseModel
                .copy( nfe = UUID.randomUUID().toString() )
        purchaseService.update(purchase)
    }
}