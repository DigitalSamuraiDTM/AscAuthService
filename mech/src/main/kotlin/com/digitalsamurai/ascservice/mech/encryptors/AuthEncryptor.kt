package com.digitalsamurai.ascservice.mech.encryptors

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AuthEncryptor(private val rsaEncryptor: RsaEncryptor,
                    private val aesEncryptor: AesEncryptor) {



    init {

    }

    fun encryptData(data : String) : String{
        val aesEncr = aesEncryptor.encryptData(data.toByteArray())
        val rsaEncr = rsaEncryptor.encryptData(aesEncr)
        return Base64.getEncoder().encodeToString(rsaEncr)
    }

    fun decryptData(data :String) : String{
        val rsaDecr = rsaEncryptor.decryptData(Base64.getDecoder().decode(data))
        val aesDecr = aesEncryptor.decryptData(rsaDecr)
        return String(aesDecr)
    }

    fun getPublicRSAKey() : String{
        return rsaEncryptor.getPublicKey()
    }

    fun getPublicAESKey() : String {
        return aesEncryptor.getKey()
    }
}