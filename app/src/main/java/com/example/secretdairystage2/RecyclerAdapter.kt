package com.example.secretdairystage2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var notes: MutableList<Note>): RecyclerView.Adapter<RecyclerAdapter.NotesHolder>() {


    class NotesHolder(view:View): RecyclerView.ViewHolder(view) {
        val date = view.findViewById<TextView>(R.id.tvDateNote)
        val numberNote = view.findViewById<TextView>(R.id.tvNumberNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return NotesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val note = notes[position]

        holder.date.text = note.date
        holder.numberNote.text = note.content
    }

    fun add(note: Note) {
        notes.add(0, note)
        //notifyItemInserted(itemCount-1)
        notifyDataSetChanged()
    }
}

