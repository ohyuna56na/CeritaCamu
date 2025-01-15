package com.uasmobile.ceritakamu.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.MainActivity
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.SessionManager
import com.uasmobile.ceritakamu.ui.onboarding.OnboardingActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sessionManager = SessionManager(this)
        val targetActivity = if (sessionManager.isOnboardingShown()) {
            OnboardingActivity::class.java
        } else {
            OnboardingActivity::class.java
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            finish()
        }, 2000)
    }
}