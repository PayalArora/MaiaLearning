package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("assigned_counselors")
	val assignedCounselors: List<AssignedCounselorsItem?>? = null,

	@field:SerializedName("allowed_values")
	val allowedValues: AllowedValues? = null,

	@field:SerializedName("parent")
	val parent: List<ParentItem?>? = null,

	@field:SerializedName("school_info")
	val schoolInfo: SchoolInfo? = null,

	@field:SerializedName("unread_notes")
	val unreadNotes: Int? = null,

	@field:SerializedName("info")
	val info: Info? = null
)

data class Info(

	@field:SerializedName("created_by_file")
	val createdByFile: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("invalid_tp_tokens")
	val invalidTpTokens: List<Any?>? = null,

	@field:SerializedName("mail")
	val mail: String? = null,

	@field:SerializedName("email_enabled")
	val emailEnabled: Int? = null,

	@field:SerializedName("sms_enabled")
	val smsEnabled: Int? = null,

	@field:SerializedName("receive_sms_from_other")
	val receiveSmsFromOther: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("secondary_email")
	val secondaryEmail: String? = null,

	@field:SerializedName("thoroughfare")
	val thoroughfare: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("primary_phone")
	val primaryPhone: String? = null,

	@field:SerializedName("student_state_id")
	val studentStateId: String? = null,

	@field:SerializedName("premise")
	val premise: String? = null,

	@field:SerializedName("zoom_pmi")
	val zoomPmi: Any? = null,

	@field:SerializedName("profile_pic_thumbnail")
	val profilePicThumbnail: String? = null,

	@field:SerializedName("created_by_transcript")
	val createdByTranscript: String? = null,

	@field:SerializedName("local_id")
	val localId: String? = null,

	@field:SerializedName("last_login")
	val lastLogin: String? = null,

	@field:SerializedName("message_center_uid")
	val messageCenterUid: String? = null,

	@field:SerializedName("locality")
	val locality: String? = null,

	@field:SerializedName("grad_year")
	val gradYear: String? = null,

	@field:SerializedName("created_by")
	val createdBy: String? = null,

	@field:SerializedName("new_phone_number_format")
	val newPhoneNumberFormat: Int? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("administrative_area_code")
	val administrativeAreaCode: String? = null,

	@field:SerializedName("gap_year_note")
	val gapYearNote: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("nick_name")
	val nickName: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("zoom_enabled")
	val zoomEnabled: Int? = null,

	@field:SerializedName("salutation")
	val salutation: String? = null,

	@field:SerializedName("application_year")
	val applicationYear: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("ethnicity")
	val ethnicity: List<EthnicityItem?>? = null,

	@field:SerializedName("administrative_area")
	val administrativeArea: String? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("receive_sms")
	val receiveSms: String? = null,

	@field:SerializedName("preferred_langcode")
	val preferredLangcode: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("graduation_date")
	val graduationDate: String? = null,

	@field:SerializedName("gap_year")
	val gapYear: String? = null,

	@field:SerializedName("efc")
	val efc: Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("race")
	val race: List<Any?>? = null,

	@field:SerializedName("citizenship")
	var citizenship: ArrayList<String?>? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("middle_name")
	val middleName: String? = null,

	@field:SerializedName("field_email_receive")
	val fieldEmailReceive: Int? = null,

	@field:SerializedName("state_of_residency")
	val stateOfResidency: Any? = null,

	@field:SerializedName("student_number")
	val studentNumber: String? = null,

	@field:SerializedName("phone_carrier")
	val phoneCarrier: Any? = null,

	@field:SerializedName("enrollmentstartdate")
	val enrollmentstartdate: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class SchoolInfo(

	@field:SerializedName("common_app_send")
	val commonAppSend: String? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("student_dob_show")
	val studentDobShow: String? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("school_hash")
	val schoolHash: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("school_name")
	val schoolName: String? = null,

	@field:SerializedName("ceeb_code")
	val ceebCode: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null
)

data class Address(

	@field:SerializedName("zipcode")
	val zipcode: Any? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("address_line2")
	val addressLine2: String? = null,

	@field:SerializedName("address_line1")
	val addressLine1: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("state_name")
	val stateName: String? = null,

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("administrative_area_code")
	val administrativeAreaCode: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("premise")
	val premise: String? = null,

	@field:SerializedName("locality")
	val locality: String? = null,

	@field:SerializedName("administrative_area")
	val administrativeArea: Any? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null,

	@field:SerializedName("thoroughfare")
	val thoroughfare: String? = null
)

data class AllowedValues(

	@field:SerializedName("phone_carrier")
	val phoneCarrier: List<String?>? = null
)

data class AssignedCounselorsItem(

	@field:SerializedName("profile_pic_thumbnail")
	val profilePicThumbnail: Any? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("teacher_email")
	val teacherEmail: String? = null,

	@field:SerializedName("roles")
	val roles: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("teacher_nid")
	val teacherNid: String? = null
)

data class EthnicityItem(

	@field:SerializedName("default")
	val jsonMemberDefault: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nid")
	val nid: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class ParentItem(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("message_center_id")
	val messageCenterId: String? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("billing_contact")
	val billingContact: Int? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("parent_nid")
	val parentNid: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("connected_date")
	val connectedDate: String? = null
)
