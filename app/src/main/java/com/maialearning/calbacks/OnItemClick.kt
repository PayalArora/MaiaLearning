package com.maialearning.calbacks

interface OnItemClick {
    fun onClick(positiion:Int)
}
interface OnItemClickDelete: OnItemClick {
    fun onDelete(positiion:Int)
}