package com.digitalsamurai.asc.model.appupdatemanager

import com.digitalsamurai.asc.model.appupdatemanager.entity.AppInfo
import com.digitalsamurai.asc.model.appupdatemanager.fileprovider.FileProvider

class AscModelUpdaterImpl(private val fileProvider: FileProvider) : AscModelUpdater {
    override fun getActualApk(): AppInfo {
        return fileProvider.getActualApp()
    }
}