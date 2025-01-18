package com.uasmobile.ceritakamu.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.SessionManager
import com.uasmobile.ceritakamu.ui.editprofile.EditProfileActivity
import com.uasmobile.ceritakamu.ui.onboarding.LastOnboardingActivity

class NotificationsFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivProfileImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        sessionManager = SessionManager(requireContext())

        tvName = view.findViewById(R.id.tvName)
        tvEmail = view.findViewById(R.id.tvEmail)
        ivProfileImage = view.findViewById(R.id.ivProfileImage)

        updateUserUI()

        view.findViewById<LinearLayout>(R.id.llEditProfile).setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }


        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            sessionManager.clearSession()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), LastOnboardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        updateUserUI()
    }

    private fun updateUserUI() {
        val user = sessionManager.getUser()
        if (user != null) {
            tvName.text = user.name
            tvEmail.text = user.email

            if (!user.profileImage.isNullOrEmpty()) {
                Glide.with(this)
                    .load(user.profileImage)
                    .placeholder(R.drawable.person)
                    .error(R.drawable.error_image)
                    .into(ivProfileImage)
            } else {
                ivProfileImage.setImageResource(R.drawable.person)
            }
        } else {
            tvName.text = "Guest"
            tvEmail.text = "guest@example.com"
            ivProfileImage.setImageResource(R.drawable.person)
        }
    }
}
