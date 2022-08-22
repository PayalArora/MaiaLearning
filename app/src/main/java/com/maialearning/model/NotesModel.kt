package com.maialearning.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NotesModel : ArrayList<NotesModel.NotesModelItem>(){
    data class NotesModelItem(
        @SerializedName("note_nid")
        var noteNid: String,
        @SerializedName("note_created_by")
        var noteCreatedBy: String,
        @SerializedName("note_creation_date")
        var noteCreationDate: String,
        @SerializedName("note_title")
        var noteTitle: String,
        @SerializedName("note_description")
        var noteDescription: String,
        @SerializedName("file_id")
        var fileId: Any,
        @SerializedName("filename")
        var filename: String,
        @SerializedName("author_uid")
        var authorUid: String,
        @SerializedName("author_name")
        var authorName: String,
        @SerializedName("marked_read")
        var markedRead: String
    ):Serializable
}