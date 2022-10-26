package com.digitalsamurai.asc.model.appupdatemanager

import com.digitalsamurai.asc.model.appupdatemanager.entity.AppInfo

interface AscModelUpdater {
    fun getActualApk() : AppInfo
}