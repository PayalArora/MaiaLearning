package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CourseModelOptionDetailResponse(

	@field:SerializedName("fees_funding")
	val feesFunding: FeesFunding? = null,

	@field:SerializedName("pubukprn")
	val pubukprn: String? = null,

	@field:SerializedName("basic_info")
	val basicInfo: BasicInfo? = null,

	@field:SerializedName("entry_requirement")
	val entryRequirement: EntryRequirement? = null,

	@field:SerializedName("how_to_apply")
	val howToApply: HowToApply? = null,

	@field:SerializedName("kis_course_id")
	val kisCourseId: Any? = null,

	@field:SerializedName("kis_mode")
	val kisMode: Any? = null,

	@field:SerializedName("statistics_data")
	val statisticsData: Any? = null,

	@field:SerializedName("course_contact")
	val courseContact: CourseContact? = null,

	@field:SerializedName("provider_info")
	val providerInfo: ProviderInfo? = null
)

data class BasicInfo(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("course_id")
	val courseId: String? = null,

	@field:SerializedName("qualification")
	val qualification: String? = null,

	@field:SerializedName("college_name")
	val collegeName: String? = null,

	@field:SerializedName("academic_year")
	val academicYear: String? = null,

	@field:SerializedName("course_name")
	val courseName: String? = null,

	@field:SerializedName("course_option_id")
	val courseOptionId: String? = null,

	@field:SerializedName("study_mode")
	val studyMode: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)

data class FeesFunding(

	@field:SerializedName("tuition_fees")
	val tuitionFees: List<TuitionFeesItem?>? = null
)

data class ProviderInfo(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("college_name")
	val collegeName: String? = null,

	@field:SerializedName("address3")
	val address3: String? = null,

	@field:SerializedName("address2")
	val address2: String? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("address4")
	val address4: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null
)

data class CourseContact(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class QualificationTableItem(

	@field:SerializedName("MinEntry")
	val minEntry: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("FurtherDetails")
	val furtherDetails: String? = null
)

data class TuitionFeesItem(

	@field:SerializedName("Locale")
	val locale: String? = null,

	@field:SerializedName("CourseFees")
	val courseFees: String? = null,

	@field:SerializedName("FeeDurationPeriod")
	val feeDurationPeriod: String? = null
)

data class HowToApply(

	@field:SerializedName("location_name")
	val locationName: String? = null,

	@field:SerializedName("course_code")
	val courseCode: String? = null,

	@field:SerializedName("entry_points")
	val entryPoints: List<String?>? = null,

	@field:SerializedName("application_deadline")
	val applicationDeadline: String? = null,

	@field:SerializedName("provider_code")
	val providerCode: String? = null
)

data class EntryRequirement(

	@field:SerializedName("qualification_table")
	val qualificationTable: List<QualificationTableItem?>? = null
)
