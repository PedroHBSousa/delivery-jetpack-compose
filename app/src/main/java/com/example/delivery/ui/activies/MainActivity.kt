package com.example.delivery.ui.activies

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.delivery.dao.ProductDao
import com.example.delivery.model.Product
import com.example.delivery.sampledata.sampleCandies
import com.example.delivery.sampledata.sampleDrinks
import com.example.delivery.sampledata.sampleProducts
import com.example.delivery.ui.screens.HomeScreen
import com.example.delivery.ui.screens.HomeScreenUiState
import com.example.delivery.ui.theme.DeliveryTheme

class MainActivity : ComponentActivity() {

    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(onFabClick = {
                startActivity(
                    Intent(this, ProductFormActivity::class.java)
                )
            }) {
                val products = dao.products()
                HomeScreen(products = products)
            }
        }
    }
}

@Composable
fun App(onFabClick: () -> Unit = {}, content: @Composable () -> Unit = {}) {
    DeliveryTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(onClick = onFabClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        ) { innerPadding ->
            /*HomeScreen(modifier = Modifier.padding(innerPadding), sampleSections)*/
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}


