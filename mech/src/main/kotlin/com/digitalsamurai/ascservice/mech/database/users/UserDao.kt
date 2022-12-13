package com.digitalsamurai.ascservice.mech.database.users

import com.digitalsamurai.ascservice.mech.database.entity.AscService
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField
import com.digitalsamurai.ascservice.mech.database.users.tables.AllUserInfo
import com.digitalsamurai.ascservice.mech.database.users.tables.User

interface UserDao {

    suspend fun getAllUserInfo(username :String) : AllUserInfo?

    suspend fun getUsersByTeam(team : String) : List<User>

    suspend fun getAuthUsers(jobLevel: JobLevel?) : List<User>

    suspend fun getAuthUsers(team: String) : List<User>

    suspend fun pageAuthUsers(searchText : String?, current : Int, pageSize : Int, sortingField : UserSortingField, sortingType : SortingType) : List<User>

    suspend fun getUsernameByTgId(tgId : String) : User?

    //-------------UPDATING DATA ---------------------------

    suspend fun updateUsername(oldUsername : String,newUsername : String) : Boolean

    suspend fun updatePassword(username : String,newPassword : String) : Boolean

    suspend fun updateTeam(username : String,team : String) : Boolean

    suspend fun updateServiceAccess(username : String, service : AscService, access : Boolean) : Boolean

    suspend fun updateServiceAccess(username : String, selenium : Boolean,carbonium : Boolean, osmium : Boolean, bohrium : Boolean, krypton : Boolean) : Boolean

    suspend fun updateJobLevel(username : String, jobLevel : JobLevel) : Boolean

    suspend fun updateInviter(username : String, inviter : String) : Boolean

    suspend fun unlinkTelegramAccount(username : String) : Boolean

    suspend fun deleteUser(username : String) : Boolean

    suspend fun isUsernameExist(username : String) : Boolean

    suspend fun bindTelegramToAccount(username : String, tgId : String, tgTag : String?):Boolean

    //------------------ CREATING NEW USER -----------------------

    suspend fun insertUser(username : String,
                           password : String,
                           tgId : String?,
                           tgTag : String?,
                           canUseSelenium : Boolean,
                           canUseOsmium : Boolean,
                           canUseCarbonium : Boolean,
                           canUseKrypton : Boolean,
                           canUseBohrium : Boolean,
                           jobLevel: JobLevel,
                           team : String,
                           inviter :String) : Boolean



}