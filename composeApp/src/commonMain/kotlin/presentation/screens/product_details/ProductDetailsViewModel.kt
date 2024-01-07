package presentation.screens.product_details

import data.utils.NetworkResult
import domain.model.ProductDetails
import domain.use_cases.GetProductDetailsUseCase
import domain.use_cases.InsertProductDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class ProductDetailsStateHolder(
    val isLoading: Boolean = false,
    val data: ProductDetails? = null,
    val error: String = ""
)

class ProductDetailsViewModel(
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val insertProductDetailsUseCase: InsertProductDetailsUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsStateHolder())
    val uiState: StateFlow<ProductDetailsStateHolder> = _uiState.asStateFlow()

    fun getProductDetails(id: Int) = productDetailsUseCase(id).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { ProductDetailsStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { ProductDetailsStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { ProductDetailsStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)

    fun insert(id: Int, title: String, desc: String, image: String) = insertProductDetailsUseCase(
        id = id, title = title, desc = desc, image = image
    ).launchIn(viewModelScope)


}