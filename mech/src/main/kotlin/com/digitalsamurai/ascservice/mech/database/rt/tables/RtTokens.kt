package com.digitalsamurai.ascservice.mech.database.rt.tables

import com.digitalsamurai.ascservice.mech.database.rt.entity.RtTokenStatus
import com.digitalsamurai.ascservice.mech.database.teams.tables.Team
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.enum
import org.ktorm.schema.varchar
import java.time.LocalDateTime

object RtTokens : Table<RtToken>("rt_tokens") {
    val user = varchar("user").bindTo { it.user }
    val token = varchar("rt_token").bindTo { it.token }
    val userAgent = varchar("user_agent").bindTo { it.userAgent }
    val creatingDate = datetime("creating_date").bindTo { it.creatingDate }
    val status = enum<RtTokenStatus>("status").bindTo { it.tokenStatus }
    val lastActive = datetime("user").bindTo { it.lastActiveTime }

}

interface RtToken : Entity<RtToken> {
    companion object : Entity.Factory<RtToken>()

    val user : String
    val token : String
    val userAgent : String
    val creatingDate : LocalDateTime
    val tokenStatus : RtTokenStatus
    val lastActiveTime : LocalDateTime
}