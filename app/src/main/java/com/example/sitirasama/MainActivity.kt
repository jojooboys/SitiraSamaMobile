package com.example.sitirasama

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isPasswordVisible = false  // Untuk menyimpan status password terlihat atau tidak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.button)
        val passwordToggle = findViewById<ImageButton>(R.id.imageButton)
        val registerTextView = findViewById<TextView>(R.id.textRegister)

        // Fungsi Toggle Password Visibility
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(android.R.drawable.ic_menu_close_clear_cancel) // Ubah ikon
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(android.R.drawable.ic_menu_view) // Kembalikan ikon
            }
            passwordEditText.setSelection(passwordEditText.text.length) // Posisikan kursor di akhir teks
        }

        // Fungsi Tombol Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login berhasil dengan email: $email", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
        }

        // Navigasi ke RegisterActivity saat TextView "Register here" ditekan
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}