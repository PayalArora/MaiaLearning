package com.maialearning.model

import com.google.gson.annotations.SerializedName


class AddProgramConsider {
    var trans_req_nid: String? = null
    var program_data: ArrayList<Programs?>? = null

    class Programs() {
         var program_name:String=""
        var program_id: Int? = null

        @SerializedName("program_deadline")
         var program_deadline: String?=null
        @SerializedName("program_status")
       lateinit var program_status: String
    }
}