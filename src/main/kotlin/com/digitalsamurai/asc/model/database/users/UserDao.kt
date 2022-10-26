package com.digitalsamurai.asc.model.database.users

import com.digitalsamurai.asc.model.database.entity.AscService
import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import com.digitalsamurai.asc.model.database.entity.SortingType
import com.digitalsamurai.asc.model.database.users.tables.User
import com.digitalsamurai.asc.model.database.users.entity.UserSortingField

interface UserDao {

    suspend fun getAuthUsers(jobLevel: JobLevel?) : List<User>

    suspend fun pageAuthUsers(searchText : String?, current : Int, pageSize : Int, sortingField : UserSortingField, sortingType : SortingType) : List<User>

    suspend fun updateUsername(oldUsername : String,newUsername : String) : Boolean

    suspend fun updatePassword(username : String,newPassword : String) : Boolean

    suspend fun updateTeam(username : String,team : String) : Boolean

    suspend fun updateServiceAccess(username : String, service : AscService, access : Boolean) : Boolean

    suspend fun updateJobLevel(username : String, jobLevel : JobLevel) : Boolean

    suspend fun updateInviter(username : String, inviter : String) : Boolean

    suspend fun unlinkTelegramAccount(username : String) : Boolean

    suspend fun deleteUser(username : String) : Boolean

    suspend fun isUsernameExist(username : String) : Boolean

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
    suspend fun getUsersByTeam(team : String) : List<User>
}