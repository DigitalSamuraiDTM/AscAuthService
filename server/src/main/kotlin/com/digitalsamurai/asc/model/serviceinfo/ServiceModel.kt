package com.digitalsamurai.asc.model.serviceinfo

import com.digitalsamurai.asc.controller.entity.serviceinfo.NetworkResponseServiceInfo
import com.digitalsamurai.ascservice.mech.jwt.JwtProvider

class ServiceModel(private val jwtProvider: JwtProvider) {
    fun getServiceInfo(): NetworkResponseServiceInfo{
        return NetworkResponseServiceInfo(jwtProvider.getActualKey())
    }

}