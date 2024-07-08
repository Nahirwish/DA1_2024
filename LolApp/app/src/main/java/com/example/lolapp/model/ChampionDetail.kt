package com.example.lolapp.model

import com.google.gson.annotations.SerializedName


data class ChampionDetail(
    @SerializedName("champion_name") var champion_name: String,
    @SerializedName("champion_caption") var champion_caption: String,
    @SerializedName("recomended_roles") var recomended_roles: String,
    @SerializedName("splashart") var profile_img: String,
    @SerializedName("lore") var lore: String,
    var isFavorite: Boolean
) {
    constructor():this("","","","", "", false)

}