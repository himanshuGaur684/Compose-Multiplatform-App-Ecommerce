package presentation.screens.cart

import data.utils.NetworkResult
import domain.model.ProductDetails
import domain.use_cases.DeleteProductDetailsUseCase
import domain.use_cases.GetAllProductDetailsUseCase
import domain.use_cases.InsertProductDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class CartStateHolder(
    val isLoading: Boolean = false,
    val data: List<ProductDetails>? = emptyList(),
    val error: String = ""
)

class CartViewModel(
    private val insertProductDetailsUseCase: InsertProductDetailsUseCase,
    private val getAllProductDetailsUseCase: GetAllProductDetailsUseCase,
    private val deleteProductDetailsUseCase: DeleteProductDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartStateHolder())
    val uiState: StateFlow<CartStateHolder> = _uiState.asStateFlow()

    init {
        getAllProduct()
    }


    fun getAllProduct() = getAllProductDetailsUseCase().onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { CartStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { CartStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { CartStateHolder(error = res.message) }

            }
        }

    }.launchIn(viewModelScope)


    fun delete(id: Int) = deleteProductDetailsUseCase(id)
        .onEach { res ->
            _uiState.update { CartStateHolder(data = res) }
        }
        .launchIn(viewModelScope)


}