package com.digitalsamurai.ascservice.mech.jwt

import com.digitalsamurai.ascservice.mech.database.users.tables.User
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtHeader
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtPayload
import com.digitalsamurai.ascservice.mech.jwt.entity.JwtStatus
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class JwtProvider(
    private var hmacKey : String,
    private var gson : Gson,
    private val configurationFile : File
) {

    private var hmacEncoder = Mac.getInstance("HmacSHA256")

    init {
        if (!configurationFile.exists()) {throw FileNotFoundException()}
        hmacEncoder.init(SecretKeySpec(hmacKey.toByteArray(StandardCharsets.UTF_8),"HmacSHA256"))
    }
    private val header = JwtHeader()

    fun updateHmackey(hmacKey: String){
        this.hmacKey = hmacKey
        hmacEncoder.init(SecretKeySpec(hmacKey.toByteArray(StandardCharsets.UTF_8),"HmacSHA256"))
    }
    fun createNewJwtKey(jwtPayload : JwtPayload) : String{
        //encode header and payload in base64
        val encodedHeaderString = Base64.getEncoder().withoutPadding().encodeToString(gson.toJson(header).toByteArray(Charsets.UTF_8))
        val encodedPayloadString = Base64.getEncoder().withoutPadding().encodeToString(gson.toJson(jwtPayload).toByteArray(Charsets.UTF_8))

        //create signature encode text
        val signature = Base64.getEncoder().withoutPadding().encodeToString(hmacEncoder.doFinal("${encodedHeaderString}.${encodedPayloadString}".toByteArray(Charsets.UTF_8)))
        return "${encodedHeaderString}.${encodedPayloadString}.${signature}"
    }

    fun createNewJwtKey(user : User,device : String) : String{
        //create payload before
        val jwtPayload = JwtPayload(
            user = user.username,
            dateDeath = createJwtDeathTime(),
            device = device,
            FACS = user.canUseSelenium,
            TS = user.canUseKrypton,
            MAAS = user.canUseBohrium,
            DNS = user.canUseCarbonium,
            PAGS = user.canUseOsmium,
            access = user.job,
            team = user.team)
        return createNewJwtKey(jwtPayload)
    }


    fun getTokenStatus(jwt :String ): JwtStatus {
        with(jwt.split(".")) {
            val header = this.get(0)
            val payload = this.get(1)
            val signature = this.get(2)

            //decode payload data and get death time
            val decodePayloadData = getPayload(jwt)
            val dateDeath = LocalDateTime.parse(decodePayloadData.dateDeath, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))

            //check signature coding
            val encodeSignature = Base64.getEncoder().withoutPadding().encodeToString(hmacEncoder.doFinal("${header}.${payload}".toByteArray(StandardCharsets.UTF_8)))

            if (encodeSignature != signature) {
                return JwtStatus.INVALID
            }


            //check death time
            if (dateDeath.isBefore(LocalDateTime.now())) {
                return JwtStatus.EXPIRED
            }

            return JwtStatus.ACTIVE
        }
    }

    fun getPayload(jwt : String) : JwtPayload{
        with(jwt.split(".")){
            val payload = this[1]

            //decode payload
            val decodePayloadData = Gson().fromJson(String(Base64.getDecoder().decode(payload.toByteArray())), JwtPayload::class.java)
            val dateDeath = LocalDateTime.parse(decodePayloadData.dateDeath, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))

            return  decodePayloadData
        }
    }

    private fun createJwtDeathTime() : String{
        val hoursTokenLife = BufferedReader(FileReader(configurationFile)).readLine()
        val currentDateTime = LocalDateTime.now().plusHours(hoursTokenLife.toLong())
        return currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
    }
}