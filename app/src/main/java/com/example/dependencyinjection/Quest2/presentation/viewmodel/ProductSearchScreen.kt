package com.example.dependencyinjection.Quest2.presentation.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.dependencyinjection.R

@Composable
fun ProductSearchScreen() {
    val viewModel: ProductSearchViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState()

    val selectedCategory by viewModel._category.collectAsState()

    Column(Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.onQueryChanged(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            Button(
                onClick = {
                    text = ""
                    viewModel.onCategoryChanged("beauty")
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Beauty",
                        fontSize = 12.sp
                    )
                    if (selectedCategory != "beauty") {
                        Spacer(modifier = Modifier.width(1.dp))
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "beauty"
                        )
                    }
                }
            }
            Spacer(Modifier.width(2.dp))
            Button(
                onClick = {
                text = ""
                viewModel.onCategoryChanged("furniture")
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Furniture",
                        fontSize = 12.sp
                    )
                    if (selectedCategory != "furniture"){
                        Spacer(modifier = Modifier.width(1.dp))
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Furniture icon"
                        )
                    }
                }
            }
            Spacer(Modifier.width(2.dp))
            Button(
                onClick = {
                text = ""
                viewModel.onCategoryChanged("groceries")
            }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Groceries",
                        fontSize = 12.sp
                    )
                    if (selectedCategory != "groceries"){
                        Spacer(modifier = Modifier.width(1.dp))
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Furniture icon"
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { viewModel.onSortOrderChanged(true) }) {
                Text("Sort Asc")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { viewModel.onSortOrderChanged(false) }) {
                Text("Sort Desc")
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { viewModel.onManualRefresh() }) {
            Text("Refresh")
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(products) { product ->
                Row(Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberImagePainter(product.thumbnail),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("${product.title} - $${product.price}", style = MaterialTheme.typography.titleSmall)
                        Text(product.description, maxLines = 2)
                    }
                }
                Divider()
            }
        }
    }
}