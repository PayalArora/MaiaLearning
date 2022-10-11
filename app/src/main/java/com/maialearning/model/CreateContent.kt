package com.maialearning.model

class CreateContent {
    lateinit var type: String
    var title: String? =null
    lateinit var uid: String
    var status: Int = 0
    lateinit var field_code: FieldCode
}
data class UndItem(
    var value: String? = null
)

data class FieldCode(
    var und: List<UndItem>? = null
)
