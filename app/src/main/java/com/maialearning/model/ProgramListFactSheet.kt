package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class ProgramListFactSheet(@SerializedName("program_name") var programName: String? = "",
                                @SerializedName("duration") var duration: String? = "",
                                @SerializedName("tuition_fee") var tuitionFee: String? = "",
                                @SerializedName("program_id") var programId: String? = "")
