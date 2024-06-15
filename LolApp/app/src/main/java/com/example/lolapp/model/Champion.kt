package com.example.lolapp.model

import java.io.Serializable

data class Champion(
    var champion_name: String,
    var recomended_roles: String,
    var profile_img: String
)

data class ChampionDetail(
    var champion_name: String,
    var recomended_roles: String,
    var profile_img: String,
    var champion_caption: String
) {
    constructor():this("","","","")

}

