package com.maialearning.model

class SendMessageModel {
    var message: NewMessageModel = NewMessageModel()
}
class NewMessageModel{
    lateinit var senderUid:  String
    lateinit var messageStatus : String
    lateinit var messageBody : String
     var isReply: Int = 0
     var isForwad: Int = 0
     var isReplyAll: Int = 0
    lateinit var subject :  String
    lateinit var recipients:  ArrayList<HashMap<String, String>>
    lateinit var auditEntity:String
    lateinit var attachments: ArrayList<HashMap<String, String>>
}