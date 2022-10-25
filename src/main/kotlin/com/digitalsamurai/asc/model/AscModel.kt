package com.digitalsamurai.asc.model

import com.digitalsamurai.asc.model.entity.AppInfo

interface AscModel {
    fun getActualApk() : AppInfo
}