package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("student_uid")
	val studentUid: String? = null,

	@field:SerializedName("college_cost_compare")
	val collegeCostCompare: List<CollegeCostCompareItem?>? = null
)

data class CollegeCostCompareItem(

	@field:SerializedName("cost")
	val cost: Cost? = null,

	@field:SerializedName("financial_aid_statistics")
	val financialAidStatistics: FinancialAidStatistics? = null,

	@field:SerializedName("financial_aid_package")
	val financialAidPackage: FinancialAidPackage? = null,

	@field:SerializedName("college_funding")
	val collegeFunding: CollegeFunding? = null,

	@field:SerializedName("anticipated_college_funding")
	val anticipatedCollegeFunding: AnticipatedCollegeFunding? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nid")
	val nid: Int? = null,

	@field:SerializedName("admission")
	val admission: Admission? = null,

	@field:SerializedName("projection")
	val projection: Projection? = null,

	@field:SerializedName("iped_id")
	val ipedId: String? = null
)

data class Admission(

	@field:SerializedName("no_of_applicants")
	val noOfApplicants: Any? = null,

	@field:SerializedName("ungrad_enrollments")
	val ungradEnrollments: Any? = null,

	@field:SerializedName("per_admitted_enrolled")
	val perAdmittedEnrolled: Any? = null,

	@field:SerializedName("acceptance_rate")
	val acceptanceRate: Any? = null
)

data class Cost(

	@field:SerializedName("total_cost")
	val totalCost: Any? = null,

	@field:SerializedName("tuition_fees")
	val tuitionFees: Any? = null,

	@field:SerializedName("additional_expenses")
	val additionalExpenses: Any? = null,

	@field:SerializedName("room_board")
	val roomBoard: Any? = null,

	@field:SerializedName("book_supplies")
	val bookSupplies: Any? = null
)

data class Projection(

	@field:SerializedName("4yr_grad_rate")
	val jsonMember4yrGradRate: Any? = null,

	@field:SerializedName("6yr_grad_rate")
	val jsonMember6yrGradRate: Any? = null,

	@field:SerializedName("4yr_cost_attend")
	val jsonMember4yrCostAttend: Any? = null,

	@field:SerializedName("6yr_out_of_pocket")
	val jsonMember6yrOutOfPocket: Any? = null,

	@field:SerializedName("6yr_cost_attend")
	val jsonMember6yrCostAttend: Any? = null,

	@field:SerializedName("4yr_out_of_pocket")
	val jsonMember4yrOutOfPocket: Any? = null
)

data class CollegeFunding(

	@field:SerializedName("out_of_pocket")
	val outOfPocket: Any? = null,

	@field:SerializedName("efc")
	val efc: Any? = null,

	@field:SerializedName("need")
	val need: Any? = null,

	@field:SerializedName("financial_aid_package")
	val financialAidPackage: Int? = null,

	@field:SerializedName("unmet_need")
	val unmetNeed: Any? = null,

	@field:SerializedName("per_need_met")
	val perNeedMet: Any? = null
)

data class FinancialAidStatistics(

	@field:SerializedName("avg_net_price")
	val avgNetPrice: Any? = null,

	@field:SerializedName("per_student_receiving_pell_grant")
	val perStudentReceivingPellGrant: Any? = null,

	@field:SerializedName("per_student_receiving_grant")
	val perStudentReceivingGrant: Any? = null,

	@field:SerializedName("per_student_receiving_federal_loans")
	val perStudentReceivingFederalLoans: Any? = null,

	@field:SerializedName("avg_financial_aid_award")
	val avgFinancialAidAward: Any? = null,

	@field:SerializedName("per_student_need_fully_met")
	val perStudentNeedFullyMet: Any? = null,

	@field:SerializedName("avg_per_need_met")
	val avgPerNeedMet: Boolean? = null
)

data class AnticipatedCollegeFunding(

	@field:SerializedName("out_of_pocket")
	val outOfPocket: Any? = null,

	@field:SerializedName("efc")
	val efc: Any? = null,

	@field:SerializedName("need")
	val need: Any? = null,

	@field:SerializedName("financial_aid_package")
	val financialAidPackage: Any? = null,

	@field:SerializedName("unmet_need")
	val unmetNeed: Any? = null,

	@field:SerializedName("per_need_met")
	val perNeedMet: Any? = null
)

data class FinancialAidPackage(

	@field:SerializedName("total_financial_aid")
	val totalFinancialAid: Int? = null,

	@field:SerializedName("total_student_load_offered")
	val totalStudentLoadOffered: Any? = null,

	@field:SerializedName("other")
	val other: Int? = null,

	@field:SerializedName("total_scholarship_awarded")
	val totalScholarshipAwarded: Any? = null,

	@field:SerializedName("total_grant_awarded")
	val totalGrantAwarded: Any? = null,

	@field:SerializedName("work_study")
	val workStudy: Any? = null
)
