package `fun`.gladkikh.app.price1c

import `fun`.gladkikh.app.price1c.usecase.downLoadPrice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvMess.setOnClickListener {
            downLoadPrice(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Toast.makeText(this, "Загрузка...", Toast.LENGTH_SHORT).show()
                }
                .subscribe({
                    Toast.makeText(this, "Загрузили!...", Toast.LENGTH_SHORT).show()
                },{
                    Toast.makeText(this, "Ошибка ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                })
        }


        App.database.getItemPriceDao().getAll().observe(this, Observer {
            Toast.makeText(
                this,
                "Изменения ${it.size}"
                , Toast.LENGTH_SHORT
            ).show()
        })

    }


    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}
