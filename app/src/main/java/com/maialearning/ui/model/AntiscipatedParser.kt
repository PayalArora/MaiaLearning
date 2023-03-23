package com.maialearning.ui.model

import com.maialearning.model.AnticipatedKeyVal
import com.maialearning.model.AntiscipatedModel
import com.maialearning.util.checkNonNull
import org.json.JSONObject

class AntiscipatedParser(val json: JSONObject) {
    fun parse():Pair<AntiscipatedModel,AntiscipatedModel>{
        val antiscipatedModel=  AntiscipatedModel()
        val actualModel=  AntiscipatedModel()
        val jsonArray = json.optJSONArray("college_cost_compare")
        jsonArray?.let {
            if (it.length()>0){
                val obj = jsonArray.optJSONObject(0)

                antiscipatedModel.nid = obj.optString("nid")
                antiscipatedModel.name = obj.optString("name")
                actualModel.nid = obj.optString("nid")
                actualModel.name = obj.optString("name")
                val x = obj?.keys() as Iterator<String>
                val arr = arrayListOf<AntiscipatedModel.CollegeCostCompareItem>()
                val arractual = arrayListOf<AntiscipatedModel.CollegeCostCompareItem>()
                while (x.hasNext()){
                    val key: String = x.next()
                    var value = ""
                    if (key != "name" && key != "nid" && key!="iped_id"){
                        val yobj =  obj.optJSONObject(key)
                        val subKey = yobj?.keys() as Iterator<String>
                        val arrDynamic = arrayListOf<AnticipatedKeyVal?>()
                        val arrDynamicActual = arrayListOf<AnticipatedKeyVal?>()
                        while (subKey.hasNext()){
                            val subkey: String? = subKey.next()
                            if (checkNonNull(subkey)) {
                                var keyDisplay:String = subkey!!
                                var type:Int = 0
                                var unit:String = ""

                                when(key){
                                    "admission"->{
                                        value = "Admission"
                                    }
                                    "cost"->{
                                        value = "Costs"
                                    }
                                    "anticipated_college_funding"->{
                                        value = "Anticipated College Funding"
                                    }
                                    "financial_aid_statistics"->{
                                        value = "Financial Aid Statistics"
                                    }
                                    "projection"->{
                                        value = "Projection"
                                    }
                                    "financial_aid_package"->{
                                        value = "Financial Aid Package Details"
                                    }
                                    "college_funding"->{
                                        value = "College Funding"
                                    }
                                }
                                if (key == "admission" || key == "cost"||  key == "anticipated_college_funding"||key == "financial_aid_statistics"||key == "projection"|| key == "financial_aid_package") {

                                    when (subkey) {
                                        "no_of_applicants" -> {
                                            keyDisplay = "Number of Applicants"
                                            type = 3
                                            unit = ""
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "acceptance_rate" -> {
                                            keyDisplay = "Acceptance Rate"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_admitted_enrolled" -> {
                                            keyDisplay = "Percent Admitted Enrolled"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "ungrad_enrollments" -> {
                                            keyDisplay = "Undergraduate Enrollment"
                                            type = 3
                                            unit = ""
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "tuition_fees" -> {
                                            keyDisplay = "Tuition & Fees"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "room_board" -> {
                                            keyDisplay = "Room & Board"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "book_supplies" -> {
                                            keyDisplay = "Books & Supplies"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "additional_expenses" -> {
                                            keyDisplay = "Additional Expenses"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "total_cost" -> {
                                            keyDisplay = "Total Costs"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "efc" -> {
                                            keyDisplay = "Estimated Family Contribution (EFC)"
                                            type = 0
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "need" -> {
                                            keyDisplay = "Anticipated Need"
                                            type = 0
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_need_met" -> {
                                            keyDisplay = "Anticipated % Need Met"
                                            type = 0
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "financial_aid_package" -> {
                                            keyDisplay =
                                                "Anticipated Need Based Financial Aid Package"
                                            type = 0
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "unmet_need" -> {
                                            keyDisplay = "Anticipated Unmet Need (Gap)"
                                            type = 0
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "out_of_pocket" -> {
                                            keyDisplay = "Anticipated Out of Pocket"
                                            type = 0
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "avg_financial_aid_award" -> {
                                            keyDisplay = "Average Financial Aid Award"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "avg_net_price" -> {
                                            keyDisplay = "Average Net Price"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_student_need_fully_met" -> {
                                            keyDisplay = "Percentage of students with need fully met"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "avg_per_need_met" -> {
                                            keyDisplay = "Average Percent of Need Met"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_student_receiving_grant" -> {
                                            keyDisplay = "Percentage of students receiving institutional grant/scholarship"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "per_student_receiving_pell_grant" -> {
                                            keyDisplay = "Percentage of students receiving Pell Grant"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_student_receiving_federal_loans" -> {
                                            keyDisplay = "Percentage of students receiving Federal Student Loan"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "4yr_grad_rate" -> {
                                            keyDisplay = "4 Year Graduation Rate"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "4yr_cost_attend" -> {
                                            keyDisplay = "4 Year Cost of Attendance"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "4yr_out_of_pocket"->{
                                            keyDisplay = "4 Year Out of Pocket"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "6yr_grad_rate" -> {
                                            keyDisplay = "6 Year Graduation Rate"
                                            type = 3
                                            unit = "%"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "6yr_cost_attend" -> {
                                            keyDisplay = "6 Year Cost of Attendance"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "6yr_out_of_pocket"->{
                                            keyDisplay = "6 Year Year Out of Pocket"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "total_grant_awarded" -> {
                                            keyDisplay = "Total Grants Awarded"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "total_scholarship_awarded"->{
                                            keyDisplay = "Total Scholarships Awarded"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }

                                        "total_student_load_offered" -> {
                                            keyDisplay = "Total Student Loans offered"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "work_study"->{
                                            keyDisplay = "Work Study"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "other" -> {
                                            keyDisplay = "Other"
                                            type = 1
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "total_financial_aid" -> {
                                            keyDisplay = "6 Year Cost of Attendance"
                                            type = 3
                                            unit = "$"
                                            if (type ==0 || type ==3)
                                                arrDynamic.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))
                                            if (type == 1 || type ==3)
                                                arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                    }
                                }
                                if (key == "college_funding"){
                                    when(subkey){
                                        "efc" -> {
                                            keyDisplay = "Estimated Family Contribution (EFC)"
                                            type = 1
                                            unit = "$"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "need" -> {
                                            keyDisplay = "Need"
                                            type = 1
                                            unit = "$"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "per_need_met" -> {
                                            keyDisplay = "% Need Met"
                                            type = 1
                                            unit = "%"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "financial_aid_package" -> {
                                            keyDisplay =
                                                "Financial Aid Package Offered"
                                            type = 1
                                            unit = "$"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "unmet_need" -> {
                                            keyDisplay = "Unmet Need (Gap)"
                                            type = 1
                                            unit = "$"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                        "out_of_pocket" -> {
                                            keyDisplay = "Out of Pocket"
                                            type = 1
                                            unit = "$"
                                            arrDynamicActual.add(AnticipatedKeyVal(subkey!!, yobj.optString(subkey), keyDisplay,unit, type))

                                        }
                                    }
                                }
                            }
                        }
                        if (arrDynamic.size>0) {
                            val collgCompar =
                                AntiscipatedModel.CollegeCostCompareItem(key, value , arrDynamic)
                            arr.add(collgCompar)
                        }
                        if (arrDynamicActual.size>0) {
                            val collgComparActual =
                                AntiscipatedModel.CollegeCostCompareItem(key,value , arrDynamicActual)
                            arractual.add(collgComparActual)
                        }
                    }
                }
                antiscipatedModel.collegeCostCompare = arr
                actualModel.collegeCostCompare = arractual
            }
        }
        return  Pair(antiscipatedModel,actualModel)
    }

}