package `fun`.gladkikh.app.price1c


import `fun`.gladkikh.app.price1c.framework.room.AppDatabase
import android.app.Application
import androidx.room.Room


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
        lateinit var appContext: Application
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        database = Room.databaseBuilder(this, AppDatabase::class.java, "mydatabase")
            .allowMainThreadQueries()
            .build()
    }
}