package com.jj.productservice.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.jj.productservice.model.Product
import com.jj.productservice.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class ProductService(private val repository: ProductRepository,
                     private val jmsTemplate: JmsTemplate) {

    @Value("\${product.jms.destination}")
    lateinit var jmsQueue: String

    fun save(product: Product): Product = repository.save(product)

    fun findAll(): List<Product> = repository.findAll()

    fun addToCart(id: Long): Product {
        val product: Optional<Product> = repository.findById(id)
        if(!product.isPresent) {
            throw RuntimeException("Product not found")
        }

        try {
            val mapper: ObjectMapper = ObjectMapper()
            val valueAsString = mapper.writeValueAsString(product.get())
            jmsTemplate.convertAndSend(jmsQueue, valueAsString)

            return product.get()
        } catch (e: JsonProcessingException) {
            throw e
        }
    }
}