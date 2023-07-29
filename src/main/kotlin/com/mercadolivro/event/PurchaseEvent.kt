package com.mercadolivro.event

import com.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
        source: Any,
        var purchaseModel: PurchaseModel) : ApplicationEvent(source)