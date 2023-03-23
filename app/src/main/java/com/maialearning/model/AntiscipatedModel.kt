package com.maialearning.model

data class AntiscipatedModel(
    var name: String? = null,
    var nid: String? = null,
    var collegeCostCompare: List<CollegeCostCompareItem>? = null
){
    data class CollegeCostCompareItem(var key:String,var value:String, var keyValue: ArrayList<AnticipatedKeyVal?>)
}
