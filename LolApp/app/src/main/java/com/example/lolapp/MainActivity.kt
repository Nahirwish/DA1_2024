package com.example.lolapp
// Pantalla Login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var buttomLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun bindViewModel(){
        inputEmail = findViewById(R.id.editEmailAddress)
        inputEmail = findViewById(R.id.editPassword)
        buttomLogin = findViewById(R.id.btn_login)
    }



}