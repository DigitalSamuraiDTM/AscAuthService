package com.digitalsamurai.ascservice.mech.rt

import com.digitalsamurai.ascservice.mech.rt.entity.RtTokenInfo
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RtProvider(private val hmacToken : String,private val rtConfFile: File) {

    private var hmacEncoder = Mac.getInstance("HmacSHA256")
    init {
        if (rtConfFile.exists()==false){throw FileNotFoundException("Rt Configuration file does not exist")}
        hmacEncoder.init(SecretKeySpec(hmacToken.toByteArray(StandardCharsets.UTF_8),"HmacSHA256"))
    }
    fun createRtToken(jwtToken : String) : String {
        return Base64.getEncoder().encodeToString(hmacEncoder.doFinal(jwtToken.toByteArray()))
    }
    fun getHoursLifetimeToken() : Int{
       return BufferedReader(FileReader(rtConfFile)).readLine().toInt()
    }
}