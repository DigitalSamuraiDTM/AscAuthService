package com.digitalsamurai.ascservice.mech.database.entity

enum class SortingType(val code : Int) {
    ASCENDING(0),DESCENDING(1);
    companion object{
        fun fromInt(num : Int) : SortingType{
            return when(num){
                0->{ASCENDING}
                1->{DESCENDING}
                else->{ASCENDING}
            }
        }
    }
}