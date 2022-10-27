package com.digitalsamurai.ascservice.mech.database.users.entity

import com.digitalsamurai.ascservice.mech.database.users.tables.Users
import org.ktorm.schema.Column

enum class UserSortingField(val code : Int,val column : Column<*>) {
    NAME(0,Users.username), TG_TAG(1,Users.tgTag),TEAM(2,Users.team),JOB(3,Users.job);
    companion object{
        fun fromInt(num : Int ) : UserSortingField{
            return when(num){
                0->{NAME}
                1->{TG_TAG}
                2->{TEAM}
                3->{JOB}
                else->{NAME}
            }
        }
    }
}