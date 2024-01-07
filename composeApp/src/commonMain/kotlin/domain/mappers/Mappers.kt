package domain.mappers

import data.model.ProductDTO
import domain.model.Product
import domain.model.ProductDetails

fun List<ProductDTO>.toDomain(): List<Product> = map {
    Product(
        image = it.image,
        title = it.title,
        id = it.id
    )
}

fun ProductDTO.toDomain(): ProductDetails {
    return ProductDetails(
        title = this.title,
        id = this.id,
        image = this.image,
        desc = this.description
    )
}