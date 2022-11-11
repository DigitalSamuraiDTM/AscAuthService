package com.digitalsamurai.ascservice.mech.encryptors

import java.lang.Math.ceil
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
import kotlin.collections.ArrayList

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
        var decryptedRsa = Base64.getUrlDecoder().decode(data)
        var buffer = ArrayList<Byte>()
        repeat(kotlin.math.ceil(decryptedRsa.size / 256.0).toInt()){
            val rsaDecr = rsaEncryptor.decryptData(decryptedRsa.slice(
                IntRange(it*256,if ((it+1)*256<decryptedRsa.size){(it+1)*256-1}else{decryptedRsa.size-1})).toByteArray())
            buffer.addAll(rsaDecr.toList())
        }
        val aesDecr = aesEncryptor.decryptData(buffer.toByteArray())
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