package com.example.lolapp.model


data class ChampionDetail(
    var champion_name: String,
    var recomended_roles: String,
    var profile_img: String,
    var champion_caption: String
) {
    constructor():this("","","","")

}