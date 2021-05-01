package com.jj.productservice.resource

import com.fasterxml.jackson.core.JsonProcessingException
import com.jj.productservice.model.Product
import com.jj.productservice.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/products")
class ProductResource(private val service: ProductService) {

    @PostMapping
    fun save(@RequestBody product: Product): ResponseEntity<Void> {
        val productSaved: Product = service.save(product)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSaved.id).toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Product>> = ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}/add-cart")
    fun addToCart(@PathVariable id: Long): ResponseEntity<Product> {
        return try {
            val product: Product = service.addToCart(id)

            ResponseEntity(product, HttpStatus.OK)
        } catch(e: RuntimeException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}