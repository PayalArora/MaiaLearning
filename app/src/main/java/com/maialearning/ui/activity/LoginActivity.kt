package com.maialearning.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnSignInStartedListener
import com.maialearning.databinding.ActivityLoginBinding
import com.maialearning.factory.LoginViewModelFactory
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.LoginNewModel
import com.maialearning.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var passVisible: Boolean = false
    private var googleSignInAccount:GoogleSignInAccount?= null

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var viewModel: LoginViewModel
    private val loginModel: LoginNewModel by viewModel()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application
        val factory = LoginViewModelFactory(application, object : OnSignInStartedListener {
            override fun onSignInStarted(client: GoogleSignInClient?) {
                startActivityForResult(client?.signInIntent, RC_SIGN_IN)
            }
        })
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        dialog = showLoadingDialog(this)
        initObserver()
        clicklisteners()
    }

    private fun clicklisteners() {
        binding.langSelector.setOnClickListener { bottomSheetWork() }

        binding.eyeBtn.setOnClickListener {
            if (!passVisible) {
                binding.eyeBtn.setImageDrawable(getDrawable(R.drawable.close_eye))
                passVisible = true
                binding.passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                binding.eyeBtn.setImageDrawable(getDrawable(R.drawable.open_eye))
                passVisible = false
                binding.passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }
        binding.loginBtn.setOnClickListener {
            if (isInputValid()){
                dialog.show()
                loginModel.userLogin(this, binding.emailEdt.text.toString().trim(), binding.passwordEdt.text.toString().trim())
               // loginWork()
            }
        }
        binding.forgotPass.setOnClickListener {
            binding.loginLay.visibility = View.GONE
            binding.backBtn.visibility = View.VISIBLE
            binding.forgotLay.visibility = View.VISIBLE
            binding.privacyPolicy.visibility = View.GONE

        }
        binding.backBtn.setOnClickListener {
            binding.loginLay.visibility = View.VISIBLE
            binding.forgotLay.visibility = View.GONE
            binding.privacyPolicy.visibility = View.VISIBLE

        }
        binding.reqBtn.setOnClickListener {
            if (binding.reqBtn.text.equals(getString(R.string.request_pass))) {
                if (binding.reqEmail.getText().isBlank() || !isEmailIdValid(
                        binding.reqEmail.getText().toString()
                    )
                ) {
                    binding.reqEmail.setError(getString(R.string.err_invalid_email_id))
                } else {
                    dialog.show()
                    loginModel.forgetPassword(binding.emailEdt.text.toString())
                }

            } else {
                binding.loginLay.visibility = View.VISIBLE
                binding.forgotLay.visibility = View.GONE
                binding.reqEmail.visibility = View.VISIBLE
                binding.reqEmail.setText("")
                binding.emailEdt.setText("")
                binding.passwordEdt.setText("")
                binding.tickLay.visibility = View.GONE
                binding.privacyPolicy.visibility = View.VISIBLE
                binding.backBtn.visibility = View.VISIBLE
                binding.reqPass.setText(getString(R.string.request_pass))
                binding.reqBtn.setText(getString(R.string.request_pass))
            }
        }

        binding.googleLogin.setOnClickListener {
           viewModel.signIn()
        }
        binding.loginMicrosoft.setOnClickListener {
            // Microsoftt code refrance https://firebase.google.com/docs/auth/android/microsoft-oauth
            viewModel.signinToMicrosoft(this)
        }
        viewModel.currentUser.observe(this) {
            it?.let {
                viewModel.signOut()
                dialog.show()

                googleSignInAccount.let { it1 -> loginModel.googleLogin(it1?.email!!,it1.id!!,it1.idToken!!) }
              //  startActivity(Intent(this, DashboardActivity::class.java))
            }
        }
        viewModel.microUser.observe(this) {
            it?.let {
                viewModel.signOut()
                dialog.show()
              loginModel.microLogin(  it)
                //startActivity(Intent(this, DashboardActivity::class.java))
            }
        }
    loginModel.showLoading.observe(this){
        if (!it){
            dialog.dismiss()
        }
    }
    }

    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val btnClose = view.findViewById<RelativeLayout>(R.id.close)
        val text = view.findViewById<RelativeLayout>(R.id.eng_lang)
        val text1 = view.findViewById<RelativeLayout>(R.id.spanish_lang)
        val usTick = view.findViewById<RelativeLayout>(R.id.us_tick)
        val spanTick = view.findViewById<RelativeLayout>(R.id.span_tick)
        if (binding.selectedLang.text == getString(R.string.spanish)) {
            usTick.visibility = View.GONE
            spanTick.visibility = View.VISIBLE
        } else {
            usTick.visibility = View.VISIBLE
            spanTick.visibility = View.GONE
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        text.setOnClickListener {
            binding.selectedLang.text = getString(R.string.english)
            dialog.dismiss()
            usTick.visibility = View.VISIBLE
            spanTick.visibility = View.GONE

        }
        text1.setOnClickListener {
            binding.selectedLang.text = getString(R.string.spanish)
            dialog.dismiss()
            usTick.visibility = View.GONE
            spanTick.visibility = View.VISIBLE

        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun loginWork() {
//        if (isInputValid()) {
            //  loginWithUserIdPassword()
            startActivity(Intent(this, DashboardActivity::class.java))
//        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                googleSignInAccount = account
                viewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObserver() {
        loginModel.loginObserver.observe(this) {
            it?.let {
                dialog.dismiss()
                println("Name "+it?.userName)
                SharedHelper(this).authkey=it.accessToken
                it.user.let {
                    it?.messageId.let {
                        if (it != null) {
                            SharedHelper(this).messageId= it
                        }
                    }
                }

                if(binding.rememberMe.isChecked)
                SharedHelper(this).login = "1"
                SharedHelper(this).id=it.user?.uid
                SharedHelper(this).ethnicityTarget=it.user?.ogUserNode?.und?.get(0)?.targetId

                loginWork()
            }
        }
        loginModel.forgetObserver.observe(this) {
            it?.let {
                dialog.dismiss()
                binding.reqEmail.visibility = View.GONE
                binding.tickLay.visibility = View.VISIBLE
                binding.reqEmail.setText("")
                binding.backBtn.visibility = View.GONE
                binding.reqPass.setText(getString(R.string.pass_sent))
                binding.reqBtn.setText(getString(R.string.back_to_login))
            }
        }
        loginModel.showError.observe(this) {
            dialog.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}