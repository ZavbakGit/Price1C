package `fun`.gladkikh.app.price1c.ui.fragment

import `fun`.gladkikh.app.price1c.ui.activity.HostActivity
import `fun`.gladkikh.app.price1c.ui.activity.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open abstract class BaseFragment : Fragment() {
    lateinit var navController: NavController


    lateinit var hostActivity: HostActivity

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hostActivity = activity as MainActivity
        navController = (activity as MainActivity).getHostNavController()
    }

    override fun onResume() {
        super.onResume()
        initSubscription()
    }

    abstract fun getLayout(): Int

    protected open fun initSubscription(){}
}