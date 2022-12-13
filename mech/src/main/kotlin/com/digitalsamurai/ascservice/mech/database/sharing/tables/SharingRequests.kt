package com.digitalsamurai.ascservice.mech.database.sharing.tables

import com.digitalsamurai.ascservice.mech.database.entity.RequestStatus
import com.digitalsamurai.ascservice.mech.database.entity.SourceRequest
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.enum
import org.ktorm.schema.varchar
import java.time.LocalDate

object SharingRequests : Table<SharingRequest>("sharing_service"){
    val owner = varchar("request_owner").bindTo { it.owner }
    val hashRequest = varchar("hash_code").bindTo { it.hashCode }.primaryKey()
    val appName = varchar("app").bindTo { it.appName }
    val appPackage = varchar("package").bindTo { it.appPackage }
    val status = enum<RequestStatus>("status").bindTo { it.status }
    val service = enum<SourceRequest>("service").bindTo { it.sourceRequest }
    val accessToken = varchar("access_token").bindTo { it.accessToken }
    val requestDate = date("request_date").bindTo { it.requestDate }
}
interface SharingRequest : Entity<SharingRequest> {
    companion object : Entity.Factory<SharingRequest>()

    val owner : String
    val hashCode :String
    val appName : String
    val appPackage :String
    val status : RequestStatus
    val sourceRequest : SourceRequest
    val accessToken : String?
    val requestDate : LocalDate
}