package presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.navigation.NavigationRoute

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    navigator: Navigator,
    onClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            contentColor = Color.Black,
            backgroundColor = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Product Lists", style = MaterialTheme.typography.h6, color = Black)
                Image(imageVector = Icons.Default.ShoppingCart, contentDescription = null,
                    modifier = Modifier.clickable {
                        navigator.navigate(NavigationRoute.CartScreen.route)
                    })
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
                    Text(text = uiState.value.error.toString())
                }
            }

            else -> {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    uiState.value.data?.let { list ->
                        items(list) {
                            Column(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                    .clickable {
                                        onClick(it.id)
                                    }
                                    .padding(12.dp)
                            ) {
                                KamelImage(
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    resource = asyncPainterResource(it.image),
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.body1,
                                    overflow = TextOverflow.Ellipsis, maxLines = 2
                                )

                            }
                        }
                    }

                }
            }

        }
    }


}