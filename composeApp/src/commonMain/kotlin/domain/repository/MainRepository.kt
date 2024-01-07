package domain.repository

import domain.model.Product
import domain.model.ProductDetails

interface MainRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductDetails(id: Int): ProductDetails

    suspend fun insert(id: Int, title: String, desc: String, image: String)

    suspend fun delete(id: Int)

    fun getProductList(): List<ProductDetails>

}