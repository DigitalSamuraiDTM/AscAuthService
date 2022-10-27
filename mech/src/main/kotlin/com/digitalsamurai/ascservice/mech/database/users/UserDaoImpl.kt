package com.digitalsamurai.ascservice.mech.database.users

import com.digitalsamurai.ascservice.mech.database.entity.AscService
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.digitalsamurai.ascservice.mech.database.users.tables.Users
import com.digitalsamurai.ascservice.mech.encryptors.PasswordEncryptor
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column

internal class UserDaoImpl(private val database : Database,private val passwordEncryptor: PasswordEncryptor) : UserDao {

    private val Database.user get() = this.sequenceOf(Users)


    override suspend fun getUser(username: String): User? {
        return try {
            database.from(Users).select().where {
                Users.username eq username
            }.map { database.user.entityExtractor(it) }[0]
        } catch (e : java.lang.Exception) {
            return null
        }
    }

    override suspend fun getAuthUsers(jobLevel: JobLevel?): List<User> {
        return if (jobLevel==null){
            database.from(Users).select().map { database.user.entityExtractor(it) }
        } else{
            database.from(Users).select().where {
                Users.job eq jobLevel
            }.map { database.user.entityExtractor(it) }
        }
    }

    override suspend fun pageAuthUsers(
        searchText: String?,
        current: Int,
        pageSize: Int,
        sortingField: UserSortingField,
        sortingType: SortingType
    ): List<User> {
       return if (searchText==null) {


            database.from(Users)
                .select()
                .limit(pageSize)
                .offset(current)
                .smartOrdering(sortingField, sortingType)
                .map { database.user.entityExtractor(it) }
        } else{
            val template = "%${searchText}%"
            database.from(Users)
                .select()
                .limit(pageSize)
                .offset(current)
                .smartOrdering(sortingField, sortingType).where {
                (Users.username like template) or
                        (Users.tgTag like template) or
                        (Users.team like template)
            }.map { database.user.entityExtractor(it) }
        }
    }

    override suspend fun updateUsername(oldUsername: String, newUsername: String): Boolean {
        return database.update(Users){
            set(Users.username,newUsername)
            where {
                Users.username eq oldUsername
            }
        } == 1
    }

    override suspend fun updatePassword(username: String, newPassword: String): Boolean {
        return database.update(Users){
            set(Users.password,newPassword)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun updateTeam(username: String, team: String): Boolean {
        return database.update(Users){
            set(Users.team,team)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun updateServiceAccess(username: String, service: AscService, access: Boolean): Boolean {
        val column : Column<Boolean> = when (service){
            AscService.SELENIUM -> {
                Users.canUsePACS}
            AscService.CARBONIUM -> {
                Users.canUseDNS}
            AscService.OSMIUM -> {
                Users.canUsePAGS}
            AscService.BOHRIUM -> {
                Users.canSeeAppsInfo}
            AscService.KRYPTON -> {
                Users.canUseTS}
        }

        return database.update(Users){
            set(column,access)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun updateJobLevel(username: String, jobLevel: JobLevel): Boolean {
        return database.update(Users){
            set(Users.job,jobLevel)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun updateInviter(username: String, inviter: String): Boolean {
        return database.update(Users){
            set(Users.inviter,inviter)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun unlinkTelegramAccount(username: String): Boolean {
        return database.update(Users){
            set(Users.tgTag,null)
            set(Users.tgId,null)
            where {
                Users.username eq username
            }
        } == 1
    }

    override suspend fun deleteUser(username: String): Boolean {
        return try {
            database.delete(Users) {
                Users.username eq username
            } == 1
        } catch (e : java.lang.Exception){
            return false
        }
    }

    private inline fun Query.smartOrdering(column : UserSortingField, type : SortingType) : Query{
        return when(type){
            SortingType.ASCENDING -> {
                this.orderBy(column.column.asc())
            }
            SortingType.DESCENDING -> {
                this.orderBy(column.column.desc())
            }

        }
    }

    override suspend fun getUsersByTeam(team: String): List<User> {
        return database.from(Users).select().where {
            Users.team eq team
        }.map { database.user.entityExtractor(it) }
    }

    override suspend fun isUsernameExist(username: String): Boolean {
        return database.from(Users).select().where {
            Users.username eq username
        }.rowSet.size() == 1

    }

    override suspend fun bindTelegramToAccount(username: String, tgId: String, tgTag: String?): Boolean {
        return database.update(Users){
            set(Users.tgId,tgId)
            set(Users.tgTag, tgTag)
            where {
                Users.username eq username
            }
        } ==1
    }

    override suspend fun insertUser(
        username: String,
        password: String,
        tgId: String?,
        tgTag: String?,
        canUseSelenium: Boolean,
        canUseOsmium: Boolean,
        canUseCarbonium: Boolean,
        canUseKrypton: Boolean,
        canUseBohrium: Boolean,
        jobLevel: JobLevel,
        team: String,
        inviter: String
    ): Boolean {
        if (isUsernameExist(username)){
            return false
        }
        val pass = passwordEncryptor.encryptStringData(password)
        return try {

            database.insert(Users) {
                set(Users.username, username)
                set(Users.password, pass)
                set(Users.tgId, tgId)
                set(Users.tgTag, tgTag)
                set(Users.canUsePACS, canUseSelenium)
                set(Users.canUsePAGS, canUseOsmium)
                set(Users.canUseDNS, canUseCarbonium)
                set(Users.canUseTS, canUseKrypton)
                set(Users.canSeeAppsInfo, canUseBohrium)
                set(Users.job, jobLevel)
                set(Users.team, team)
                set(Users.inviter, inviter)
            } == 1
        } catch (e : java.lang.Exception){
            false
        }
    }
}