package com.uasmobile.ceritakamu.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ActivityRegisterBinding
import com.uasmobile.ceritakamu.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IvBackLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            // Logika registrasi di sini
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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
