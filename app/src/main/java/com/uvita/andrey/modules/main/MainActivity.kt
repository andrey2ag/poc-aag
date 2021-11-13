package com.uvita.andrey.modules.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import com.uvita.andrey.BuildConfig
import com.uvita.andrey.R
import com.uvita.andrey.databinding.ActivityMainBinding
import com.uvita.andrey.modules.analytics.PonyoAnalytics
import com.uvita.andrey.modules.baseComponents.BaseActivity

const val TAG = "MainActivity"

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), MainView, MainPresenter {

    override fun getVMClass() = MainViewModel::class.java
    override fun getToolbarTitle() = R.string.app_name
    override fun layout() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialSetup()
        PonyoAnalytics.trackAppLaunch()
    }

    private fun initialSetup() {
        binding.presenter = this

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.more -> showMoreOptions()
                else -> false
            }
        }
    }

    private fun showMoreOptions(): Boolean {
        PopupMenu(this, findViewById(R.id.more)).apply {
            inflate(R.menu.more_menu)
            setForceShowIcon(true)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> shareApp()
                    R.id.exit -> showExitDialog()
                }
                true
            }
        }.show()
        return false
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            var shareMessage = getString(R.string.share_message)
            shareMessage = """
            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
            """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)))
        } catch (e: Exception) {
            // TODO handle exception
        }
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        with(AlertDialog.Builder(this@MainActivity, R.style.UvitaAlertDialog)) {
            setTitle(R.string.app_name)
            setMessage(R.string.exit_message)
            //Yes Button
            setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }

            //No Button
            setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            create().show()
        }
    }

    // Presenter implementation
    override fun onLoginClick() {
        TODO("Not yet implemented")
    }

    // End Presenter implementation
}