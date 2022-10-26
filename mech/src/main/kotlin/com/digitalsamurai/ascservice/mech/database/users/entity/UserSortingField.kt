package com.digitalsamurai.ascservice.mech.database.users.entity

import com.digitalsamurai.ascservice.mech.database.users.tables.Users
import org.ktorm.schema.Column

enum class UserSortingField(val column : Column<*>) {
    NAME(Users.username), TG_TAG(Users.tgTag),TEAM(Users.team),JOB(Users.job)
}