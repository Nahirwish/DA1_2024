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


class ChampionDetailActivity : AppCompatActivity() {
    lateinit var champion_name: TextView
    lateinit var champion_caption: TextView
    lateinit var profile_img: ImageView
    lateinit var pb: ProgressBar
    lateinit var viewModel: ChampionViewModel
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
        pb = findViewById(R.id.progressBar)

        val id = intent.getStringExtra("id")


        viewModel.champion.observe(this) {
            champion_name.text = it.champion_name
            champion_caption.text = it.champion_caption
            Glide.with(this).load(it.profile_img).into(profile_img)
            pb.visibility = View.INVISIBLE
        }

        pb.visibility = View.VISIBLE
        viewModel.init(id!!, this)


    }
}