package com.maialearning.model

class UpdateUserData {

    var userdata: UserData = UserData()
}

class UserData {
    lateinit var nick_name: String
    lateinit var middle_name: String
    lateinit var last_name: String
    lateinit var first_name: String
    lateinit var gap_year_note: String
    lateinit var country: String
    lateinit var administrative_area: String
    lateinit var gap_year: String
    var ethnicity: ArrayList<String?>? = null
    lateinit var gender: String
    lateinit var application_year: String
    lateinit var grad_year: String
    var citizenship: ArrayList<String?>? = null
    lateinit var mail: String
    lateinit var uid: String
    lateinit var dob: String
    var phone_number: PhoneNumber = PhoneNumber()
    lateinit var thoroughfare: String
    lateinit var premise: String
    lateinit var postal_code: String
    lateinit var locality: String

    class PhoneNumber {
        lateinit var phone_number: String
        lateinit var country_code: String
    }
}