package com.digitalsamurai.asc.model.database.users

import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import com.digitalsamurai.asc.model.database.entity.SortingType
import com.digitalsamurai.asc.model.database.users.tables.User
import com.digitalsamurai.asc.model.database.users.entity.UserSortingField

interface UserDao {

    suspend fun getAuthUsers(jobLevel: JobLevel?) : List<User>

    suspend fun pageAuthUsers(current : Int, pageSize : Int, sortingField : UserSortingField, sortingType : SortingType) : List<User>

    suspend fun findUsers(text : String) : List<User>
}