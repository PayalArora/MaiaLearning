package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class NYSCareerResponse(

	@field:SerializedName("personal_academic_areas")
	val personalAcademicAreas: List<PersonalAcademicAreasItem?>? = null,

	@field:SerializedName("culminating_activity")
	val culminatingActivity: List<Any?>? = null,

	@field:SerializedName("student_last_name")
	val studentLastName: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("student_id")
	val studentId: String? = null,

	@field:SerializedName("schools_experience")
	val schoolsExperience: List<SchoolsExperienceItem?>? = null,

	@field:SerializedName("student_first_name")
	val studentFirstName: String? = null,

	@field:SerializedName("career_interests")
	val careerInterests: List<Any?>? = null,

	@field:SerializedName("abilities")
	val abilities: List<AbilitiesItem?>? = null,

	@field:SerializedName("skills")
	val skills: List<SkillsItem?>? = null,

	@field:SerializedName("employability_profile_fid")
	val employabilityProfileFid: String? = null,

	@field:SerializedName("work_experiences")
	val workExperiences: List<Any?>? = null,

	@field:SerializedName("employability_profile_format")
	val employabilityProfileFormat: String? = null,

	@field:SerializedName("school")
	val school: String? = null,

	@field:SerializedName("student_career_review")
	val studentCareerReview: List<StudentCareerReviewItem?>? = null,

	@field:SerializedName("future_goals")
	val futureGoals: List<Any?>? = null,

	@field:SerializedName("interests")
	val interests: List<InterestsItem?>? = null
)

data class StudentCareerReviewItem(

	@field:SerializedName("teacher_review_date")
	val teacherReviewDate: String? = null,

	@field:SerializedName("review_date")
	val reviewDate: String? = null,

	@field:SerializedName("parent_review_date")
	val parentReviewDate: Any? = null,

	@field:SerializedName("review_by_teacher")
	val reviewByTeacher: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("review_by_student")
	val reviewByStudent: String? = null,

	@field:SerializedName("review_by_counselor")
	val reviewByCounselor: Any? = null,

	@field:SerializedName("counselor_review_date")
	val counselorReviewDate: Any? = null,

	@field:SerializedName("review_by_parent")
	val reviewByParent: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null
)

data class AbilitiesItem(

	@field:SerializedName("personal_abilities")
	val personalAbilities: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("career_areas_abilities")
	val careerAreasAbilities: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null
)

data class InterestsItem(

	@field:SerializedName("academic_interests")
	val academicInterests: String? = null,

	@field:SerializedName("work_preference")
	val workPreference: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null,

	@field:SerializedName("personal_interests")
	val personalInterests: String? = null
)

data class SchoolsExperienceItem(

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("skills_acquired_school_exp")
	val skillsAcquiredSchoolExp: String? = null,

	@field:SerializedName("school_community_experince")
	val schoolCommunityExperince: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null
)

data class SkillsItem(

	@field:SerializedName("beginning_skill")
	val beginningSkill: String? = null,

	@field:SerializedName("decription")
	val decription: String? = null,

	@field:SerializedName("experiences_activities")
	val experiencesActivities: String? = null,

	@field:SerializedName("final_skill")
	val finalSkill: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null
)

data class PersonalAcademicAreasItem(

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("need_strengthen")
	val needStrengthen: String? = null,

	@field:SerializedName("steps_to_strengthen_areas")
	val stepsToStrengthenAreas: String? = null,

	@field:SerializedName("cid")
	val cid: String? = null
)
