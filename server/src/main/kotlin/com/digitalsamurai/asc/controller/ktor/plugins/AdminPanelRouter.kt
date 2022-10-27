package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.ktor.KtorServer
import com.digitalsamurai.asc.controller.ktor.KtorServer.Companion.checkJwtValid
import com.digitalsamurai.asc.model.usermanager.UserModel
import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtStatus
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.netty.handler.codec.http.HttpStatusClass

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
            call.checkJwtValid(JobLevel.ADMIN,port = PUBLIC_PORT, jwtProvider = jwtProvider){

            }
        }

        /**
         * Return all (or by job) list
         * Only for [ADMIN] and [TEAMLEAD]
         * [username] : String
         * [tg_tag] : String
         * [tg_id] : String
         * [team]  :String
         * [job] : Enum
         */
        get("/getAuthUsers") {

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

        }

        /**
         *  Return main users info with
         *  @param text
         * */
        get("/findUsers") {

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

        }





    }
}