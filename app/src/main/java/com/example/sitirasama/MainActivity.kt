package com.example.sitirasama

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.button)
        val registerTextView = findViewById<TextView>(R.id.textRegister)
        val passwordToggle = findViewById<ImageButton>(R.id.imageButton)

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

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = UserRequest(email = email, password = password)
            ApiClient.apiService.loginUser(request).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Log.d("API_DEBUG", "Response dari server: ${response.body()}")  // ✅ Cek response API
                        val token = user?.accessToken ?: ""
                        val username = user?.username ?: ""
                        val status = user?.status ?: ""

                        if (token.isNotEmpty()) {
                            // Simpan token di SharedPreferences
                            sessionManager.saveSession(token, username, status)
                            Log.d("SESSION_DEBUG", "Token berhasil disimpan: $token")

                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.e("SESSION_DEBUG", "Login berhasil tetapi token kosong!")
                            Toast.makeText(this@MainActivity, "Login berhasil tetapi gagal mendapatkan token!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("API_DEBUG", "Login gagal: ${response.errorBody()?.string()}")
                        Toast.makeText(this@MainActivity, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("API_DEBUG", "Kesalahan koneksi: ${t.message}")
                    Toast.makeText(this@MainActivity, "Kesalahan koneksi ke server!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
