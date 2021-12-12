package com.pdm.ndv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.pdm.ndv.ui.login.LoginActivity
import com.pdm.ndv.viewModels.LoginViewModel
import com.pdm.ndv.viewModels.LoginViewModelFactory
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val loginApp by lazy {
        application as NDVApplication
    }

    private val loginViewModelFactory: LoginViewModelFactory by lazy {
        LoginViewModelFactory(loginApp.userRepository)
    }

    private val loginViewModel: LoginViewModel by viewModels{
        loginViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.log_out -> {
                    // Handle logout icon press
                    run {
                        lifecycleScope.launch { loginViewModel.onLogOut() }

                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }

        var switch = false
        lifecycleScope.launch {
            switch = loginViewModel.validateSignInToken()
        }.invokeOnCompletion {
            if (!switch) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}