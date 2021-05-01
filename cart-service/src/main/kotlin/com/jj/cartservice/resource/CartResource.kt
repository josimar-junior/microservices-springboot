package com.jj.cartservice.resource

import com.jj.cartservice.model.Product
import com.jj.cartservice.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/carts")
class CartResource(private val repository: ProductRepository) {

    @GetMapping("/products")
    fun getProducts(): ResponseEntity<List<Product>> = ResponseEntity.ok(repository.findAll())
}