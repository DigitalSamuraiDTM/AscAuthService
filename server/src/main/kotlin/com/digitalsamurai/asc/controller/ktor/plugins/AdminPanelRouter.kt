package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.checkJwtValid
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Job

fun Application.configureAdminPanelRouting(jwtProvider : JwtProvider,userModel :UserModel) {
    val PUBLIC_PORT = KtorServer.PUBLIC_ASC_PORT
    val PRIVATE_PORT = KtorServer.PRIVATE_ASC_PORT
    routing {


        /**
         * Teams list
         * Only for [ADMIN]
         * list[String]
         * */

        get("/getTeams") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val data = userModel.getTeamsList()
                call.respond(data)
            }
        }

        /**
         * BE CAREFUL! THIS REQUEST RETURN ALL USERS DATA WITHOUT PAGING
         *
         * Return all (or by [job]) list
         * Only for [ADMIN] and [TEAMLEAD]
         * [username] : String
         * [tg_tag] : String
         * [tg_id] : String
         * [team]  :String
         * [job] : Enum
         */
        get("/getAuthUsers") {
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val jobLevel = call.request.queryParameters.get("job")
                val data = userModel.getAuthUsersList(JobLevel.fromString(jobLevel))
                call.respond(data)
            }
        }

        /**
         * Return GENERAL user info by [team] parameter
         *
         *
         */

        get("/getUserByTeam"){
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val team = call.request.queryParameters["team"]
                if (team==null){
                    call.respond(HttpStatusCode.BadRequest)
                } else{
                    call.respond(userModel.getAuthUsersList(team))
                }
            }
        }

        /**
         * Params:
         * @param current : Int - current page pos
         * @param pageSize : Int - next page size
         * @param SortingUsers : Enum - sorting field type
         * @param SortingUsersType : Enum - sorting type (asc,desc)
         *
         * Returned data:
         * [username] : String
         * [tg_tag] : String
         * [tg_id] : String
         * [team]  :String
         * [job] : Enum
         */

        get("/pagingMainUsersInfo") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val text = call.request.queryParameters.get("text") ?: ""
                val current = call.request.queryParameters.get("current")?.toInt() ?: 0
                val pageSize = call.request.queryParameters.get("pageSize")?.toInt() ?:10
                val SortingUsers = call.request.queryParameters.get("SortingUsers")?.toInt() ?:0
                val SortingUsersType = call.request.queryParameters.get("SortingUsersType")?.toInt() ?:0
                val data = userModel.pageData(text,current,pageSize,UserSortingField.fromInt(SortingUsers), SortingType.fromInt(SortingUsersType))
                call.respond(data)
            }        }



        /**
         * Return all data by info
         * Only for [ADMIN] and [TEAMLEAD]
         *
         * @param user : String - username
         *
         * Return data:
         *
         *
         * */
        get("/getUserInfo") {
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val username = call.request.queryParameters.get("username")
                username?.let{
                    val data = userModel.getUserInfo(username)
                    call.respond(data ?:"")
                    return@get
                }
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
        }
        delete("/deleteUser"){
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val username = call.request.queryParameters.get("username")
                username?.let {
                    val response = userModel.deleteUser(username)
                    call.respond(response)
                }


                return@delete
            }
            call.respond(HttpStatusCode.BadRequest)
        }




    }
}