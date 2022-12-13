package com.digitalsamurai.asc.controller.entity

import com.digitalsamurai.asc.model.usermanager.entity.UserInfo
import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.google.gson.annotations.SerializedName

data class NetworkAllTeamInfo (

        @SerializedName("team_name")
        val teamName : String,

        @SerializedName("interactions_type")
        val interactionsType: InteractionsType,

        @SerializedName("note")
        val note : String,

        @SerializedName("users")
        val usersList : List<UserInfo>,

        @SerializedName("count_apks")
        val countApk : Int,

        @SerializedName("count_namings")
        val countNamings : Int
        )