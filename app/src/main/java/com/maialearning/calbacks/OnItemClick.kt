package com.maialearning.calbacks

interface OnItemClick {
    fun onClick(positiion:Int)
}
interface OnItemClickDelete: OnItemClick {
    fun onDelete(positiion:Int)
}

interface OnItemClickType: OnItemClick {
    fun onClickOpt(positiion:Int, type:String)
}

