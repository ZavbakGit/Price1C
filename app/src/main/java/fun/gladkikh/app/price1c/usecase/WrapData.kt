package `fun`.gladkikh.app.price1c.usecase

import `fun`.gladkikh.app.price1c.entity.ItemPrice

data class WrapData(
    val dataPriceStr: String,
    val listItem: List<ItemPrice>
)