package com.digitalsamurai.ascservice.test

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
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
        rtProvider = RtProvider("cjX8BNJMoUKLZBo1VsZl29tPhB+c46eBRoml5z7rCU0=",File("C:\\Users\\Андрей\\Desktop\\conf.txt"))
        jwtProvider = JwtProvider(
            "cjX8BNJMoUKLZBo1VsZl29tPhB+c46eBRoml5z7rCU0=",
            Gson(),
            File("C:\\Users\\Андрей\\Desktop\\conf.txt"))
    }

    @Test
    fun test(){
        val data = jwtProvider.createNewJwtKey(User{
            this.username = "andrew"
            this.job = JobLevel.WEB
            this.inviter = "andrew"
            this.isUseTgAlarm = false
            this.team = "radiant"
            this.canUseBohrium = false
            this.canUseCarbonium = true
            this.canUseKrypton = false
            this.canUseOsmium = true
            this.canUseSelenium = false
            this.password = "awdawd"
            this.tgId = "pudge"
            this.tgTag = "rudje"
        },"Iphone100")
        println(data)

        print("RT: "+rtProvider.createRtToken(data))

        println(jwtProvider.getTokenStatus(data))
        assert(true)
    }
    @Test
    fun checkEqual(){
        val jwt = "eyJhbGciOiJKV1QiLCJ0eXAiOiJIUzI1NiJ9.eyJ1c2VyIjoiYW5kcmV3IiwiZGF0ZV9kZWF0aCI6IjA4LjExLjIwMjIgMDA6NTA6NTMiLCJkZXZpY2UiOiJJcGhvbmUxMDAiLCJGQUNTIjpmYWxzZSwiVFMiOmZhbHNlLCJNQUFTIjpmYWxzZSwiRE5TIjp0cnVlLCJQQUdTIjp0cnVlLCJhY2Nlc3MiOiJXRUIiLCJ0ZWFtIjoicmFkaWFudCJ9.KFJVgl1GbCRtyzMpIJyYl1a2cmrXz-JuIZ_LpL39zd4"
        val rt = "vQOKCZJQMLz2XwJ9_Q1uSSUf80QWVCfYXlaDrU0mKso="
        val new = rtProvider.isJwtBelongRt(jwt,rt)
        println(new)
        assert(new)
    }
}