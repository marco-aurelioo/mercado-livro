package com.mercadolivro.service

import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
        var repository: PurchaseRepository,
        var applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchase: PurchaseModel) {
        repository.save(purchase)
        applicationEventPublisher.publishEvent(
                PurchaseEvent(this, purchase))
    }

    fun update(purchase: PurchaseModel) {
        repository.save(purchase)
    }
}