package com.uasmobile.ceritakamu.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Mode Gelap Aktif" else "Mode Gelap Nonaktif"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
