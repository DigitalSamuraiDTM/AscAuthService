package com.digitalsamurai.ascservice.mech.database.namings.tables

import com.digitalsamurai.ascservice.mech.database.entity.SourceRequest
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDate

object Namings : Table<Naming>("deepname_service"){
    val user = varchar("request_owner").bindTo { it.user }
    val hashCode = varchar("hash_code").bindTo { it.hashCode }
    val deeplink = varchar("deeplink").bindTo { it.deeplink }
    val naming = varchar("naming").bindTo { it.naming }
    val url = varchar("url").bindTo { it.url }
    val app = varchar("app").bindTo { it.packageApp }
    val geo = varchar("geo").bindTo { it.geo }
    val source = enum<SourceRequest>("service").bindTo { it.source }
    val accessToken = varchar("access_token").bindTo { it.accessToken }
    val date = date("request_date").bindTo { it.date }
    val adminStatus = boolean("admin_status").bindTo { it.adminStatus }
}
interface Naming : Entity<Naming> {

    companion object : Entity.Factory<Naming>()

    var user : String
    var hashCode : String
    var deeplink : String
    var naming : String
    var url : String
    var packageApp : String
    var geo : String
    var source : SourceRequest
    var accessToken : String?
    var date : LocalDate
    var adminStatus : Boolean
}