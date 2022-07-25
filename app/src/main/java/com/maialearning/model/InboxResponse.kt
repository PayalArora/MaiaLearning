package com.maialearning.model

data class InboxResponse(
	val item: Item? = null,
	val folderName: String? = null,
	val success: Boolean? = null,
){
	data class Item(
		val isLastPage: Boolean? = null,
		val count: Int? = null,
		val totalPages: Int? = null,
		val messages: List<MessagesItem?>? = null,
		val currentPage: Int? = null
	)
		data class MessagesItem(
			val senderName: String? = null,
			val subject: String? = null,
			val sentTimestamp: String? = null,
			val messageId: String? = null,
			val shortMessageBody: String? = null,
			val isRead: Int? = null
		)

}





