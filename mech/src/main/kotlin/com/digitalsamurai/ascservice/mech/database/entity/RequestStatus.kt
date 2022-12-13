package com.digitalsamurai.ascservice.mech.database.entity

import com.google.gson.annotations.SerializedName

enum class RequestStatus(val codeStatus : Int) {

    @SerializedName("QUEUE")
    QUEUE(1),
    @SerializedName("RUNNING")
    RUNNING(0),
    @SerializedName("FINISHED")
    FINISHED(2),
    @SerializedName("ERROR")
    ERROR(3)

}