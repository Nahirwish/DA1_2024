package com.example.lolapp.data.dbLocal

import com.example.lolapp.model.Champion

fun ChampionLocal.toChampion() = Champion(
    champion_name ?: "",
    recomended_roles ?: "",
    profile_img ?: "",
    isFavorite ?: false

)

fun List<ChampionLocal>.toChampionList() = map(ChampionLocal::toChampion)


fun Champion.toChampionLocal() = ChampionLocal(
    champion_name ?:"",
    recomended_roles ?: "",
    profile_img ?:"",
    isFavorite ?: false
)

fun List<Champion>.toChampionLocalList() = map(Champion::toChampionLocal)