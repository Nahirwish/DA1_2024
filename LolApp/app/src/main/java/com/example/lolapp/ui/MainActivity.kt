package com.example.lolapp.ui
// Pantalla Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.lolapp.R

class MainActivity : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var buttomLogin : Button

    val tag = "Log_Main_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModel()
    }

    fun bindViewModel(){
        Log.d(tag, "bindViewModel ejecutado")
        inputEmail = findViewById(R.id.editEmailAddress)
        inputPassword = findViewById(R.id.editPassword)
        buttomLogin = findViewById(R.id.btn_login)

        buttomLogin.isEnabled = true

        buttomLogin.setOnClickListener(){
            login()
        }

    }

    fun login(){
        Log.d(tag, "Se ejecuto el login")
        var intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)

    }





}