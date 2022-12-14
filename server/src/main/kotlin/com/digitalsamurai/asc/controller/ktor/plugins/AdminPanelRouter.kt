package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.entity.NetworkTeamInfo
import com.digitalsamurai.asc.controller.entity.adminpanel.NetworkCreateTeamData
import com.digitalsamurai.asc.controller.entity.adminpanel.NetworkOkBodyResponse
import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.checkJwtValid
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.entity.SortingType
import com.digitalsamurai.ascservice.mech.database.teams.entity.InteractionsType
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.entity.UserSortingField
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureAdminPanelRouting(jwtProvider : JwtProvider,userModel :UserModel) {
    val PUBLIC_PORT = KtorServer.PUBLIC_ASC_PORT
    val PRIVATE_PORT = KtorServer.PRIVATE_ASC_PORT
    routing {


        get("/isUsernameAvailable") {
            call.checkJwtValid(port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters["username"]
                if (username == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val data = userModel.isUsernameAvailable(username)
                call.respond(data)
            }
        }




        delete("/deleteTeam") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val teamName = call.parameters["team_name"]
                if (teamName!=null){
                    val data = userModel.deleteTeam(teamName)

                    call.respond(data)
                } else{

                }
            }
        }
        post("/registerTeam") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val body = call.receive<NetworkCreateTeamData>()
                val data = userModel.createTeam(body)
                call.respond(data)
            }
        }

        /**
         * All team info
         * Only for [ADMIN],[TEAMLEAD],[AFF],[ARBITR_1W]
         * Return: List[String]
         * */

        get("/getAllTeamInfo") {
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD,JobLevel.ARBITR_1W,JobLevel.AFFILIATE, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val teamName = it.request.queryParameters["team_name"]
                if (teamName==null){
                    call.respond(HttpStatusCode.BadRequest)
                } else{
                    val data = userModel.getAllTeamInfo(teamName)
                    if (data!=null){
                        call.respond(data)
                    } else{
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }

        /**
         * Teams list
         * Only for [ADMIN]
         * Return: List[NetworkTeamInfo]
         * */

        get("/getTeams") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
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
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
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

        get("/getUserByTeam") {
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val team = call.request.queryParameters["team"]
                if (team == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(userModel.getAuthUsersList(team))
                }
            }
        }

        /**
         * Params:
         * @param[current] : Int - current page pos
         * @param[pageSize] : Int - next page size
         * @param[SortingUsers] : Enum - sorting field type
         * @param[SortingUsersType] : Enum - sorting type (asc,desc)
         *
         * @return data:
         * [username] : String
         * [tg_tag] : String
         * [tg_id] : String
         * [team]  :String
         * [job] : Enum
         */

        get("/pagingMainUsersInfo") {
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val text = call.request.queryParameters["text"] ?: ""
                val current = call.request.queryParameters["current"]?.toInt() ?: 0
                val pageSize = call.request.queryParameters["pageSize"]?.toInt() ?: 10
                val SortingUsers = call.request.queryParameters["SortingUsers"]?.toInt() ?: 0
                val SortingUsersType = call.request.queryParameters["SortingUsersType"]?.toInt() ?: 0
                val data = userModel.pageData(
                    text,
                    current,
                    pageSize,
                    UserSortingField.fromInt(SortingUsers),
                    SortingType.fromInt(SortingUsersType)
                )
                call.respond(data)
            }
        }


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
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters["username"]
                username?.let {
                    val data = userModel.getUserInfo(username)
                    call.respond(data ?: "")
                    return@get
                }
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
        }
        delete("/deleteUser") {
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters["username"]
                val owner = call.request.queryParameters["owner"]
                username?.let {
                    if (owner!=null){
                        val response = userModel.deleteUser(owner,username)
                        call.respond(response)
                    } else{
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }


                return@delete
            }
            call.respond(HttpStatusCode.BadRequest)
        }


        /**
         * Update interactions type in database
         * @param[team_name]
         * @param[interactions_type]
         *
         * Only for [ADMIN]
         * */

        put("/updateTeamInteractionType"){
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val teamName = call.request.queryParameters["team_name"]
                val interactionsType = InteractionsType.fromString(call.request.queryParameters["interactions_type"])
                if (teamName==null || interactionsType==null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.updateTeamInteractionType(teamName,interactionsType)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }

        /**
         * Update team name in database
         * @param[team_name]
         * @param[new_team_name]
         *
         * Only for [ADMIN]
         * */

        put("/updateTeamName"){
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val teamName = call.request.queryParameters["team_name"]
                val newTeamName = call.request.queryParameters["new_team_name"]
                if (teamName==null || newTeamName==null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.updateTeamName(teamName,newTeamName)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }

        /**
         * Update team name in database
         * @param[team_name]
         * @param[note]
         *
         * Only for [ADMIN]
         * */

        put("/updateTeamName"){
            call.checkJwtValid(JobLevel.ADMIN, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val teamName = call.request.queryParameters["team_name"]
                val note = call.request.queryParameters["note"]
                if (teamName==null || note==null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.updateTeamNote(teamName,note)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }



        /**
         * Update user data
         * Need for settings, admin panel and <my team>
         *
         * */


        put("/updateUserTeam") {
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters.get("username")
                val team = call.request.queryParameters.get("team")
                val owner = call.request.queryParameters.get("owner")
                if (username == null || team == null || owner == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.updateUserTeam(owner, username, team)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }

        put("/updateUserUsername") {
            call.checkJwtValid(port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters.get("username")
                val newUsername = call.request.queryParameters.get("newUsername")
                val owner = call.request.queryParameters.get("owner")
                if (username == null || newUsername == null || owner == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response =
                        userModel.updateUserUsername(owner = owner, username = username, newUsername = newUsername)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false, "Internal server error"))
                    }
                }
            }
        }
        put("/updateJobLevel"){
            call.checkJwtValid(JobLevel.ADMIN,JobLevel.TEAMLEAD,port = PUBLIC_PORT, jwtProvider = jwtProvider){
                val username = call.request.queryParameters.get("username")
                val job = call.request.queryParameters.get("job")
                val owner = call.request.queryParameters.get("owner")
                if (username == null || job == null || owner == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else{
                    val response = userModel.updateUserJob(owner,username = username,job = job)
                    call.respond(response)
                }
            }
        }
        put("/updateUserInviter") {
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters.get("username")
                val inviter = call.request.queryParameters.get("inviter")
                val owner = call.request.queryParameters.get("owner")
                if (username == null || inviter == null || owner == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.updateUserInviter(owner, username = username, inviter = inviter)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }
        put("/unlinkUserTelegram") {
            call.checkJwtValid(port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val username = call.request.queryParameters.get("username")
                val owner = call.request.queryParameters.get("owner")
                if (username == null || owner == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val response = userModel.unlinkUserTelegram(owner = owner,username = username)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                }
            }
        }

        put("/updateServiceAccess") {
            call.checkJwtValid(JobLevel.ADMIN, JobLevel.TEAMLEAD, port = PUBLIC_PORT, jwtProvider = jwtProvider) {
                val owner = call.request.queryParameters["owner"]
                val username = call.request.queryParameters["username"]
                val selenium = call.request.queryParameters["seleniumAccess"].toBoolean()
                val carbonium = call.request.queryParameters["carboniumAccess"].toBoolean()
                val osmium = call.request.queryParameters["osmiumAccess"].toBoolean()
                val bohrium = call.request.queryParameters["bohriumAccess"].toBoolean()
                val krypton = call.request.queryParameters["kryptonAccess"].toBoolean()
                if (!(username == null || owner == null)) {

                    val response =
                            userModel.updateUserServiceAccess(owner = owner,username = username, selenium, carbonium, osmium, bohrium, krypton)
                    if (response){
                        call.respond(NetworkOkBodyResponse(true))
                    } else{
                        call.respond(NetworkOkBodyResponse(false,"Internal server error"))
                    }
                    call.respond(HttpStatusCode.InternalServerError)
                } else{
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }

    }
}