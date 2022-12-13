package com.digitalsamurai.ascservice.mech.database.inviters

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Inviters :Table<Inviter>("inviters") {
    val username = varchar("username").bindTo { it.username }
    val inviterName = varchar("inviter_name").bindTo { it.inviterName }
}
interface Inviter : Entity<Inviter>{
    val username : String
    val inviterName : String
}