package `fun`.gladkikh.app.price1c.ui.fragment

import `fun`.gladkikh.app.price1c.App
import `fun`.gladkikh.app.price1c.R
import `fun`.gladkikh.app.price1c.ui.activity.MainActivity
import `fun`.gladkikh.app.price1c.ui.adapter.ItemPriceAdapter
import `fun`.gladkikh.app.price1c.viewmodel.PriseLisViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.swipe_list_item_price.*


class MainFragment : BaseFragment() {

    private lateinit var viewModel: PriseLisViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvItemPrice.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ItemPriceAdapter()
        }

        edSearch.setOnClickListener {
            (activity as MainActivity).getHostNavController().navigate(R.id.aboutFragment,null,null)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get(PriseLisViewModel::class.java)
    }


    override fun initSubscription() {
        super.initSubscription()

        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(ch: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.changeSearchString(ch.toString())

            }
        })

        viewModel.ldSearchString.observe(viewLifecycleOwner, Observer { searchString ->
            App.database.getItemPriceDao().getAllWithNameLike("%$searchString%", "руб.")
                .observe(this, Observer {
                    (rvItemPrice.adapter as ItemPriceAdapter).updateData(it)
                })
        })


        viewModel.ldShowProgress.observe(viewLifecycleOwner, Observer { show: Boolean ->
            if (show) {
                (activity as MainActivity).showProgress()
            } else {
                (activity as MainActivity).hideProgress()
            }
        })


        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            viewModel.downloadPrice()
        }


    }


    override fun getLayout(): Int = R.layout.main_fragment
}