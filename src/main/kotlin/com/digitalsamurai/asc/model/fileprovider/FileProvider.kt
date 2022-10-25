package com.digitalsamurai.asc.model.fileprovider

import com.digitalsamurai.asc.model.entity.AppVersion
import com.digitalsamurai.asc.model.entity.AppInfo
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader

class FileProvider(
    private val fileStorage: String,
    private val jsonActualVersion : String,
    private val fileName : String = "asc.apk") {

    private val patchNoteFileName = "info"

    private val gson = Gson()
    private val storageDirectory = File(fileStorage)

    init {

        if (!File(jsonActualVersion).exists()){
            throw FileNotFoundException("NOT FOUND ACTUAL JSON")
        }
        if (!File(fileStorage).exists()){
            throw FileNotFoundException("STORAGE NOT FOUND")
        }
    }

    fun getActualApp() : AppInfo{
        val version = getActualVersion()
        return AppInfo(File(fileStorage+File.separator+version.toString()+File.separator+fileName),fileName, version,getActualPatchNote())

    }
    fun getDirectoriesCountInStorage() : Int{
        return storageDirectory.list()?.size ?: -1
    }
    fun getActualPatchNote() : String{
        val version = getActualVersion()
        val filePatchNote = File(fileStorage+File.separator+version.toString()+File.separator+patchNoteFileName)
        return filePatchNote.readText()
    }
    fun getActualVersion() : AppVersion{
        val version = gson.fromJson<AppVersion>(JsonReader(FileReader(jsonActualVersion)),AppVersion::class.java)
        return version
    }
}