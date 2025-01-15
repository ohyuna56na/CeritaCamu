package com.uasmobile.ceritakamu.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layouts = listOf(
            R.layout.onboarding_1,
            R.layout.onboarding_2,
            R.layout.onboarding_3
        )

        onboardingAdapter = OnboardingAdapter(layouts)
        binding.viewPager.adapter = onboardingAdapter

        updateButtonVisibility()

        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem + 1 < layouts.size) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                navigateToMain()
            }
            updateButtonVisibility()
        }

        binding.btnBack.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem > 0) {
                binding.viewPager.currentItem = currentItem - 1
            }
            updateButtonVisibility()
        }
    }

    private fun updateButtonVisibility() {
        val currentItem = binding.viewPager.currentItem
        val layoutsSize = onboardingAdapter.itemCount

        binding.btnBack.isEnabled = currentItem > 0
        binding.btnNext.text = if (currentItem == layoutsSize - 1) "Finish" else "Next"
    }

    private fun navigateToMain() {
        startActivity(Intent(this, LastOnboardingActivity::class.java))
        finish()
    }
}
