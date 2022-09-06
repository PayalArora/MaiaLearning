package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class RecomdersModel (

    @SerializedName("pager" ) var pager : Pager?          = Pager(),
    @SerializedName("data"  ) var data  : ArrayList<Data> = arrayListOf()

){
    data class Pager (

        @SerializedName("current" ) var current : String? = null,
        @SerializedName("last"    ) var last    : Int?    = null

    )
    data class Data (

        @SerializedName("nid"                 ) var nid              : String? = null,
        @SerializedName("uid"                 ) var uid              : String? = null,
        @SerializedName("due_date"            ) var dueDate          : String? = null,
        @SerializedName("note"                ) var note             : String? = null,
        @SerializedName("preserved_school"    ) var preservedSchool  : String? = null,
        @SerializedName("preserved_user"      ) var preservedUser    : String? = null,
        @SerializedName("req_fileid"          ) var reqFileid        : String? = null,
        @SerializedName("req_filename"        ) var reqFilename      : String? = null,
        @SerializedName("reco_created"        ) var recoCreated      : String? = null,
        @SerializedName("name"                ) var name             : String? = null,
        @SerializedName("done"                ) var done             : Int?    = null,
        @SerializedName("cancel"              ) var cancel           : Int?    = null,
        @SerializedName("ucas_ref_letter_req" ) var ucasRefLetterReq : String? = null,
        @SerializedName("ucas_ref_letter_completed_date" ) var ucasRefLetterCompleted : String? = null,
        @SerializedName("ucas_ref_letter_due_date" ) var ucasRefLetterdue : String? = null,
        @SerializedName("ucas_ref_completed" ) var isRefLetterCompleted : Int? = null,
        @SerializedName("completed_date"      ) var completedDate    : String? = null

    )
}