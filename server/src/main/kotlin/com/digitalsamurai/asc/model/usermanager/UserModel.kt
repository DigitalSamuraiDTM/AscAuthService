package com.digitalsamurai.asc.model.usermanager

import com.digitalsamurai.asc.controller.entity.*
import com.digitalsamurai.asc.controller.entity.adminpanel.NetworkOkBodyResponse
import com.digitalsamurai.asc.controller.entity.adminpanel.UsernameExistResponse
import com.digitalsamurai.asc.controller.entity.adminpanel.toBaseUserInfo
import com.digitalsamurai.asc.controller.entity.auth.NetworkRequestBindTelegram
import com.digitalsamurai.asc.model.usermanager.entity.BaseDataUser
import com.digitalsamurai.asc.model.usermanager.entity.UserInfo
import com.digitalsamurai.asc.model.usermanager.entity.toUserInfo
import com.digitalsamurai.ascservice.mech.database.access.AppAccessDao
import com.digitalsamurai.ascservice.mech.database.entity.AscService
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.teams.TeamDao
import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.users.UserDao
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField
import com.digitalsamurai.ascservice.mech.database.users.tables.AllUserInfo
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload

class UserModel(private val teamDao: TeamDao,
                private val userDao : UserDao,
                private val appAccessDao: AppAccessDao) {

    //DEFAULT INFO
    private val registrationAccessMap = mapOf<JobLevel, List<JobLevel>>(
        JobLevel.WEB to listOf(),
        JobLevel.AFFILIATE to listOf(JobLevel.WEB),
        JobLevel.TEAMLEAD to listOf(JobLevel.WEB, JobLevel.AFFILIATE, JobLevel.TEAMLEAD, JobLevel.ARBITR_1W),
        JobLevel.ARBITR_1W to listOf(JobLevel.ARBITR_1W),
        JobLevel.ADMIN to listOf(
            JobLevel.WEB,
            JobLevel.AFFILIATE,
            JobLevel.TEAMLEAD,
            JobLevel.ARBITR_1W,
            JobLevel.ARBITR_1W,
            JobLevel.ADMIN
        )
    )

    suspend fun getTeamsList(): List<NetworkTeamInfo> {
        return teamDao.getTeamsList().map { it.toNetworkTeam() }
    }

    suspend fun deleteUser(owner: String, username: String): NetworkOkBodyResponse {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)

        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            val dbResponse = userDao.deleteUser(username)
            if (dbResponse) {
                NetworkOkBodyResponse(true)

            } else {
                NetworkOkBodyResponse(false, "Request to database was failed")

            }
        } else {
            NetworkOkBodyResponse(false, "Undefined username")
        }
    }

    suspend fun getAuthUsersList(jobLevel: JobLevel?): List<BaseDataUser> {
        return userDao.getAuthUsers(jobLevel).map {
            BaseDataUser(
                username = it.username,
                tgTag = it.tgTag,
                tgId = it.tgId,
                team = it.team,
                jobLevel = it.job
            )
        }
    }

    suspend fun getAuthUsersList(team: String): List<BaseDataUser> {
        return userDao.getAuthUsers(team)
            .map {
                BaseDataUser(
                    username = it.username,
                    tgTag = it.tgTag,
                    tgId = it.tgId,
                    team = it.team,
                    jobLevel = it.job
                )
            }
    }

    suspend fun pageData(
        searchText: String,
        current: Int,
        pageSize: Int,
        sortingField: UserSortingField,
        sortingUsersType: SortingType
    ): List<BaseDataUser> {
        return userDao.pageAuthUsers(searchText, current, pageSize, sortingField, sortingUsersType).map {
            BaseDataUser(
                username = it.username,
                tgTag = it.tgTag,
                tgId = it.tgId,
                team = it.team,
                jobLevel = it.job
            )
        }
    }

    suspend fun registerUser(jwt: JwtPayload, info: NetworkRegistrationUserInfo): NetworkResponseRegistrationUser {


        val targetRegList = registrationAccessMap[jwt.access] ?: return NetworkResponseRegistrationUser(
            false,
            3,
            "Registration access job denied"
        )
        if (!targetRegList.contains(info.jobLevel)) {
            return NetworkResponseRegistrationUser(false, 3, "Registration access denied")
        }
        if (jwt.access != JobLevel.ADMIN && jwt.team != info.team) {
            return NetworkResponseRegistrationUser(false, 3, "Registration team access denied")
        }

        val isUsernameExist = userDao.isUsernameExist(info.username)
        if (isUsernameExist) {
            return NetworkResponseRegistrationUser(false, 1)
        }

        if (info.tgId != null) {
            val userTg = userDao.getUsernameByTgId(info.tgId)
            if (userTg != null) {
                return NetworkResponseRegistrationUser(false, 2)
            }
        }

        val result = userDao.insertUser(
            info.username,
            info.password,
            info.tgId,
            info.tgTag,
            info.seleniumAccess,
            info.osmiumAccess,
            info.carboniumAccess,
            info.kryptonAccess,
            info.bohriumAccess,
            info.jobLevel,
            info.team,
            info.inviter
        )
        return if (result) {
            NetworkResponseRegistrationUser(true, 0)
        } else {
            NetworkResponseRegistrationUser(false, 3, "Database request error")
        }

    }

    suspend fun getUserInfo(username: String): UserInfo? {
        return userDao.getAllUserInfo(username)?.toUserInfo()
    }

    suspend fun unlinkUserTelegram(owner: String, username: String): Boolean {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)
        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            userDao.unlinkTelegramAccount(username)
        } else {
            false
        }
    }

    suspend fun updateUserServiceAccess(
        owner: String,
        username: String,
        selenium: Boolean,
        carbonium: Boolean,
        osmium: Boolean,
        bohrium: Boolean,
        krypton: Boolean
    ): Boolean {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)
        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            userDao.updateServiceAccess(username, selenium, carbonium, osmium, bohrium, krypton)
        } else {
            false
        }
    }

    suspend fun updateUserInviter(owner: String, username: String, inviter: String): Boolean {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)
        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            val userInviter = userDao.getAllUserInfo(inviter)
            if (userInviter != null) {
                userDao.updateInviter(username, inviter)
            } else {
                false
            }
        } else {
            false
        }
    }

    suspend fun updateUserUsername(owner: String, username: String, newUsername: String): Boolean {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)
        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            if (userDao.isUsernameExist(newUsername)) {
                false
            } else {
                userDao.updateUsername(username, newUsername)
            }
        } else {
            false
        }
    }

    suspend fun updateUserTeam(owner: String, username: String, team: String): Boolean {
        val userRequestOwner = userDao.getAllUserInfo(owner)
        val userRequestTarget = userDao.getAllUserInfo(username)
        return if (checkInternalUserEditAccess(userRequestOwner, userRequestTarget)) {
            if (teamDao.getTeamsList().map { it.teamName }.contains(team)) {
                userDao.updateTeam(username, team)
            } else {
                false
            }
        } else {
            false
        }
    }

    suspend fun updateUserPassword(jwt: JwtPayload, info: NetworkUpdatePassword): NetworkOkBodyResponse {
        val targetUser = userDao.getAllUserInfo(info.username)
        val requestOwner = userDao.getAllUserInfo(jwt.user)
        return if (checkInternalUserEditAccess(requestOwner, targetUser)) {
            val dbResponse = userDao.updatePassword(info.username, info.newPassword)
            if (dbResponse) {
                NetworkOkBodyResponse(true)
            } else {
                NetworkOkBodyResponse(false, "Database request error")
            }
        } else {
            NetworkOkBodyResponse(false, "Access denied")
        }

    }

    suspend fun isUsernameAvailable(username: String): UsernameExistResponse {
        val response = userDao.isUsernameExist(username)
        return UsernameExistResponse(true, null, response)
    }

    suspend fun updateUserJob(owner: String, username: String, job: String): NetworkOkBodyResponse {
        val jobLevel: JobLevel =
            JobLevel.fromString(job) ?: return NetworkOkBodyResponse(false, "Request unknown job level")
        val targetUser = userDao.getAllUserInfo(username)
        val requestOwner = userDao.getAllUserInfo(owner)
        return if (checkInternalUserEditAccess(target = targetUser, owner = requestOwner)) {
            if (targetUser == requestOwner) {
                NetworkOkBodyResponse(false, "User cannot change his access")
            } else {
                val dbResponse = userDao.updateJobLevel(targetUser?.username!!, jobLevel)
                if (dbResponse) {
                    if (jobLevel == JobLevel.WEB) {
                        userDao.updateServiceAccess(targetUser.username, AscService.CARBONIUM, false)
                        userDao.updateServiceAccess(targetUser.username, AscService.OSMIUM, false)
                        userDao.updateServiceAccess(targetUser.username, AscService.KRYPTON, false)
                    }
                    NetworkOkBodyResponse(true)
                } else {
                    NetworkOkBodyResponse(false, "Database request error")
                }
            }
        } else {
            NetworkOkBodyResponse(false, "Access denied")
        }
    }

    suspend fun getAllTeamInfo(teamName: String): NetworkAllTeamInfo? {
        val mainTeamInfo = teamDao.getTeamInfo(teamName)
        val users = userDao.getUsersByTeam(teamName).map { it.toBaseUserInfo() }
        return if (mainTeamInfo!=null){
            NetworkAllTeamInfo(
                teamName =teamName,
                interactionsType = mainTeamInfo.interactionsType,
                note = mainTeamInfo.note,
                usersList = users,
                countApkInstalls = teamDao.getCountApkInstallsByTeam(teamName),
                countApk = teamDao.getCountApkByTeam(teamName),
                countNamings = teamDao.getCountNamingsByTeam(teamName),
                countSharingRequests = teamDao.getCountSharingRequest(teamName),
                countSharingCabinets = teamDao.getCountSharedCabinetsByTeam(teamName),
                countAvailableApps =  appAccessDao.getTeamAccess(teamName).size)
        } else{
            null
        }
    }

    suspend fun bindTelegram(info: NetworkRequestBindTelegram): NetworkOkBodyResponse {
        val user = userDao.getAllUserInfo(info.username)
        return if (user != null) {
            if (userDao.bindTelegramToAccount(user.username, info.tgId, null)) {
                NetworkOkBodyResponse(true)
            } else {
                NetworkOkBodyResponse(false, "Database request error")
            }
        } else {
            NetworkOkBodyResponse(false, "User not found")
        }
    }

    suspend fun updateTeamInteractionType(teamName: String, interactionsType: InteractionsType): Boolean {
        val team = teamDao.getTeamInfo(teamName)
        team?.let {
            return teamDao.updateInteractionsType(teamName, interactionsType)
        }
        return false
    }

    suspend fun updateTeamName(teamName: String, newTeamName : String): Boolean {
        val team = teamDao.getTeamInfo(teamName)
        team?.let {
            return teamDao.updateTeamName(teamName, newTeamName)
        }
        return false
    }
    suspend fun updateTeamNote(teamName: String, note : String): Boolean {
        val team = teamDao.getTeamInfo(teamName)
        team?.let {
            return teamDao.updateTeamNote(teamName, note)
        }
        return false
    }

    private fun checkInternalUserEditAccess(owner: AllUserInfo?, target: AllUserInfo?): Boolean {
        if (owner == null || target == null) {
            return false
        }
        return owner.username == target.username ||
                owner.job == JobLevel.TEAMLEAD && owner.team == target.team ||
                owner.job == JobLevel.ADMIN
    }
}
