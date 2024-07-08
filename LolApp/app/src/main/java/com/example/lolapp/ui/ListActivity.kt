package com.example.lolapp.ui

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
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
import android.view.Menu
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.example.lolapp.model.Champion

class ListActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private lateinit var rvChampions: RecyclerView
    private lateinit var adapter: ChampionsAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private var isShowingFavorites = false

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

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.champions.observe(this) {champions ->
            Log.d("Log_Main_Activity", "actualización de champions: ${champions.size}")
            champions.forEach{champion ->
                if (champion.champion_name == "Ashe") {
                    champion.isFavorite = true
                }
                Log.d("Log_Main_Activity", "champion: ${champion.champion_name}, isFavorite: ${champion.isFavorite}")
            }
            if (isShowingFavorites) {
                val favoriteChampions = champions.filter { champion -> champion.isFavorite }
                Log.d("Log_Main_Activity", "Mostrando solo favoritos: ${favoriteChampions.size}")
                /*favoriteChampions.forEach {
                    Log.d(
                        "Log_Main_Activity", "Favorite Champion: ${it.champion_name}")

                }*/
                adapter.update(favoriteChampions as MutableList<Champion>)
            } else {
                Log.d("Log_Main_Activity", "Mostrando todos los champs: ${champions.size}")
                adapter.update(champions as MutableList<Champion>)
            }
        }
        Log.d("Log_Main_Activity", "Llamando al init del view model")
        viewModel.init(this)

        findViewById<TextView>(R.id.btn_all).setOnClickListener {
            isShowingFavorites = false
            viewModel.champions.value?.let { champions ->
                Log.d("Log_Main_Activity", "click btn_all, campeones: ${champions.size} ")
                adapter.update(champions as MutableList<Champion>)
            }
        }

        findViewById<TextView>(R.id.btn_fav).setOnClickListener {
            isShowingFavorites = true
            viewModel.champions.value?.let { champions ->
                val favoriteChampions = champions.filter { champion -> champion.isFavorite }
                Log.d("Log_Main_Activity", "click btn_favorite, favoritos: ${favoriteChampions.size} ")
                adapter.update(favoriteChampions as MutableList<Champion>)
            }
        }

    }
    // Busqueda
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_bar)?.actionView as SearchView
        val component = ComponentName(this, ListActivity::class.java)
        val serchableInfo = searchManager.getSearchableInfo(component)
        searchView.setSearchableInfo(serchableInfo)
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        if (Intent.ACTION_SEARCH == intent.action){
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("Log_Main_Activity","busqueda: ${query}")
        }
    }

    override fun onResume() {
        super.onResume()
        checkUser()
    }
        fun checkUser() {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }


}