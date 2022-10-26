package com.digitalsamurai.ascservice.test

import com.digitalsamurai.ascservice.mech.database.users.entity.JobLevel
import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.reflect.KClass


class JwtProviderTest {

    private lateinit var jwtProvider: JwtProvider

    @BeforeEach
    fun before(){
        jwtProvider = JwtProvider(
            "cjX8BNJMoUKLZBo1VsZl29tPhB+c46eBRoml5z7rCU0=",
            Gson(),
            File("C:\\Users\\Андрей\\Desktop\\conf.txt"))
    }

    @Test
    fun test(){
        val data = jwtProvider.createNewJwtKey(User{
            this.username = "user"
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

        println(jwtProvider.getTokenStatus(data))
        assert(true)
    }
}