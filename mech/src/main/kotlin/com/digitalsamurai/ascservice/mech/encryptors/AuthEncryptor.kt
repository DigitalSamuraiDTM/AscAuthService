package com.digitalsamurai.ascservice.mech.encryptors

import java.nio.charset.StandardCharsets
import java.security.AuthProvider
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
import javax.security.auth.Subject
import javax.security.auth.callback.CallbackHandler

class AuthEncryptor(private val rsaEncryptor: RsaEncryptor,
                    private val aesEncryptor: AesEncryptor) {



    init {

    }

    fun encryptData(data : String) : String{
        val aesEncr = aesEncryptor.encryptData(data.toByteArray())
        val rsaEncr = rsaEncryptor.encryptData(aesEncr)
        return Base64.getUrlEncoder().encodeToString(rsaEncr)
    }

    fun decryptData(data :String) : String{
        val rsaDecr = rsaEncryptor.decryptData(Base64.getUrlDecoder().decode(data))
        val aesDecr = aesEncryptor.decryptData(rsaDecr)
        return String(aesDecr)
    }

    fun encryptAes(data : String, key : String) : String{
        return Base64.getUrlEncoder().encodeToString(aesEncryptor.encryptData(data.toByteArray(),
            SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8),"AES")))
    }

    fun getPublicRSAKey() : String{
        return rsaEncryptor.getPublicKey()
    }

    fun getPublicAESKey() : String {
        return aesEncryptor.getKey()
    }
}