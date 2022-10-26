package com.digitalsamurai.ascservice.mech.database

import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.users.UserDao

class DatabaseImpl(
    private val userDao : UserDao,
    private val teamDao: TeamDao
) :
    UserDao by userDao,
    TeamDao by teamDao{
    init {

    }

}