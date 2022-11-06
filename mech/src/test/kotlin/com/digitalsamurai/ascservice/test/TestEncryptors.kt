package com.digitalsamurai.ascservice.test

import com.digitalsamurai.ascservice.mech.encryptors.AesEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.AuthEncryptor
import com.digitalsamurai.ascservice.mech.encryptors.RsaEncryptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Base64

class TestEncryptors {

    private lateinit var authEncryptor: AuthEncryptor
    private lateinit var aes: AesEncryptor
    private lateinit var rsa: RsaEncryptor

    @BeforeEach
    fun before(){
        aes = AesEncryptor(null)
        rsa = RsaEncryptor(null,null)
        authEncryptor = AuthEncryptor(rsaEncryptor = rsa, aesEncryptor = aes)
    }
    @Test
    fun testAes(){

        val data = "FACK OBEMA"
        var resultEncr = aes.encryptData(data.toByteArray())
        println("encrpyted: ${Base64.getEncoder().encodeToString(resultEncr)}")
        var resultDecr = aes.decryptData(resultEncr)
        println("decrypted: ${Base64.getEncoder().encodeToString(resultDecr)}")
        assert(data==String(resultDecr))
    }

    @Test
    fun testRsaWithAes(){

        val data = "FACK OBEMA??"
        val encrAes = aes.encryptData(data.toByteArray())
        println("encrypted aes: ${Base64.getEncoder().encodeToString(encrAes)}")
        val encrRsa = rsa.encryptData(encrAes)
        println("encrypted rsa: ${Base64.getEncoder().encodeToString(encrRsa)}")
        val decrRsa = rsa.decryptData(encrRsa)
        println("decrypted rsa: ${Base64.getEncoder().encodeToString(decrRsa)}")
        val decrAes = aes.decryptData(decrRsa)
        println("decrypted aes: ${Base64.getEncoder().encodeToString(decrAes)}")
        println(String(decrAes))
        assert(data == String(decrAes))


    }

    @Test
    fun testAuth(){
        val data = "FACK OBEMA??"
        val encryptedData = authEncryptor.encryptData(data)
        println(encryptedData)
        val decryptedData  =authEncryptor.decryptData(encryptedData)
        println(decryptedData)
        assert(decryptedData==data)
    }
}