package com.example.lolapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.lolapp.R
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.widget.TextView
import com.example.lolapp.model.Champion

class ListActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private lateinit var rvChampions: RecyclerView
    private lateinit var adapter: ChampionsAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private var isShowingFavorites = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvChampions = findViewById(R.id.rvList)
        rvChampions.layoutManager = LinearLayoutManager(this)
        adapter = ChampionsAdapter()
        rvChampions.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        viewModel = ViewModelProvider(this)[ListViewModel :: class.java]
        viewModel.champions.observe(this){
            if(isShowingFavorites){
                adapter.update(it.filter { champion -> champion.isFavorite}as ArrayList<Champion>)
            }
            else{
                adapter.update(it)
            }

        }
        Log.d("Log_Main_Activity", "Llamando al init del view model")
        viewModel.init(this)

        findViewById<TextView>(R.id.btn_all).setOnClickListener{
            isShowingFavorites = false
            viewModel.champions.value?.let{champions -> adapter.update(champions) as MutableList<Champion> }
        }

        findViewById<TextView>(R.id.btn_fav).setOnClickListener{
            isShowingFavorites = true
            viewModel.champions.value?.let { champions -> adapter.update(champions.filter { champion -> champion.isFavorite} as MutableList<Champion>)  }
        }
    }


    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}