package com.example.delivery.dao

import androidx.compose.runtime.mutableStateListOf
import com.example.delivery.model.Product
import com.example.delivery.sampledata.sampleProducts

class ProductDao {

    companion object{
        private val products = mutableStateListOf<Product>(/**sampleProducts.toTypedArray()*/)
    }

    fun products() = products.toList()
    fun save(product: Product) {
        products.add(product)

    }

}