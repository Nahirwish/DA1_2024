package com.example.lolapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lolapp.R
import android.view.View
import com.bumptech.glide.Glide
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class ChampionDetailActivity : AppCompatActivity() {
    lateinit var champion_name: TextView
    lateinit var champion_caption: TextView
    lateinit var profile_img: ImageView
    lateinit var champ_lore: TextView
    lateinit var pb: ProgressBar
    lateinit var ic_rol: ImageView
    private var isFavorite = false
    lateinit var btn_add: FloatingActionButton
    lateinit var viewModel: ChampionViewModel
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_champion_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ChampionViewModel::class.java]
        champion_name = findViewById(R.id.txtChampionName)
        champion_caption= findViewById(R.id.txtChampionCaption)
        profile_img = findViewById(R.id.imgSplashArt)
        champ_lore = findViewById(R.id.txtLore)
        ic_rol = findViewById(R.id.img_Rol)
        btn_add = findViewById(R.id.btnAdd)
        pb = findViewById(R.id.progressBar)

        val id = intent.getStringExtra("id")
        Log.d("Log_Main_Activity", "DetailActivity, id obtenido ${id}")


        if(id != null) {
            viewModel.champion.observe(this) {champion->
                champion_name.text = champion.champion_name
                champion_caption.text = champion.champion_caption.uppercase()
                val img_res = when(champion.recomended_roles){
                    "Luchador" -> R.drawable.ic_luchador
                    "Asesino" -> R.drawable.ic_asesino
                    "Tanque" -> R.drawable.ic_tanque
                    "Tirador" -> R.drawable.icono_tirador
                    "Mago" -> R.drawable.ic_mago
                    "Soporte" -> R.drawable.ic_soporte
                    else -> R.drawable.icons_busqueda
                }
                Glide.with(this).load(img_res).into(ic_rol)
                Glide.with(this).load(champion.profile_img).into(profile_img)
                champ_lore.text = champion.lore
                isFavorite = champion.isFavorite
                pb.visibility = View.INVISIBLE

                btn_add.setOnClickListener {
                    if(champion.isFavorite){
                        viewModel.removeFavorite(id)
                        champion.isFavorite = false
                        Log.d("Log_Main_Activity", "onClick remove, id: ${id} ")

                    }
                    else {
                        viewModel.addFavorite(id!!)
                        champion.isFavorite = true
                        Log.d("Log_Main_Activity", "onClick btn_add, id: ${id} ")
                    }
                }
            }

            pb.visibility = View.VISIBLE
            viewModel.init(id!!, this)
        }
        else{
            Log.d("Log_Main_Activity", "No hay id valido")
        }

    }
}