package com.digitalsamurai.asc.controller.entity.adminpanel

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.google.gson.annotations.SerializedName

data class NetworkBaseUserInfo(
    @SerializedName("username")
    var username : String,
    @SerializedName("tg_id")
    var tgId : String?,
    @SerializedName("tg_tag")
    var tgTag : String?,
    @SerializedName("job")
    var job : JobLevel,
    @SerializedName("team")
    val teamName : String
)
fun User.toBaseUserInfo() = NetworkBaseUserInfo(username, tgId, tgTag, job,team)