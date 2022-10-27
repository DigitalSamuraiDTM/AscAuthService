package com.digitalsamurai.asc.model.usermanager.entity

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.google.gson.annotations.SerializedName

data class BaseDataUser(
    @SerializedName("username")
    val username : String,
    @SerializedName("tg_tag")
    val tgTag : String?,
    @SerializedName("tg_id")
    val tgId : String?,
    @SerializedName("team")
    val team : String,
    @SerializedName("job")
    val jobLevel: JobLevel
)
