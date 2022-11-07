package com.digitalsamurai.ascservice.mech.database.users.tables


import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import org.ktorm.entity.Entity
import org.ktorm.schema.*

object Users : Table<User>("users"){
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val tgId = varchar("tg_id").bindTo { it.tgId }
    val tgTag = text("tg_tag").bindTo { it.tgTag }
    val osmiumAccess = boolean("can_use_apk-service").bindTo { it.canUseOsmium }
    val seleniumAccess = boolean("can_use_sharing-service").bindTo { it.canUseSelenium }
    val carboniumAccess = boolean("can_use_deep-name-service").bindTo { it.canUseCarbonium }
    val kryptonAccess = boolean("can_use_token-service").bindTo { it.canUseKrypton }
    val bohriumAccess = boolean("is_sub_service_alarm").bindTo { it.canUseBohrium }
    val isUseTgAlarm = boolean("is_sub_tg_alarm").bindTo { it.isUseTgAlarm }
    val job = enum<JobLevel>("job").bindTo { it.job }
    val team = varchar("team").bindTo { it.team }
    val inviter = varchar("inviter").bindTo { it.inviter }
}

interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    var username : String
    var password : String
    var tgId : String?
    var tgTag :String?
    var canUseOsmium :Boolean
    var canUseCarbonium :Boolean
    var canUseSelenium :Boolean
    var canUseKrypton :Boolean
    var isUseTgAlarm :Boolean
    var canUseBohrium : Boolean
    var job : JobLevel
    var team : String
    var inviter : String
}