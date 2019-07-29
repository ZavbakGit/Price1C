package `fun`.gladkikh.app.price1c.intity


data class Item(val code:String,
                val name:String,
                val valuta: Valuta,
                val storePrice:Float,
                val dealerPrice:Float,
                val partnerPrice:Float,
                val vat: Vat,
                val storage:String,
                val startSale: Long,
                val comment: String)


