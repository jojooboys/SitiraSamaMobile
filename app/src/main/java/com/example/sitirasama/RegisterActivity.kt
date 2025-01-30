package com.example.sitirasama

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi Views
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val statusEditText = findViewById<EditText>(R.id.editTextStatus)
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val loginTextView = findViewById<TextView>(R.id.textLogin)
        val passwordToggle = findViewById<ImageButton>(R.id.passwordToggle)
        val confirmPasswordToggle = findViewById<ImageButton>(R.id.confirmPasswordToggle)

        // Toggle Password Visibility
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(android.R.drawable.ic_menu_close_clear_cancel) // Ubah ikon menjadi "X"
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(android.R.drawable.ic_menu_view) // Kembalikan ikon "eye"
            }
            passwordEditText.setSelection(passwordEditText.text.length) // Posisikan kursor di akhir teks
        }

        confirmPasswordToggle.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirmPasswordToggle.setImageResource(android.R.drawable.ic_menu_close_clear_cancel) // Ubah ikon menjadi "X"
            } else {
                confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPasswordToggle.setImageResource(android.R.drawable.ic_menu_view) // Kembalikan ikon "eye"
            }
            confirmPasswordEditText.setSelection(confirmPasswordEditText.text.length) // Posisikan kursor di akhir teks
        }

        // Register Button Click Listener
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val status = statusEditText.text.toString().lowercase()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username, Email, dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Password dan Confirm Password harus sama!", Toast.LENGTH_SHORT).show()
            } else if (status != "satpam" && status != "mahasiswa") {
                Toast.makeText(this, "Status harus 'satpam' atau 'mahasiswa'!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigasi ke Login
        loginTextView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
