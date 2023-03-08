package com.example.secretdairystage2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secretdairystage2.databinding.ActivityMainBinding
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
//import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val notes = mutableListOf(Note("today", "first"), Note("tomorrow", "second"))
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(notes)
        binding.rvNotes.adapter = adapter

        binding.btnSave.setOnClickListener {
            if (binding.etNewWriting.text.isBlank()) {
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val text = binding.etNewWriting.text.toString()
            //binding.tvDiary.text = text
            //todo guadar el texto en la lista

            val now: Instant = Clock.System.now()
            val localDateTime = LocalDateTime.ofInstant(now.toJavaInstant(), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = localDateTime.format(formatter)

            Log.d("format", formatted)
            adapter.add(Note(formatted, text))
            binding.etNewWriting.text.clear()
        }
    }
}