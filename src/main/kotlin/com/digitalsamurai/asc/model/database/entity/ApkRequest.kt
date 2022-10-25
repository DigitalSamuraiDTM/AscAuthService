package com.digitalsamurai.apkgenerator.model.repositories.db.entity

import com.digitalsamurai.apkgenerator.model.entity.RequestStatus
import com.digitalsamurai.apkgenerator.model.entity.SourceRequest
import org.ktorm.entity.Entity
import java.time.LocalDate


//Запись в базе данных
interface ApkRequest : Entity<ApkRequest>{
    companion object : Entity.Factory<ApkRequest>()

    val owner : String
    val hashCode : String
    val url : String
    val apkPath : String?
    val requestStatus: RequestStatus
    val requestDate : LocalDate
    val source : SourceRequest
    val accessToken : String?
    val isCheckUrl : Boolean
    val apkName : String?
    val appName : String
    val iconUrl : String
}
