package com.maialearning.model

class SearchkeywordRequestModel {
    lateinit var  onet_code:ArrayList<String>
}
class BrightLook {
    lateinit var  bo_key:String
    var  pager: Int = 0
}
class CareerListModel {
    lateinit var  onet_code:ArrayList<String>
    var  onet_year: Int = 0
}
class SaveCountryModel {
    lateinit var  country_code:String
    var  user_id: String = ""
}