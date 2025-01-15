package com.uasmobile.ceritakamu.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.databinding.ActivityLastOnboardingBinding
import com.uasmobile.ceritakamu.ui.login.LoginActivity
import com.uasmobile.ceritakamu.ui.register.RegisterActivity

class LastOnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLastOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}