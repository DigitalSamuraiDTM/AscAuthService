package com.digitalsamurai.asc.model.database

import com.digitalsamurai.asc.model.database.teams.TeamDao
import com.digitalsamurai.asc.model.database.users.UserDao
import com.digitalsamurai.asc.model.database.users.UserDaoImpl
import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import com.digitalsamurai.asc.model.database.users.tables.User
import org.ktorm.database.Database

class DatabaseImpl(
    private val userDao : UserDao,
    private val teamDao: TeamDao) :
    UserDao by userDao,
    TeamDao by teamDao{
    init {

    }

}