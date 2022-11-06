package com.digitalsamurai.asc.model.usermanager

import com.digitalsamurai.asc.controller.entity.NetworkRegistrationUserInfo
import com.digitalsamurai.asc.controller.entity.NetworkResponseRegistrationUser
import com.digitalsamurai.asc.model.usermanager.entity.BaseDataUser
import com.digitalsamurai.asc.model.usermanager.entity.UserInfo
import com.digitalsamurai.asc.model.usermanager.entity.toUserInfo
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.users.UserDao
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField

class UserModel(private val teamDao: TeamDao,private val userDao : UserDao) {

    suspend fun getTeamsList() : List<String>{
        return teamDao.getTeamsList()
    }

    suspend fun deleteUser(username : String) : Boolean{
        return userDao.deleteUser(username)
    }

    suspend fun getAuthUsersList(jobLevel: JobLevel?) : List<BaseDataUser>{
        return userDao.getAuthUsers(jobLevel).map {
            BaseDataUser(
                username = it.username,
                tgTag = it.tgTag,
                tgId = it.tgId,
                team = it.team,
                jobLevel = it.job)
        }
    }
    suspend fun getAuthUsersList(team: String) : List<BaseDataUser>{
        return userDao.getAuthUsers(team)
            .map {
            BaseDataUser(
                username = it.username,
                tgTag = it.tgTag,
                tgId = it.tgId,
                team = it.team,
                jobLevel = it.job)
        }
    }
    suspend fun pageData(searchText : String,
                         current : Int,
                         pageSize : Int,
                         sortingField : UserSortingField,
                         sortingUsersType : SortingType) : List<BaseDataUser>{
        return userDao.pageAuthUsers(searchText,current,pageSize,sortingField,sortingUsersType).map {
            BaseDataUser(
                username = it.username,
                tgTag = it.tgTag,
                tgId = it.tgId,
                team = it.team,
                jobLevel = it.job)
        }
    }

    suspend fun registerUser(info : NetworkRegistrationUserInfo) : NetworkResponseRegistrationUser {
        var result = userDao.insertUser(info.username,info.password,info.tgId,info.tgTag,info.seleniumAccess,info.osmiumAccess
            ,info.carboniumAccess,info.kryptonAccess,info.bohriumAccess,info.jobLevel,info.team,info.inviter)
        return NetworkResponseRegistrationUser(result)
    }

    suspend fun getUserInfo(username : String) : UserInfo?{
        return userDao.getUser(username)?.toUserInfo()
    }

}