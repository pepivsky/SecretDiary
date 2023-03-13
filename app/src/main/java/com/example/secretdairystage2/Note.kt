package com.example.secretdairystage2

data class Note(val date: String, val content: String) {
    override fun toString(): String {
        return this.date + "\n" + this.content
    }
}