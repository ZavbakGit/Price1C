package `fun`.gladkikh.app.price1c.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity:AppCompatActivity(){

    private val publishSubjectKeyClick = PublishSubject.create<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        onCreateInit()
    }

    protected abstract fun getLayout(): Int
    protected abstract fun onCreateInit()


}