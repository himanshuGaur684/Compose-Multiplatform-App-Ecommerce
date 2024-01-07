package presentation.screens

import data.utils.NetworkResult
import domain.model.Product
import domain.use_cases.GetProductListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class ProductListStateHolder(
    val isLoading: Boolean = false,
    val data: List<Product>? = null,
    val error: String = ""
)

class ProductListViewModel(private val getProductListUseCase: GetProductListUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListStateHolder())
    val uiState: StateFlow<ProductListStateHolder> = _uiState.asStateFlow()

    init {
        getProductList()
    }

    fun getProductList() = getProductListUseCase().onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { ProductListStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { ProductListStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { ProductListStateHolder(error = res.message) }
            }
        }

    }.launchIn(viewModelScope)

}