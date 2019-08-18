package `fun`.gladkikh.app.price1c.ui.activity

import androidx.navigation.NavController

interface HostActivity{
    fun showMessage(text: CharSequence)
    fun isShowProgress():Boolean
    fun showProgress()
    fun hideProgress()
    fun getHostNavController(): NavController
}