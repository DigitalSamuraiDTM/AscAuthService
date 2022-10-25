package com.digitalsamurai.asc.model.database.users.tables


import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import org.ktorm.entity.Entity
import org.ktorm.schema.*

object Users : Table<User>("users"){
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val tgId = varchar("tg_id").bindTo { it.tgId }
    val tgTag = text("tg_tag").bindTo { it.tgTag }
    val canUsePAGS = boolean("can_use_apk-service").bindTo { it.canUsePAGS }
    val canUsePACS = boolean("can_use_sharing-service").bindTo { it.canUsePACS }
    val canUseDNS = boolean("can_use_deep-name-service").bindTo { it.canUseDNS }
    val canUseTS = boolean("can_use_token-service").bindTo { it.canUseTS }
    val isUseTgAlarm = boolean("is_sub_tg_alarm").bindTo { it.isUseTgAlarm }
    val isUseServiceAlarm = boolean("is_sub_service_alarm").bindTo { it.isUseServiceAlarm }
    val job = enum<JobLevel>("job").bindTo { it.job }
    val team = varchar("team").bindTo { it.team }
    val inviter = varchar("inviter").bindTo { it.inviter }
}

interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    val username : String
    val password : String
    val tgId : String
    val tgTag :String
    val canUsePAGS :Boolean
    val canUseDNS :Boolean
    val canUsePACS :Boolean
    val canUseTS :Boolean
    val isUseTgAlarm :Boolean
    val isUseServiceAlarm : Boolean
    val job : JobLevel
    val team : String
    val inviter : String
}