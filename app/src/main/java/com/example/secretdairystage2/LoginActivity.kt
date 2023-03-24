package com.example.secretdairystage2



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.secretdairystage2.databinding.ActivityLoginBinding

const val CORRECT_PIN = "1234"
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.etPin.text.toString() == CORRECT_PIN) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                binding.etPin.error = "Wrong PIN!"
            }

        }
    }
}