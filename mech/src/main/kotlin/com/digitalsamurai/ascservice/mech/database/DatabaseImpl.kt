package com.digitalsamurai.ascservice.mech.database

import com.digitalsamurai.ascservice.mech.database.access.AppAccessDao
import com.digitalsamurai.ascservice.mech.database.access.tables.AppAccess
import com.digitalsamurai.ascservice.mech.database.rt.RtDao
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.users.UserDao

class DatabaseImpl(
    private val userDao : UserDao,
    private val teamDao: TeamDao,
    private val rtDao : RtDao,
    private val appAccessDao : AppAccessDao
) :
    UserDao by userDao,
    TeamDao by teamDao,
    RtDao by rtDao,
    AppAccessDao by appAccessDao{
    init {

    }

}