package com.jj.cartservice.jms

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.jj.cartservice.model.Product
import com.jj.cartservice.repository.ProductRepository
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class JmsConsumer(private val repository: ProductRepository) {

    @JmsListener(destination = "\${product.jms.destination}")
    fun consumer(data: String) {
        try {
            val mapper: ObjectMapper = ObjectMapper()
            val product: Product = mapper.readValue(data, Product::class.java)
            repository.save(product)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
    }
}