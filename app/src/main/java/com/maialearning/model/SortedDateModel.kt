package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class SortedDateModel(

    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("assignment")
    var assignment: List<AssignmentItem?>? = null
)