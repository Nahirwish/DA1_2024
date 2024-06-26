package com.example.lolapp.ui

import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore

class ChampionDetailActivity : AppCompatActivity() {
    lateinit var champion_name: TextView
    lateinit var champion_caption: TextView
    lateinit var profile_img: ImageView
    lateinit var champ_lore: TextView
    lateinit var pb: ProgressBar
    lateinit var btn_add: Button
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
        btn_add = findViewById(R.id.btnAdd)
        pb = findViewById(R.id.progressBar)

        val id = intent.getStringExtra("id")


        viewModel.champion.observe(this) {
            champion_name.text = it.champion_name
            champion_caption.text = it.champion_caption
            Glide.with(this).load(it.profile_img).into(profile_img)
            champ_lore.text = it.lore
            pb.visibility = View.INVISIBLE

            btn_add.setOnClickListener{
                viewModel.addFavorite(id!!)
            }
        }

        pb.visibility = View.VISIBLE
        viewModel.init(id!!, this)


    }
}