package `fun`.gladkikh.app.price1c

import `fun`.gladkikh.app.price1c.util.net.downloadFile
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Single.just("http://1c.ru/ftp/pub/pricelst/price_1c.zip")
            .map {
                downloadFile(url = it, dir = cacheDir)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                Toast.makeText(this,"${it.name} ${it.length()}",Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
            })

    }
}
