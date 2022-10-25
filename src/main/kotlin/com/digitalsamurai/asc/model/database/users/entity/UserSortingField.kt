package com.digitalsamurai.asc.model.database.users.entity

import com.digitalsamurai.asc.model.database.users.tables.Users
import org.ktorm.schema.Column

enum class UserSortingField(val column : Column<*>) {
    NAME(Users.username), TG_TAG(Users.tgTag),TEAM(Users.team),JOB(Users.job)
}