package com.uasmobile.ceritakamu.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.MainActivity
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.SessionManager
import com.uasmobile.ceritakamu.ui.onboarding.LastOnboardingActivity
import com.uasmobile.ceritakamu.ui.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sessionManager = SessionManager(this)
        val targetActivity = when {
            !sessionManager.isOnboardingShown() -> {
                sessionManager.setOnboardingShown()
                OnboardingActivity::class.java
            }
            sessionManager.isLoggedIn() -> {
                MainActivity::class.java
            }
            else -> {
                LastOnboardingActivity::class.java
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            finish()
        }, 2000)
    }
}
