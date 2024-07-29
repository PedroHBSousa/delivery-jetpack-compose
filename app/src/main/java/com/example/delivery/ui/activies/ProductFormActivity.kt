package com.example.delivery.ui.activies

import android.content.ContentValues.TAG
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.delivery.R
import com.example.delivery.dao.ProductDao
import com.example.delivery.model.Product
import com.example.delivery.ui.theme.DeliveryTheme
import java.math.BigDecimal

class ProductFormActivity : ComponentActivity() {

    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.setContent {
            DeliveryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductFormScreen( modifier = Modifier.padding(innerPadding), onSaveClick = { product ->
                        dao.save(product)
                        finish()
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductFormScreen(
    modifier: Modifier = Modifier,
    onSaveClick: (Product) -> Unit ={}
    ) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .imeNestedScroll(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier)
        Text(text = "Adicione um produto", Modifier.fillMaxWidth(), fontSize = 20.sp)

        var url by remember { mutableStateOf("") }

        if (url.isNotBlank()) {
            AsyncImage(
                model = url,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )
        }

        TextField(
            value = url, onValueChange = { url = it },
            Modifier
                .fillMaxWidth()
                .height(60.dp), label = {
                Text(text = "Insira a URL da imagem")
            }, shape = RoundedCornerShape(40),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            )
        )

        var name by remember { mutableStateOf("") }
        TextField(value = name, onValueChange = { name = it }, Modifier.fillMaxWidth(), label = {
            Text(text = "Nome")
        }, shape = RoundedCornerShape(40),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )

        var price by remember { mutableStateOf("") }

        var isPriceError by remember {
            mutableStateOf(false)
        }

        TextField(
            value = price,
            onValueChange = {
                isPriceError = try {
                    BigDecimal(it)
                    false
                } catch (e: IllegalArgumentException) {
                    it.isNotEmpty()
                }
                price = it
            },
            Modifier.fillMaxWidth(),
            isError = isPriceError,
            label = { Text(text = "Preço") },
            shape = RoundedCornerShape(40),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )
        if (isPriceError) {
            Text(
                text = "Preço deve ser um número decimal",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }



        var description by remember { mutableStateOf("") }
        TextField(
            value = description,
            onValueChange = { description = it },
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = {
                Text(text = "Descrição")
            }, shape = RoundedCornerShape(15),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
                val convertedPrice = try {
                    BigDecimal(price)
                } catch (e: NumberFormatException) {
                    BigDecimal.ZERO
                }
                val product = Product(
                    name = name,
                    image = url,
                    price = convertedPrice,
                    description = description
                )
                Log.i("ProductFormActivity", "ProductFormScreen: $product")
                onSaveClick(product)
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Publicar")
        }
        Spacer(modifier = Modifier)
    }
}
