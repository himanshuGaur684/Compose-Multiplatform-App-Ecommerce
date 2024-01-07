package presentation.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.screens.ProductListScreen
import presentation.screens.ProductListViewModel
import presentation.screens.cart.CartScreen
import presentation.screens.cart.CartViewModel
import presentation.screens.product_details.ProductDetailsScreen
import presentation.screens.product_details.ProductDetailsViewModel

@Composable
fun AppNavigation() {
    val navigator = rememberNavigator()
    NavHost(navigator = navigator, initialRoute = NavigationRoute.ProductList.route) {
        scene(route = NavigationRoute.ProductList.route) {
            val viewModel: ProductListViewModel = koinViewModel(ProductListViewModel::class)
            ProductListScreen(viewModel, navigator) {
                navigator.navigate(NavigationRoute.ProductDetails.getRoute(it))
            }
        }
        scene(route = NavigationRoute.ProductDetails.route) {
            val id = it.path.filter { it.isDigit() }
            val viewModel = koinViewModel(ProductDetailsViewModel::class)
            viewModel.getProductDetails(id.toInt())
            ProductDetailsScreen(navigator, viewModel) {
                viewModel.insert(it.id, it.title, it.desc, it.image)
            }
        }
        scene(NavigationRoute.CartScreen.route) {
            val viewModel = koinViewModel(CartViewModel::class)
            CartScreen(viewModel, navigator) {
                viewModel.delete(it)
            }
        }

    }


}

sealed class NavigationRoute(val route: String) {
    object ProductList : NavigationRoute("/product_list")
    object ProductDetails : NavigationRoute("/product_details/{id}") {
        fun getRoute(id: Int) = "/product_details/${id}"
    }

    object CartScreen : NavigationRoute("/cart")
}