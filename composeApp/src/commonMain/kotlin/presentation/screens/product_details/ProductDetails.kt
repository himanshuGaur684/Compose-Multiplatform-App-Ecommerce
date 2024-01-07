package presentation.screens.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.ProductDetails
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarTitle

@Composable
fun ProductDetailsScreen(
    navigator: Navigator,
    viewModel: ProductDetailsViewModel,
    onClick: (ProductDetails) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            contentColor = Color.Black,
            backgroundColor = Color.White
        ) {
            ToolBarTitle("Product Details") {
                navigator.goBack()
            }
        }
    }) {
        when {
            uiState.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.value.error.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.value.error)
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        .padding(12.dp)
                ) {
                    uiState.value.data?.let { value ->
                        KamelImage(
                            resource = asyncPainterResource(value.image), contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = value.title, style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = value.desc, style = MaterialTheme.typography.body1)

                        Spacer(Modifier.height(12.dp))
                        Button(onClick = {
                            onClick(value)
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.Center

                            ) {
                                Image(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add To Cart")
                            }
                        }

                    }
                }
            }
        }
    }


}