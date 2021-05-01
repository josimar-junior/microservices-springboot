package com.jj.cartservice.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Product(

    @Id
    var id: Long,

    var name: String,

    var price: Double,
)
