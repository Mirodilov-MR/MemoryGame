package com.example.memorygame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val totalQuestions = intent.getIntExtra(Constants.getInt, 0)
        val text: TextView = findViewById(R.id.scoreTv)
        text.setText(totalQuestions.toString())
        congratulationsTv.text = "Congratulations, !"
        scoreTv.text = "Your find is $totalQuestions of 6"
        btnRestart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}