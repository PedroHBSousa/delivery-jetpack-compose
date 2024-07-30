package com.example.delivery.ui.activies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.delivery.ui.screens.ProductFormScreen
import com.example.delivery.ui.theme.DeliveryTheme
import com.example.delivery.viewmodels.ProductFormScreenViewModel

class ProductFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.setContent {
            DeliveryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ProductFormScreenViewModel by viewModels()
                    ProductFormScreen( modifier = Modifier.padding(innerPadding), viewModel = viewModel, onSaveClick = {finish()})
                }
            }
        }
    }
}


