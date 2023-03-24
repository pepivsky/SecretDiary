package com.example.secretdairystage2

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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


const val PREF_DIARY = "PREF_DIARY"
const val KEY_DIARY_TEXT = "KEY_DIARY_TEXT"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val notes = mutableListOf<Note>()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_DIARY, Context.MODE_PRIVATE)
        //sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val saved = getFromSp()
        if (saved != "null") {
            notes.addAll(stringToList(saved))
            Log.d("oncreate", saved)
            showNotes()
        }


        binding.btnSave.setOnClickListener {
            if (binding.etNewWriting.text.isBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Empty or blank input cannot be saved",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val now: Instant = Clock.System.now()
            val localDateTime = LocalDateTime.ofInstant(now.toJavaInstant(), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = localDateTime.format(formatter)

            val text = binding.etNewWriting.text.toString()
            //val oldText = binding.tvDiary.text.trim()

            val note = Note(date, text)
            saveNote(note)
            showNotes()
            clearInput()


        }

        binding.btnUndo.setOnClickListener {
            showDialog()
        }

    }

    private fun saveNote(note: Note) {
        notes.add(note)
        saveInSp(listToString(notes))
    }


    private fun showNotes() {
        val notesString = listToString(notes)
        //println(notesString)
        binding.tvDiary.text = notesString
    }

    private fun clearInput() {
        binding.etNewWriting.text.clear()
    }

    private fun undo() {
        notes.removeLast()
        saveInSp(listToString(notes))
    }

    private fun showDialog() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                //Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                if (notes.isEmpty()) {
                    return@setPositiveButton
                }
                undo()
                showNotes()

            }
            .setNegativeButton("No", null)
            .show()
    }


    private fun listToString(list: List<Note>): String {
        return list.reversed().joinToString("\n\n")
    }


    private fun stringToList(notes: String): List<Note> {
        val strings = notes.split("\n\n")
        val notesList = mutableListOf<Note>()
        strings.forEach {
            val (date, content) = it.split("\n")
            val note = Note(date, content)
            notesList.add(note)
        }
        return notesList.reversed()
    }

    private fun saveInSp(toSave: String) {
        editor.putString(KEY_DIARY_TEXT, toSave).apply()
    }

    private fun getFromSp(): String {
        val notes = sharedPreferences.getString(KEY_DIARY_TEXT, "null")
        return notes!!
    }

}
