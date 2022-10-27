package com.digitalsamurai.asc.model.usermanager.entity

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("username")
    val username : String,
    @SerializedName("tg_tag")
    val tgTag : String?,
    @SerializedName("tg_id")
    val tgId : String?,
    @SerializedName("team")
    val team : String,
    @SerializedName("job")
    val jobLevel: JobLevel,
    @SerializedName("inviter")
    val inviter : String,
    @SerializedName("selenium_access")
    val seleniumAccess : Boolean,
    @SerializedName("carbonium_access")
    val carboniumAccess : Boolean,
    @SerializedName("osmium_access")
    val osmiumAccess : Boolean,
    @SerializedName("bohrium_access")
    val bohrmiumAccess : Boolean,
    @SerializedName("krypton_access")
    val kryptonAccess : Boolean,
        )

fun User.toUserInfo() = UserInfo(username,tgTag,tgId,team,job,inviter,canUseSelenium,canUseCarbonium,canUseOsmium,canUseBohrium,canUseKrypton)