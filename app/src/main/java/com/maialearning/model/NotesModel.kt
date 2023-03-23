package com.maialearning.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NotesModel (

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null
) { data class DataItem(

        @field:SerializedName("informed")
        val informed: List<InformedItem?>? = null,

        @field:SerializedName("file")
        val file: File? = null,

        @field:SerializedName("visible_to")
        val visibleTo: List<String?>? = null,

        @field:SerializedName("created")
        val created: Int? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("uuid")
        val uuid: String? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("assigned_to")
        val assignedTo: AssignedTo? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("changed")
        val changed: Int? = null
    ):Serializable

    data class AssignedTo(

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("salutation")
        val salutation: String? = null,

        @field:SerializedName("middle_name")
        val middleName: String? = null,

        @field:SerializedName("uuid")
        val uuid: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null
    ):Serializable

    data class InformedItem(

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("salutation")
        val salutation: String? = null,

        @field:SerializedName("middle_name")
        val middleName: String? = null,

        @field:SerializedName("uuid")
        val uuid: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null
    ):Serializable

    data class User(

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("salutation")
        val salutation: String? = null,

        @field:SerializedName("middle_name")
        val middleName: Any? = null,

        @field:SerializedName("uuid")
        val uuid: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null
    ):Serializable

    data class File(

        @field:SerializedName("file_uuid")
        val fileUuid: String? = null,

        @field:SerializedName("url")
        val url: String? = null
    ):Serializable
}