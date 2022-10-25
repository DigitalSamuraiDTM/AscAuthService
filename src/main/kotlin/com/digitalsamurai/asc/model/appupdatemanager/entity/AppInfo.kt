package com.digitalsamurai.asc.model.appupdatemanager.entity

import java.io.File

data class AppInfo(
    val apk : File,
    //with .apk
    val apkName : String,
    val version : AppVersion,
    val patchNote  :String

)
