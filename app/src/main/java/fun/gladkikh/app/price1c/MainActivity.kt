package `fun`.gladkikh.app.price1c

import `fun`.gladkikh.app.price1c.util.net.downloadFile
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable.add(

        Single.just("http://1c.ru/ftp/pub/pricelst/price_1c_.zip")
            .map {
                downloadFile(url = it, dir = cacheDir)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                Toast.makeText(this,"${it!!.name} ${it!!.length()}",Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }))

    }


    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}
