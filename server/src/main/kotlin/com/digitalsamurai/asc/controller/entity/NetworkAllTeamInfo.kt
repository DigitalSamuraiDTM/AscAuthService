package com.digitalsamurai.asc.controller.entity

import com.digitalsamurai.asc.controller.entity.adminpanel.NetworkBaseUserInfo
import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.google.gson.annotations.SerializedName

data class NetworkAllTeamInfo (

        @SerializedName("team_name")
        val teamName : String,

        @SerializedName("interactions_type")
        val interactionsType: InteractionsType,

        @SerializedName("note")
        val note : String,

        @SerializedName("users_list")
        val usersList : List<NetworkBaseUserInfo>,

        //SMALL STAT INFO

        @SerializedName("count_apks")
        val countApk : Int,

        @SerializedName("count_apk_installs")
        val countApkInstalls : Int,

        @SerializedName("count_namings")
        val countNamings : Int,

        @SerializedName("count_sharing_requests")
        val countSharingRequests : Int,

        @SerializedName("count_sharing_cabinets")
        val countSharingCabinets : Int,

        @SerializedName("count_available_apps")
        val countAvailableApps : Int
        )