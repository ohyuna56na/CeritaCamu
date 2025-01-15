package com.uasmobile.ceritakamu.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ActivityLoginBinding
import com.uasmobile.ceritakamu.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IvBackLogin.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {
            // Logika login di sini
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.visibilityToggleRegister.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.visibilityToggleRegister.setImageResource(R.drawable.visibility)
            } else {
                binding.passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.visibilityToggleRegister.setImageResource(R.drawable.visibility_off)
            }
            binding.passwordInput.setSelection(binding.passwordInput.text?.length ?: 0)
        }
    }
}
