package com.maialearning.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.maialearning.R

fun showLoadingDialog(context: Context): Dialog {
    val dialogView = View.inflate(context, R.layout.loader_dialog, null)
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(dialogView)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
//    var view=dialog.findViewById<LottieAnimationView>(R.id.anim_view)
    val window: Window = dialog.window!!
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//   view.apply {
//        playAnimation()
//        repeatCount = 20
//    }

    return dialog
}
