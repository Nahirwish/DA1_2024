package com.example.lolapp.model

import java.io.Serializable

data class Champion(
    var champion_name: String,
    var recomended_roles: String,
    var profile_img: String,
    var isFavorite: Boolean
)


