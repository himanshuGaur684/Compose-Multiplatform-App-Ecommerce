package domain.use_cases

import data.utils.NetworkResult
import domain.model.ProductDetails
import domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAllProductDetailsUseCase : KoinComponent {

    private val mainRepository: MainRepository by inject()

    operator fun invoke() = flow<NetworkResult<List<ProductDetails>>> {
        emit(NetworkResult.Loading())
        emit(NetworkResult.Success(data = mainRepository.getProductList()))
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}