package com.maialearning.model

class FileUploadData {

    lateinit var fm_uid: String
    lateinit var nid: String
    lateinit var name: String
    lateinit var description: String
    lateinit var uri: String
    lateinit var size: String
    lateinit var mime: String
    var field_fm_visible_with_users: ArrayList<String?>? = null
    var field_fm_visible_with_groups: ArrayList<String?>? = null
    var field_fm_share_with_users: ArrayList<String?>? = null
    var field_fm_share_with_groups: ArrayList<String?>? = null
    var tags: ArrayList<String?>? = null
//    {
//        "field_fm_visible_with_users": [],
//        "field_fm_visible_with_groups": ["role:all_staff"],
//        "field_fm_share_with_users": [],
//        "field_fm_share_with_groups": [],
//        "fm_uid": "9375",
//        "name": "new.jpg",
//        "description": "test",
//        "uri": "YQlBxORGo6dW/9375/new.jpg",
//        "size": 9772,
//        "mime": "image/jpeg",
//        "tags": ["4", "5"],
//        "nid": "527614"
//    }
}