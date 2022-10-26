package com.digitalsamurai.ascservice.mech.encryptors

import java.math.BigInteger
import java.security.MessageDigest

class PasswordEncryptor {
    private val encryptor : MessageDigest = MessageDigest.getInstance("SHA-512")

    //TODO Need add salt

    /**
     * Encrypt String data
     * */

    fun encryptStringData(text : String) : String{

        encryptor.update(text.toByteArray())
        val data =encryptor.digest()
        return BigInteger(1,data).toString(16).format("%032d",0)
    }
}