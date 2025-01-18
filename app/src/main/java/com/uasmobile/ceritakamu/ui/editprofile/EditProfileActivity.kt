package com.uasmobile.ceritakamu.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.SessionManager
import com.uasmobile.ceritakamu.userdb.User
import com.uasmobile.ceritakamu.userdb.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var sessionManager: SessionManager
    private var profileImagePath: String? = null
    private var userId: Int = 1 // ID default pengguna, sesuaikan dengan aplikasi Anda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Log.d("EditProfileActivity", "onCreate: Activity created")

        ivProfileImage = findViewById(R.id.ivEditProfileImage)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)

        sessionManager = SessionManager(this)
        val userDao = UserDatabase.getInstance(this).userDao()

        // Mendapatkan data pengguna dari database
        userDao.getUserById(userId).observe(this) { user ->
            user?.let {
                Log.d("EditProfileActivity", "onCreate: User data fetched, populating fields")
                etName.setText(it.name)
                etEmail.setText(it.email)
                profileImagePath = it.profileImage
                Glide.with(this).load(it.profileImage).into(ivProfileImage)
            }
        }

        ivProfileImage.setOnClickListener {
            Log.d("EditProfileActivity", "onCreate: Profile image clicked, opening gallery")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            Log.d("EditProfileActivity", "onCreate: Save button clicked")
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Log.d("EditProfileActivity", "onCreate: Name or email is empty")
                Toast.makeText(this, "Nama dan email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedUser = User(
                id = userId,
                name = name,
                email = email,
                password = "",
                profileImage = profileImagePath
            )

            Log.d("EditProfileActivity", "onCreate: Updating user data in database")
            lifecycleScope.launch(Dispatchers.IO) {
                userDao.updateUser(updatedUser)
                sessionManager.saveUser(updatedUser) // Perbarui juga session pengguna
                runOnUiThread {
                    Log.d("EditProfileActivity", "onCreate: User data saved successfully")
                    Toast.makeText(this@EditProfileActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                Log.d("EditProfileActivity", "pickImageLauncher: Image picked, uri: $uri")
                profileImagePath = uri.toString()
                Glide.with(this).load(uri).into(ivProfileImage)
            } else {
                Log.d("EditProfileActivity", "pickImageLauncher: Image selection failed")
            }
        }
}
