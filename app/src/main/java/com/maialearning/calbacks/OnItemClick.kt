package com.maialearning.calbacks

interface OnItemClick {
    fun onClick(positiion:Int)
}
interface OnItemClickDelete: OnItemClickId {
    fun onDelete(positiion:Int)
}

interface OnItemClickType: OnItemClick {
    fun onClickOpt(positiion:Int, type:String)
}

interface OnItemClickId {
    fun onClick(positiion:Int,id:String)
}