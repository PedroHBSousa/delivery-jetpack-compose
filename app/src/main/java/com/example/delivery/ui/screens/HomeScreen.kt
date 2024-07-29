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
import com.example.delivery.sampledata.sampleCandies
import com.example.delivery.sampledata.sampleDrinks
import com.example.delivery.sampledata.sampleProducts
import com.example.delivery.ui.components.CardProductItem
import com.example.delivery.ui.components.ProductsSection
import com.example.delivery.ui.components.SearchTextField

class HomeScreenUiState (
    val sections: Map<String, List<Product>> = emptyMap(),
    val searchedProducts: List<Product> = emptyList(),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {}
) {

    fun isShowSections(): Boolean {
        return searchText.isBlank()
    }

}

@Composable
fun HomeScreen(products: List<Product>) {

    val sections = mapOf(
        "Todos produtos" to products,
        "Promoçoes" to sampleDrinks + sampleCandies,
        "Doces" to sampleCandies,
        "Bebidas" to sampleDrinks
    )

    var text by remember {
        mutableStateOf("")
    }

    fun containsInNameOrDescription() = { product: Product ->
        product.name.contains(text, ignoreCase = true) || product.description?.contains(
            text,
            ignoreCase = true
        ) ?: false
    }

    val searchedProducts = remember (text, products) {
        if (text.isNotBlank()) {
            sampleProducts.filter(containsInNameOrDescription()) + products.filter(
                containsInNameOrDescription())
        } else emptyList()
    }

    val state = remember(products, text) {
        HomeScreenUiState(
            sections = sections,
            searchedProducts = searchedProducts,
            searchText = text,
            onSearchChange = {
                text = it
            }
        )
    }
    HomeScreen(state = state)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenUiState = HomeScreenUiState()
) {
    Column(
        modifier,
    ) {
        val sections = state.sections
        val text = state.searchText
        val searchedProducts = state.searchedProducts
        SearchTextField(
            searchText = text,
            onSearchChange = state.onSearchChange,
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (state.isShowSections()) {
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