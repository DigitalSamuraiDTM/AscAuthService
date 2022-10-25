package com.digitalsamurai.asc.model.database.users

import com.digitalsamurai.asc.model.database.users.entity.JobLevel
import com.digitalsamurai.asc.model.database.entity.SortingType
import com.digitalsamurai.asc.model.database.users.tables.User
import com.digitalsamurai.asc.model.database.users.entity.UserSortingField
import com.digitalsamurai.asc.model.database.users.tables.Users
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import org.ktorm.expression.OrderByExpression

class UserDaoImpl(private val database : Database) : UserDao {

    private val Database.user get() = this.sequenceOf(Users)



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
        current: Int,
        pageSize: Int,
        sortingField: UserSortingField,
        sortingType: SortingType
    ): List<User> {
        return database.from(Users)
            .select()
            .limit(pageSize)
            .offset(current)
            .smartOrdering(sortingField,sortingType)
            .map { database.user.entityExtractor(it) }

    }


    override suspend fun findUsers(text: String): List<User> {

        return database.from(Users).select(Users.username).where {
                    (Users.username like text) or
                    (Users.tgTag like text) or
                    (Users.team like text)
        }.map { database.user.entityExtractor(it) }
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
}