package com.maialearning.model

data class TeacherListModel(
	val teacherListModel: List<TeacherListModelItem?>? = null
)

data class TeacherListModelItem(
	val uid: String? = null,
	val usersName: String? = null,
	val schoolNid: String? = null,
	val nid: String? = null,
	val lastName: String? = null,
	val schoolName: String? = null,
	val firstName: String? = null
)

