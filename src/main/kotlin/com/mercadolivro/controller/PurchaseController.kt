package com.mercadolivro.controller

import com.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.controller.request.PurchaseRequest
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/purchase")
class PurchaseController(
        var purchaseMapper: PurchaseMapper,
        var purchaseService: PurchaseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPurchase(@RequestBody @Valid request : PurchaseRequest): ResponseEntity<Any>{
        purchaseService.create(purchaseMapper.toPurchaseModel(request))
        return ResponseEntity(true,HttpStatus.CREATED)
    }
}