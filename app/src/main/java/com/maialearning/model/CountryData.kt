package com.maialearning.model

data class CountryData(val key:String,val name:String)
{
    override fun toString(): String {
        return name
    }
}