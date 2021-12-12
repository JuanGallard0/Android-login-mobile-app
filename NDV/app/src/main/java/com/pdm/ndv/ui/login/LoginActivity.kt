package com.pdm.ndv.ui.login

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.pdm.ndv.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navHostFragment  =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<Button>(R.id.button_sign_in).setOnClickListener {
            navController.navigate(R.id.signInFragment)
        }
        findViewById<Button>(R.id.button_home).setOnClickListener {
            navController.navigate(R.id.authenticationFragment)
        }
        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            navController.navigate(R.id.signUpFragment)
        }
    }
}