package com.digitalsamurai.ascservice.test

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.tables.AllUserInfo
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload
import com.digitalsamurai.ascservice.mech.rt.RtProvider
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.reflect.KClass


class JwtProviderTest {

    private lateinit var jwtProvider: JwtProvider
    private lateinit var rtProvider: RtProvider

    @BeforeEach
    fun before(){
        rtProvider = RtProvider("cjX8BNJMoUKLZBo1VsZl29tPhB+c46eBRoml5z7rCU0=",File("C:\\Users\\andre\\Desktop\\conf.txt"))
        jwtProvider = JwtProvider(
            "cjX8BNJMoUKLZBo1VsZl29tPhB+c46eBRoml5z7rCU0=",
            Gson(),
            File("C:\\Users\\andre\\Desktop\\conf.txt"))
    }

    @Test
    fun test(){
        val data = jwtProvider.createNewJwtKey(AllUserInfo(
            username = "andrew",
            job = JobLevel.WEB,
            isUseTgAlarm = false,
            team = "radiant",
            canUseBohrium = false,
            canUseCarbonium = true,
            canUseKrypton = false,
            canUseOsmium = true,
            canUseSelenium = false,
            password = "awdawd",
            tgId = "pudge",
            tgTag = "rudje",
            inviter = "123"
        ),"Iphone100")
        println(data)

        print("RT: "+rtProvider.createRtToken(data))

        println(jwtProvider.getTokenStatus(data))
        assert(true)
    }
    @Test
    fun checkEqual(){
//        val jwt = "eyJhbGciOiJKV1QiLCJ0eXAiOiJIUzI1NiJ9.eyJ1c2VyIjoiYW5kcmV3IiwiZGF0ZV9kZWF0aCI6IjExLjEyLjIwMjIgMTg6MjA6MTUiLCJkZXZpY2UiOiJhbmRyb2lkIDo6IEFuZHJvaWQgU0RLIGJ1aWx0IGZvciB4ODYgOjogMzAiLCJGQUNTIjp0cnVlLCJUUyI6dHJ1ZSwiTUFBUyI6dHJ1ZSwiRE5TIjp0cnVlLCJQQUdTIjp0cnVlLCJhY2Nlc3MiOiJBRE1JTiIsInRlYW0iOiJEZXZlbG9wZXJzIn0.-ApBAHyi6q3HAUQlpIGIJr0NDIHpzureS_nxYBKOAZM"
        val jwt = "eyJhbGciOiJKV1QiLCJ0eXAiOiJIUzI1NiJ9.eyJ1c2VyIjoidXNlcjUiLCJkYXRlX2RlYXRoIjoiMTEuMTIuMjAyMiAxODoxMToxOCIsImRldmljZSI6ImFuZHJvaWQgOjogQW5kcm9pZCBTREsgYnVpbHQgZm9yIHg4NiA6OiAzMCIsIkZBQ1MiOnRydWUsIlRTIjpmYWxzZSwiTUFBUyI6ZmFsc2UsIkROUyI6dHJ1ZSwiUEFHUyI6dHJ1ZSwiYWNjZXNzIjoiVEVBTUxFQUQiLCJ0ZWFtIjoiQ1BBX01vc2NvdyJ9.HkxmgOGFYOCYD3flmU_Bks2euxacQw4yQlCMqVvnbYI"
        val rt = "7NzJifsC37GP3CkKDixV3MoW6Z0RoGmK10xOACKbGCM="
        val new = rtProvider.isJwtBelongRt(jwt,rt)
        println(new)
        assert(new)
    }
}