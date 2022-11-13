package com.maialearning.model

class UniversitySearchPayload {
    lateinit var country: String
    var region: ArrayList<String>? = null
    var state: ArrayList<String>? = null
    var public_private: ArrayList<String?>? = null
    var type_of_env: ArrayList<String?>? = null
    lateinit var ug_size: String
    lateinit var religious: String
    var selectivity: ArrayList<String>? = null
    lateinit var cipcode: String
    lateinit var athletic_associations: String
    lateinit var sports: String
    lateinit var sports_participants: String
    lateinit var special: String
    lateinit var campus_activities: String
    lateinit var sat_act_null: String
    lateinit var sat_ebrw_min: String
    lateinit var sat_ebrw_max: String
    lateinit var sat_math_min: String
    lateinit var sat_math_max: String
    lateinit var act_composite_min: String
    lateinit var act_composite_max: String
    var university_list: ArrayList<String>? = null
    lateinit var sort_order: String
    lateinit var sort_parameter: String
    lateinit var search: String
     var pager: Int = 0
    lateinit var student_uid: String

/*    {
        "country": "US",
        "region": [],
        "state": [],
        "2_4_year": [],
        "public_private": [],
        "type_of_env": [],
        "ug_size": [],
        "religious": null,
        "selectivity": [],
        "cipcode": [],
        "athletic_associations": null,
        "sports": null,
        "sports_participants": null,
        "special": null,
        "campus_activities": null,
        "sat_act_null": 0,
        "sat_ebrw_min": null,
        "sat_ebrw_max": null,
        "sat_math_min": null,
        "sat_math_max": null,
        "act_composite_min": null,
        "act_composite_max": null,
        "university_list": [],
        "sort_order": "asc",
        "sort_parameter": "college_name",
        "search": "",
        "pager": 1,
        "student_uid": "9375"
    }*/
}