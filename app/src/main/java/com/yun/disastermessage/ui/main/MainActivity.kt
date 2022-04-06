package com.yun.disastermessage.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yun.disastermessage.R
import com.yun.disastermessage.custom.LoadingDialog
import com.yun.disastermessage.databinding.ActivityMainBinding
import com.yun.disastermessage.ui.popup.TwoButtonPopup
import com.yun.disastermessage.util.PreferenceManager
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    lateinit var dialog: LoadingDialog

    val sharedPreferences: PreferenceManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.main = mainViewModel

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        dialog = LoadingDialog(this)

        mainViewModel.isLoading.observe(this, {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        })
    }

    override fun onBackPressed() {
        showExitPopup()
    }

    private fun showExitPopup() {
        TwoButtonPopup().apply {
            showPopup(
                this@MainActivity,
                this@MainActivity.getString(R.string.notice),
                this@MainActivity.getString(R.string.exit_question)
            )
            setDialogListener(object : TwoButtonPopup.CustomDialogListener {
                override fun onResultClicked(result: Boolean) {
                    if (result) {
                        finish()
                    }
                }
            })
        }
    }

}