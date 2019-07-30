package `fun`.gladkikh.app.price1c.framework.room

import `fun`.gladkikh.app.price1c.entity.ItemPrice
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ItemPrice::class), version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getItemPriceDao(): ItemPriceDao
}