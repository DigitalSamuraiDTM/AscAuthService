package com.digitalsamurai.ascservice.mech.database.apks.tables

import com.digitalsamurai.ascservice.mech.database.entity.RequestStatus
import com.digitalsamurai.ascservice.mech.database.entity.SourceRequest
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDate

object ApkRequests : Table<ApkRequest>("apk_service"){
    val owner = varchar("request_owner").bindTo { it.owner }
    val hashRequest = varchar("hash_code").bindTo { it.hashCode }
    val url = varchar("url").bindTo { it.url }
    val apkPath = varchar("apk_path").bindTo { it.apkPath }
    val requestStatus = enum<RequestStatus>("request_status").bindTo { it.requestStatus }
    val requestDate = date("request_date").bindTo { it.requestDate }
    val service = enum<SourceRequest>("service").bindTo { it.source }
    val accessToken = varchar("access_token").bindTo { it.accessToken }
    val isCheckUrl = boolean("is_url_check").bindTo { it.isCheckUrl }
    val apkName = varchar("apk_name").bindTo { it.apkName }
    val appName = varchar("app_label").bindTo { it.appName }
    val iconUrl = varchar("icon").bindTo { it.iconUrl }
}
interface ApkRequest : Entity<ApkRequest> {
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