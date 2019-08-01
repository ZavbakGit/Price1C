package `fun`.gladkikh.app.price1c.viewmodel

import `fun`.gladkikh.app.price1c.App
import `fun`.gladkikh.app.price1c.entity.ItemPrice
import `fun`.gladkikh.app.price1c.ui.adapter.ItemPriceAdapter
import `fun`.gladkikh.app.price1c.usecase.downLoadPrice
import `fun`.gladkikh.app.price1c.util.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import com.uber.autodispose.AutoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.swipe_list_item_price.*
import java.util.concurrent.TimeUnit

class PriseLisViewModel : BaseViewModel() {

    val ldSearchString = MutableLiveData<String>()
    val ldPriceList = MutableLiveData<List<ItemPrice>>()
    val ldSMessage = SingleLiveEvent<String>()
    val ldShowProgress = SingleLiveEvent<Boolean>()


    private val searchPublishSubject = PublishSubject.create<String?>()

    init {
        searchPublishSubject
            .debounce(1000, TimeUnit.MILLISECONDS)
            .`as`(AutoDispose.autoDisposable(this))
            .subscribe {
                ldSearchString.postValue(it)
            }
    }

    fun downloadPrice() {
        ldShowProgress.value = true
        downLoadPrice(App.appContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                ldShowProgress.value = false
            }
            .`as`(AutoDispose.autoDisposable(this))
            .subscribe({
                ldSMessage.value = "Загрузили!"
            }, {
                ldSMessage.value = "Ошибка ${it.localizedMessage}"
            })
    }


    fun changeSearchString(str: String) {
        searchPublishSubject.onNext(str)
    }

}