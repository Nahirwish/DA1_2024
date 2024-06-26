package com.example.lolapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Champion(
    @SerializedName("champion_name") var champion_name: String,
    @SerializedName("recomended_roles") var recomended_roles: String,
    @SerializedName("profile_img") var profile_img: String,
    var isFavorite: Boolean
)


