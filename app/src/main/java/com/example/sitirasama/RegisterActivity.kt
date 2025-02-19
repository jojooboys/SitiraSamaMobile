package com.example.sitirasama

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val spinnerStatus = findViewById<Spinner>(R.id.spinnerStatus)
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val loginTextView = findViewById<TextView>(R.id.textLogin)
        val passwordToggle = findViewById<ImageButton>(R.id.passwordToggle)
        val confirmPasswordToggle = findViewById<ImageButton>(R.id.confirmPasswordToggle)

        // Data untuk Spinner
        val statusOptions = arrayOf("satpam", "mahasiswa")

        // Adapter untuk Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinnerStatus.adapter = adapter

        // Variable untuk menyimpan status yang dipilih
        var selectedStatus = statusOptions[0] // Default: satpam

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStatus = statusOptions[position] // Simpan status yang dipilih
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tidak perlu aksi jika tidak ada yang dipilih
            }
        }

        // Toggle Password Visibility
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(passwordEditText, passwordToggle, isPasswordVisible)
        }

        confirmPasswordToggle.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            togglePasswordVisibility(confirmPasswordEditText, confirmPasswordToggle, isConfirmPasswordVisible)
        }

        // Register Button Click Listener
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val status = selectedStatus

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast("Username, Email, dan Password tidak boleh kosong!")
                return@setOnClickListener
            }

            if (password.length < 6) {
                showToast("Password harus minimal 6 karakter!")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showToast("Password dan Konfirmasi Password harus sama!")
                return@setOnClickListener
            }

            if (status != "satpam" && status != "mahasiswa") {
                showToast("Status harus 'satpam' atau 'mahasiswa'!")
                return@setOnClickListener
            }

            // Panggil API Register
            registerUser(username, email, password, status)
        }

        // Navigasi ke Login
        loginTextView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun registerUser(username: String, email: String, password: String, status: String) {
        val userRequest = UserRequest(
            username = username,
            email = email,
            password = password,
            status = status
        )

        ApiClient.apiService.createUser(userRequest).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    showToast("Registrasi berhasil! Akun: $username dengan role $status telah dibuat.")
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                } else {
                    showToast("Registrasi gagal: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showToast("Terjadi kesalahan: ${t.message}")
            }
        })
    }

    private fun togglePasswordVisibility(editText: EditText, button: ImageButton, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            button.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            button.setImageResource(android.R.drawable.ic_menu_view)
        }
        editText.setSelection(editText.text.length) // Posisikan kursor di akhir teks
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
