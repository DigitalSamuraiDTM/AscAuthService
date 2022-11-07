package com.digitalsamurai.ascservice.mech.encryptors

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesEncryptor(private var key : String?) {

    private var symbols = "abcdefghijklmnopqrstuvwxyz1234567890"
    private var random : Random = Random()


    private val keyGenerator = KeyGenerator.getInstance("AES")

    private var cipherAesEncryptor = Cipher.getInstance("AES/CBC/PKCS5Padding")
    private var cipherAesDecryptor = Cipher.getInstance("AES/CBC/PKCS5Padding")

    private var aesKey : SecretKey

    init {
        if (key==null){
            keyGenerator.init(128)
            aesKey = generateRandomSecretKey()
            key = String(aesKey.encoded)
        } else{
            aesKey = SecretKeySpec(key!!.toByteArray(StandardCharsets.UTF_8),"AES")
            key = String(aesKey.encoded)
        }
        cipherAesEncryptor.init(Cipher.ENCRYPT_MODE,aesKey, IvParameterSpec(ByteArray(16)))
        cipherAesDecryptor.init(Cipher.DECRYPT_MODE,aesKey, IvParameterSpec(ByteArray(16)))
    }

    fun generateRandomSecretKey() : SecretKey {
        var keyString = ""
        repeat(16){
            keyString+=symbols.get(random.nextInt(symbols.length))
        }
        return SecretKeySpec(keyString.toByteArray(), "AES")
    }

    fun encryptData(data : ByteArray, key : SecretKey) : ByteArray{
        val cipher = Cipher.getInstance("AES").also {  it.init(Cipher.ENCRYPT_MODE,key)}
        return cipher.doFinal(data)
    }
    fun decryptData(data : ByteArray, key : SecretKey) : ByteArray{
        val cipher = Cipher.getInstance("AES").also {  it.init(Cipher.DECRYPT_MODE,key)}
        return cipher.doFinal(data)
    }
    fun encryptData(data : ByteArray) : ByteArray{
        var result = cipherAesEncryptor.doFinal(data)
        return result
    }
    fun decryptData(data : ByteArray) : ByteArray{
        var result = cipherAesDecryptor.doFinal(data)
        return result
    }
    fun getKey() : String{
        return key!!
    }
}