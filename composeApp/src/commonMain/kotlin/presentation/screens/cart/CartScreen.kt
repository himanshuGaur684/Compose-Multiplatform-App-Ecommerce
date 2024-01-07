package presentation.screens.cart

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarTitle


@Composable
fun CartScreen(viewModel: CartViewModel, navigator: Navigator, onClick: (Int) -> Unit) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(contentColor = Color.Black, backgroundColor = Color.White) {
            ToolBarTitle("Cart") {
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
                if (uiState.value.data.isNullOrEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Cart is Empty")
                    }
                } else {
                    LazyColumn {
                        items(uiState.value.data!!) {
                            Column(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                    .padding(12.dp)
                            ) {
                                it?.let { value ->
                                    KamelImage(
                                        resource = asyncPainterResource(value.image),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth().height(300.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(text = value.title, style = MaterialTheme.typography.h5)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(text = value.desc, style = MaterialTheme.typography.body1)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(onClick = {
                                        onClick(it.id)
                                    }) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

