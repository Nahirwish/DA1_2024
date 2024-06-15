package com.example.lolapp.ui
// Pantalla Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lolapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {
    lateinit var buttonLogin : Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    val tag = "Log_Main_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin =  findViewById(R.id.btn_login)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()

        buttonLogin.setOnClickListener(){
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception){
                Log.e("Log_Main_Activity", "onActivityResult ${e.message}")
            }
        }

    }


    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {authResult ->
                val  firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                if(authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this@MainActivity, "Cuenta creada", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@MainActivity, "Cuenta existente", Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this@MainActivity, ListActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                Toast.makeText(this@MainActivity, "Login fallido", Toast.LENGTH_LONG).show()
            }

    }





}