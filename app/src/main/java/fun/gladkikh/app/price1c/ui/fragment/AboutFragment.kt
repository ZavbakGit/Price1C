package `fun`.gladkikh.app.price1c.ui.fragment


import `fun`.gladkikh.app.price1c.R
import `fun`.gladkikh.app.price1c.viewmodel.AboutViewModel
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

class AboutFragment : BaseFragment() {
    private lateinit var viewModel: AboutViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get(AboutViewModel::class.java)
    }

    override fun getLayout() = R.layout.about_fragment
}