package com.maialearning.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var passVisible : Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicklisteners()
    }

    private fun clicklisteners() {
        binding.langSelector.setOnClickListener { bottomSheetWork() }

        binding.eyeBtn.setOnClickListener { if(!passVisible) {
            binding.eyeBtn.setImageDrawable(getDrawable(R.drawable.close_eye))
            passVisible=true
            binding.passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            binding.eyeBtn.setImageDrawable(getDrawable(R.drawable.open_eye))
            passVisible=false
            binding.passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
        }
        binding.loginBtn.setOnClickListener { loginWork() }
        binding.forgotPass.setOnClickListener {
            binding.loginLay.visibility = View.GONE
            binding.backBtn.visibility = View.VISIBLE
            binding.forgotLay.visibility = View.VISIBLE
            binding.privacyPolicy.visibility=View.GONE

        }
        binding.backBtn.setOnClickListener {
            binding.loginLay.visibility = View.VISIBLE
            binding.forgotLay.visibility = View.GONE
            binding.privacyPolicy.visibility=View.VISIBLE

        }
        binding.reqBtn.setOnClickListener {
            if (binding.reqBtn.text.equals(getString(R.string.request_pass))) {
                if (binding.reqEmail.getText().isBlank() || !isEmailIdValid(
                        binding.reqEmail.getText().toString()
                    )
                ) {
                    binding.reqEmail.setError(getString(R.string.err_invalid_email_id))
                } else {
                    binding.reqEmail.visibility = View.GONE
                    binding.tickLay.visibility=View.VISIBLE
                    binding.reqEmail.setText("")
                    binding.backBtn.visibility=View.GONE
                    binding.reqPass.setText(getString(R.string.pass_sent))
                    binding.reqBtn.setText(getString(R.string.back_to_login))
                }

            } else {
                binding.loginLay.visibility = View.VISIBLE
                binding.forgotLay.visibility = View.GONE
                binding.reqEmail.visibility = View.VISIBLE
                binding.reqEmail.setText("")
                binding.emailEdt.setText("")
                binding.passwordEdt.setText("")
                binding.tickLay.visibility=View.GONE
                binding.privacyPolicy.visibility=View.VISIBLE
                binding.backBtn.visibility=View.VISIBLE
                binding.reqPass.setText(getString(R.string.request_pass))
                binding.reqBtn.setText(getString(R.string.request_pass))
            }
        }

    }

    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
       // val bottomSheetBehavior = BottomSheetBehavior.from(layout)

      //  bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
        //view.minimumHeight = ViewGroup.LayoutParams.MATCH_PARENT
       view.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
//setupFullHeight(layout)

//        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(view: View, i: Int) {
//                if (BottomSheetBehavior.STATE_HIDDEN == i) {
//                    dialog.dismiss()
//                }
//            }

//            override fun onSlide(view: View, v: Float) {
//            }
//        })

        val btnClose = view.findViewById<RelativeLayout>(R.id.close)
        val text = view.findViewById<RelativeLayout>(R.id.eng_lang)
        val text1 = view.findViewById<RelativeLayout>(R.id.spanish_lang)
        val usTick = view.findViewById<RelativeLayout>(R.id.us_tick)
        val spanTick = view.findViewById<RelativeLayout>(R.id.span_tick)
        if( binding.selectedLang.text==getString(R.string.spanish))
        {
            usTick.visibility=View.GONE
            spanTick.visibility=View.VISIBLE
        }else{
            usTick.visibility=View.VISIBLE
            spanTick.visibility=View.GONE
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        text.setOnClickListener {
            binding.selectedLang.text=getString(R.string.english)
            dialog.dismiss()
            usTick.visibility=View.VISIBLE
            spanTick.visibility=View.GONE

        }
        text1.setOnClickListener {
            binding.selectedLang.text=getString(R.string.spanish)
            dialog.dismiss()
            usTick.visibility=View.GONE
            spanTick.visibility=View.VISIBLE

        }

        dialog.setContentView(view)
        dialog.show()    }

    private fun loginWork() {
        if (isInputValid()) {
          //  loginWithUserIdPassword()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun isInputValid(): Boolean {
        if (binding.emailEdt.getText().isBlank() || !isEmailIdValid(
                binding.emailEdt.getText().toString()
            )
        ) {
            binding.emailEdt.setError(getString(R.string.err_invalid_email_id))
            return false
        } else if (binding.passwordEdt.getText().isBlank()) {
            binding.passwordEdt.setError(getString(R.string.err_invalid_password))
            return false
        }
        return true
    }

    fun isEmailIdValid(emailId: String): Boolean {
        val regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(emailId)
        return matcher.find()
    }


    fun AppCompatActivity.showToast(msg: String?) {
        msg?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun loginWithUserIdPassword() {
        showToast("Coming Soon")
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}