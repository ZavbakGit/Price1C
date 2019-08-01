package `fun`.gladkikh.app.price1c.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ScopeProvider
import io.reactivex.CompletableSource
import io.reactivex.Observable
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AboutViewModel : BaseViewModel() {



    private val searchPublishSubject = PublishSubject.create<String?>()

    init {
        Observable.interval(1,TimeUnit.SECONDS)
            .doOnDispose {
                val r = "kuhjkh"
            }
            .`as`(AutoDispose.autoDisposable(this))
            .subscribe {
                Log.d("anit",it.toString())
            }
    }


    fun changeSearchString(str: String) {
        searchPublishSubject.onNext(str)
    }

}