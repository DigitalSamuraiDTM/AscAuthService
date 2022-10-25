package com.digitalsamurai.asc.controller.ktor.plugins

import com.digitalsamurai.asc.controller.ktor.KtorServer
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureAdminPanelRouting(){
    routing {
        val PUBLIC_PORT = KtorServer.PUBLIC_ASC_PORT
        val PRIVATE_PORT = KtorServer.PRIVATE_ASC_PORT


        /**
         * Teams list
         * Only for [ADMIN]
         * list[String]
         * */

        get("/getTeams"){

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
        get("/getAuthUsers"){

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

        get("/pagingMainUsersInfo"){

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
        get("/getUserInfo"){

        }

    }
}