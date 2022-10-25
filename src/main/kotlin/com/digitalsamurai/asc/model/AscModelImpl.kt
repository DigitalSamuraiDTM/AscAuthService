package com.digitalsamurai.asc.model

import com.digitalsamurai.asc.model.entity.AppInfo
import com.digitalsamurai.asc.model.fileprovider.FileProvider

class AscModelImpl(private val fileProvider: FileProvider) : AscModel {
    override fun getActualApk(): AppInfo {
        return fileProvider.getActualApp()
    }
}