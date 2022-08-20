package com.maialearning.model

data class CountryData(val key:String,val name:String,var select :Boolean)
{
    override fun toString(): String {
        return name
    }

}