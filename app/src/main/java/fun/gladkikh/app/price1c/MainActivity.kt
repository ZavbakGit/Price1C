package `fun`.gladkikh.app.price1c

import `fun`.gladkikh.app.price1c.usecase.downLoadPrice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downLoadPrice(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Toast.makeText(this,"Загрузка...",Toast.LENGTH_SHORT).show()
            }
            .subscribe({
                Toast.makeText(this,"Загружено ${it.size} товаров!",Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }


    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}
