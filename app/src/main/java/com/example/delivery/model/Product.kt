package com.example.delivery.model

import androidx.annotation.DrawableRes
import java.math.BigDecimal

class Product (val name: String,
               val price: BigDecimal,
               val description: String? = null,
               val image: String? = null,
)