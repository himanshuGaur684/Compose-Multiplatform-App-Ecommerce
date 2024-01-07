package domain.di

import domain.use_cases.DeleteProductDetailsUseCase
import domain.use_cases.GetAllProductDetailsUseCase
import domain.use_cases.GetProductDetailsUseCase
import domain.use_cases.GetProductListUseCase
import domain.use_cases.InsertProductDetailsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetProductListUseCase() }
    factory { GetProductDetailsUseCase() }
    factory { InsertProductDetailsUseCase() }
    factory { DeleteProductDetailsUseCase() }
    factory { GetAllProductDetailsUseCase() }

}