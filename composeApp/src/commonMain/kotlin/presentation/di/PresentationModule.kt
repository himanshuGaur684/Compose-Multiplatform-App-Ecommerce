package presentation.di

import org.koin.dsl.module
import presentation.screens.ProductListViewModel
import presentation.screens.cart.CartViewModel
import presentation.screens.product_details.ProductDetailsViewModel

val presentationModule = module {

    factory { ProductListViewModel(get()) }
    factory { ProductDetailsViewModel(get(), get()) }
    factory { CartViewModel(get(), get(), get()) }

}