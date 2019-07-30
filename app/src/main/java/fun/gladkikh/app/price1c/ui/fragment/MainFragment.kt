package `fun`.gladkikh.app.price1c.ui.fragment

import `fun`.gladkikh.app.price1c.App
import `fun`.gladkikh.app.price1c.R
import `fun`.gladkikh.app.price1c.ui.activity.MainActivity
import `fun`.gladkikh.app.price1c.ui.adapter.ItemPriceAdapter
import `fun`.gladkikh.app.price1c.usecase.downLoadPrice
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uber.autodispose.AutoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.swipe_list_item_price.*


class MainFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvItemPrice.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ItemPriceAdapter()
        }


    }

    override fun initSubscription() {
        super.initSubscription()
        App.database.getItemPriceDao().getAllWithNameLike("","руб.").observe(this, Observer {
            (activity as MainActivity).showMessage("Прочитали!")
            (rvItemPrice.adapter as ItemPriceAdapter).updateData(it)
        })

        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(ch: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changeText(ch.toString())
            }
        })

        swipeContainer.isRefreshing = false
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            (activity as MainActivity).showProgress()
            downLoadPrice(activity as MainActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    (activity as MainActivity).hideProgress()
                }
                .`as`(AutoDispose.autoDisposable(scopeProvider))
                .subscribe({

                }, {

                })

        }

    }

    fun changeText(str:String){
        App.database.getItemPriceDao().getAllWithNameLike("%$str%","руб.")
            .observe(this, Observer {
                (rvItemPrice.adapter as ItemPriceAdapter).updateData(it)
            })
    }

    override fun getLayout(): Int = R.layout.main_fragment
}