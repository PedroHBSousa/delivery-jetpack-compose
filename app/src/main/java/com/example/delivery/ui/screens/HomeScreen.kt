package com.example.delivery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delivery.model.Product
import com.example.delivery.sampledata.sampleProducts
import com.example.delivery.ui.components.CardProductItem
import com.example.delivery.ui.components.ProductsSection
import com.example.delivery.ui.components.SearchTextField

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    sections: Map<String, List<Product>>,
    searchText: String = ""
) {
    Column(
        modifier,
    ) {
        var text by remember { mutableStateOf(searchText) }
        SearchTextField(
            searchText = text,
            onSearchChange = {
                text = it
            },
        )

        val searchedProducts = remember(text) {
            sampleProducts.filter { product ->
                product.name.contains(text, ignoreCase = true) ||
                        product.description?.contains(text, ignoreCase = true) ?: false
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (text.isBlank()) {
                sections.forEach { section ->
                    val title = section.key
                    val products = section.value
                    item {
                        ProductsSection(
                            title = title,
                            products = products
                        )
                    }
                }
            } else {
                items(searchedProducts) { p ->
                    CardProductItem(product = p, Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}