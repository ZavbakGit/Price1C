package `fun`.gladkikh.app.price1c.framework.room

import `fun`.gladkikh.app.price1c.entity.ItemPrice
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemPriceDao {
    @Insert
    fun insert(item: ItemPrice)

    @Insert
    fun insert(item: List<ItemPrice>)

    @Query("DELETE FROM ItemPrice")
    fun dellALL()

    @Query("SELECT * FROM ItemPrice")
    fun getAll(): LiveData<List<ItemPrice>>

    @Query("SELECT * FROM ItemPrice WHERE name LIKE:search AND valuta =:valu")
    fun getAllWithNameLike(search: String,valu:String): LiveData<List<ItemPrice>>

}