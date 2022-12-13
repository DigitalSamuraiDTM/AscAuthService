package com.digitalsamurai.asc.controller.entity.adminpanel

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.google.gson.annotations.SerializedName

data class NetworkBaseUserInfo(
    @SerializedName("username")
    var username : String,
    @SerializedName("tg")
    var tgId : String?,
    var tgTag : String?,
    var job : JobLevel
)
fun User.toBaseUserInfo() = NetworkBaseUserInfo(username, tgId, tgTag, job)