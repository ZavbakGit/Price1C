package `fun`.gladkikh.app.price1c.ui.activity

import `fun`.gladkikh.app.price1c.R
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.progress_overlay.*


class MainActivity : BaseActivity(), HostActivity {
    private lateinit var navController: NavController

    override fun getLayout() = R.layout.activity_main

    override fun onCreateInit() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun getHostNavController() = navController

    override fun isShowProgress() = (progressView.visibility == View.VISIBLE)

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun showMessage(text: CharSequence) {
        Snackbar.make(root, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}
