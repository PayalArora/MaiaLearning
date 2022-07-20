package com.maialearning.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileViewpagerBinding
import com.maialearning.model.*
import com.maialearning.ui.activity.LoginActivity
import com.maialearning.ui.adapter.CitizenshipAdapter
import com.maialearning.ui.adapter.EthnicityAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.ProfileViewModel
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class ProfileFragment(val viewModel: ProfileViewModel) : Fragment(), OnItemClick {

    private lateinit var mBinding: ProfileViewpagerBinding
    var dialog: BottomSheetDialog? = null
    private lateinit var progress: Dialog
    private var profileResponse: ProfileResponse = ProfileResponse()
    private var ethinicityList = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = ProfileViewpagerBinding.inflate(inflater, container, false)
        // profileModel = ViewModelProvider.get(viewLifecycleOwner,ProfileViewModel::class.java)
        profileWork()
        progress = showLoadingDialog(requireContext())
        return mBinding.root
    }

    private fun profileWork() {
        viewModel.observer.observe(requireActivity()) {
            profileResponse = it
            setData(it)
        }
        viewModel.updateObserver.observe(viewLifecycleOwner) {
            Log.e("Response", "" + it.toString())
            progress.dismiss()
            SharedHelper(requireContext()).authkey?.let {
                SharedHelper(requireContext()).id?.let { it1 ->
                    viewModel.getProfile("Bearer $it", it1)
                    dialog?.dismiss()
                    progress.dismiss()
                }
            }
        }
        viewModel.showError.observe(viewLifecycleOwner) {
            progress.dismiss()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(it: ProfileResponse?) {
        mBinding.emailTxt.text = it?.info?.mail
        mBinding.phoneTxt.text = it?.info?.primaryPhone
        mBinding.addTxt.text = it?.info?.citizenship?.let { it1 ->
            android.text.TextUtils.join(
                ",",
                it1
            )
        }
        mBinding.idTxt.text = it?.info?.studentNumber
        mBinding.stateidTxt.text = it?.info?.studentStateId
        mBinding.yearTxt.text = it?.info?.gradYear
        mBinding.dobTxt.text = it?.info?.dob
        mBinding.appYearTxt.text = it?.info?.applicationYear
        mBinding.addressTxt.text = it?.info?.administrativeArea
        if (it?.info?.gender == ("F"))
            mBinding.genderTxt.text = "Female"
        else if (it?.info?.gender == ("M"))
            mBinding.genderTxt.text = "Male"
        else
            mBinding.genderTxt.text = "Non-Binary"

        if (it?.info?.gapYear == ("1"))
            mBinding.gapTxt.text = Html.fromHtml(it.info.gapYearNote)
        else
            mBinding.gapTxt.text = "No gap year"

        mBinding.schoolAddTxt.text = it?.schoolInfo?.district
        mBinding.schoolNameTxt.text = it?.schoolInfo?.schoolName
        mBinding.fullAddressTxt.text =
            it?.schoolInfo?.address?.addressLine1 + "," + it?.schoolInfo?.address?.addressLine2
        mBinding.addressTwo.text =
            it?.schoolInfo?.address?.city + "-" + it?.schoolInfo?.address?.zipcode + ", " + it?.schoolInfo?.address?.state
        mBinding.ceebTxt.text = it?.schoolInfo?.ceebCode
        ethinicityList.clear()
        for (i in it?.info?.ethnicity?.indices!!) {
            it.info.ethnicity[i]?.name?.let { it1 -> ethinicityList.add(it1) }
        }

        mBinding.ethincityTxt.text = ethinicityList.let {
            android.text.TextUtils.join(
                ",",
                ethinicityList
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.emailLay.setOnClickListener {
            showSheet("mail")
        }
        mBinding.phoneLay.setOnClickListener {
            showSheet("phone")
        }
        mBinding.dobLay.setOnClickListener {
//            showSheet("dob")
        }
        mBinding.citizenLay.setOnClickListener {
            showSheet("citizenship")
        }
        mBinding.gradYear.setOnClickListener {
            showSheet("grad_year")
        }
        mBinding.applicationYear.setOnClickListener {
            showSheet("application_year")
        }
        mBinding.genderLay.setOnClickListener {
            showSheet("gender")
        }
        mBinding.ethnicityLay.setOnClickListener {
            showSheet("ethnicity")
        }
        mBinding.countryLay.setOnClickListener {
            showSheet("country")
        }
        mBinding.logoutLay.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(
                    "Yes"
                ) { dialog, _ -> logout() }
                .setNegativeButton("No", null)
                .show()
        }
        mBinding.gapYear.setOnClickListener {
            showSheet("gap_year")
        }

    }

    private fun showSheet(s: String) {
        dialog = BottomSheetDialog(requireContext())
        val sheetBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog?.setContentView(sheetBinding.root)
        dialog?.show()
        clickListener(s, sheetBinding, dialog!!)
    }

    //    {"userdata":{"uid":"9375","first_name":"st1003(Test)","last_name":"last nametest","phone_number":{"phone_number":"16164336312","country_code":"1"},"mail":"st1003@mailinator.com","secondary_email":null,"gender":"F","country":"US","administrative_area":"ID","locality":"","postal_code":"","thoroughfare":"address1","premise":"","middle_name":null,"nick_name":null,"citizenship":[],"grad_year":2021,"application_year":"2022","grade":"12","gap_year":"0","gap_year_note":"","efc":null,"state_of_residency":null,"race":[],"ethnicity":["9"]}}
    @SuppressLint("NotifyDataSetChanged")
    private fun clickListener(
        key: String,
        sheetBinding: PrimaryEmailSheetBinding,
        dialog: BottomSheetDialog
    ) {
        var citizens: ArrayList<String?>? = ArrayList()
        var ethinicitiesList = arrayListOf<EthnicityResponseItem?>()

        var country = "Select country"
        var administrative_area = "Select state"


        when (key) {
            "mail" -> {
                sheetBinding.emailEdt.setText(profileResponse.info?.mail)
            }
            "phone" -> {
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.filters.text = requireActivity().getString(R.string.primary_phone)
                sheetBinding.phoneLay.visibility = View.VISIBLE
                sheetBinding.ccp.registerCarrierNumberEditText(sheetBinding.editTextCarrierNumber)
                sheetBinding.editTextCarrierNumber.setText(profileResponse.info?.primaryPhone)
            }
            "dob" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.birthday)
                sheetBinding.emailEdt.setText(profileResponse.info?.dob)
                sheetBinding.emailEdt.setOnClickListener {
                    showDatePicker(sheetBinding.emailEdt)
                }
            }
            "citizenship" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.citizenship)
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.citizenShip.visibility = View.VISIBLE
                sheetBinding.addMore.visibility = View.VISIBLE


                if (profileResponse.info?.citizenship != null && profileResponse.info?.citizenship!!.size > 0) {
                    citizens = profileResponse.info?.citizenship
                    sheetBinding.citizenShip.adapter =
                        CitizenshipAdapter(
                            citizens, this
                        )
                } else {
                    sheetBinding.citizenShip.adapter =
                        CitizenshipAdapter(
                            citizens, this
                        )
                }
                sheetBinding.addMore.setOnClickListener {
                    citizens?.add("")
                    sheetBinding.citizenShip.adapter?.notifyDataSetChanged()
                }
            }
            "grad_year" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.class_of)
                sheetBinding.emailEdt.inputType = InputType.TYPE_CLASS_PHONE
                sheetBinding.emailEdt.setText(profileResponse.info?.gradYear)
            }
            "gap_year" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.gap_year)
                sheetBinding.emailEdt.setText(Html.fromHtml(profileResponse.info?.gapYearNote))
                sheetBinding.emailEdt.hint = requireActivity().getString(R.string.gap_year)
            }
            "application_year" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.app_year)
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.spinnerLay.visibility = View.VISIBLE
                var startingYear = Calendar.getInstance().get(Calendar.YEAR)
                if (profileResponse.info?.gradYear != null)
                    startingYear = profileResponse.info?.gradYear!!.toInt()
                val array = arrayOf("", "", "", "", "", "", "")
                array[0] = "Select application year"
                array[1] = "" + startingYear
                for (i in 2 until 7) {
                    startingYear += 1
                    array[i] = "" + startingYear
                }
                val adapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, array
                )
                sheetBinding.spinner.adapter = adapter
            }
            "gender" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.gender)
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.spinnerLay.visibility = View.VISIBLE
                val array = arrayOf("Select Gender", "Female", "Male", "Non-Binary")
                val adapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, array
                )
                sheetBinding.spinner.adapter = adapter
            }
            "ethnicity" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.ethincity)
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.spinnerLay.visibility = View.GONE
                sheetBinding.citizenShip.visibility = View.VISIBLE
                progress.show()
                SharedHelper(requireContext()).authkey?.let {
                    SharedHelper(requireContext()).ethnicityTarget?.let { it1 ->
                        viewModel.getEthnicity(
                            "Bearer $it",
                            it1
                        )
                    }
                }
                viewModel.ethnicityObserver.observe(viewLifecycleOwner) {
                    Log.e("Response: ", " " + it?.size)
                    progress.dismiss()
                    if (it != null) {
                        ethinicitiesList = it
                        sheetBinding.citizenShip.adapter =
                            EthnicityAdapter(ethinicitiesList, ethinicityList, ::ethinicityClick)
                    }
                }
            }
            "country" -> {
                sheetBinding.filters.text =
                    requireActivity().getString(R.string.administrative_area)
                sheetBinding.spinnerLay.visibility = View.VISIBLE
                sheetBinding.secondSpinnerLay.visibility = View.VISIBLE
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.addressLay.visibility = View.VISIBLE
                sheetBinding.countryTxt.visibility = View.VISIBLE
                sheetBinding.stateTxt.visibility = View.VISIBLE
                sheetBinding.addressEdt.setText(profileResponse.info?.thoroughfare)
                sheetBinding.address2Edt.setText(profileResponse.info?.premise)
                sheetBinding.cityEdt.setText(profileResponse.info?.locality)
                sheetBinding.postalcodeEdt.setText(profileResponse.info?.postalCode)
                progress.show()
                SharedHelper(requireContext()).authkey?.let { viewModel.getCountries("Bearer $it") }
                val countries = arrayListOf<CountryData>()
                var states = arrayListOf<CountryData>()
                countries.add(CountryData("Select country", "Select country"))
                states.add(CountryData("Select state", "Select state"))
                val adapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, countries
                )
                val stateAdapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, states
                )
                sheetBinding.spinner.adapter = adapter
                sheetBinding.secondSpinner.adapter = stateAdapter

                viewModel.countryObserver.observe(viewLifecycleOwner) {
                    Log.e("Response: ", " $it")
                    progress.dismiss()
                    val iter: Iterator<String> = it.keySet().iterator()

                    while (iter.hasNext()) {
                        val key = iter.next()
                        try {
                            val countryData =
                                CountryData(key, it.get(key).toString().replace("\"", ""))
                            countries.add(countryData)
                        } catch (e: JSONException) {
                            // Something went wrong!
                        }
                    }
                    sheetBinding.spinner.adapter = adapter
                    if (profileResponse.info?.country != null) {
                        for (i in countries.indices) {
                            if (profileResponse.info?.country == countries.get(i).name) {
                                sheetBinding.spinner.setSelection(i)
                            }
                        }
                    }
                }
                /*SharedHelper(requireContext()).authkey?.let {
                    viewModel.getStates(
                        "Bearer " + it,
                        "US"
                    )
                }*/


                viewModel.stateObserver.observe(viewLifecycleOwner) {
                    val iter: Iterator<String> = it.keySet().iterator()
                    states = arrayListOf()
                    progress.dismiss()
                    states.add(CountryData("Select state", "Select state"))
                    while (iter.hasNext()) {
                        val key = iter.next()
                        try {
                            val countryData =
                                CountryData(key, it.get(key).toString().replace("\"", ""))
                            states.add(countryData)
                        } catch (e: JSONException) {
                            // Something went wrong!
                        }
                    }
                    val stateAdapter = ArrayAdapter(
                        sheetBinding.root.context,
                        R.layout.spinner_text, states
                    )
                    sheetBinding.secondSpinner.adapter = stateAdapter
                }

                sheetBinding.spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position != 0) {
                            country = countries[position].key
                            progress.show()
                            SharedHelper(requireContext()).authkey?.let {
                                viewModel.getStates(
                                    "Bearer $it",
                                    country
                                )
                            }

                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }

                }

                sheetBinding.secondSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position != 0) {
                            administrative_area = states[position].key
                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }
                }
            }
        }
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        sheetBinding.saveBtn.setOnClickListener {
            val updateUserData = UpdateUserData()
            updateUserData.userdata.uid = SharedHelper(requireContext()).id.toString()
            if (key == ("mail")) {
//                sheetBinding.emailEdt.setText(mBinding.emailTxt.text)
                if (sheetBinding.emailEdt.text
                        .isBlank() || !isEmailIdValid(sheetBinding.emailEdt.text.toString())
                ) {
                    sheetBinding.emailEdt.error =
                        requireContext().getString(R.string.err_invalid_email_id)
                    return@setOnClickListener

                } else {
                    updateUserData.userdata.mail = sheetBinding.emailEdt.text.toString()
                }
            } else if (key == "phone") {
                if (sheetBinding.editTextCarrierNumber.text
                        .isBlank() || sheetBinding.editTextCarrierNumber.text
                        .toString().length < 6
                ) {
                    sheetBinding.editTextCarrierNumber.error =
                        requireContext().getString(R.string.err_invalid_cellphone)
                    return@setOnClickListener

                }
                updateUserData.userdata.phone_number.phone_number =
                    sheetBinding.editTextCarrierNumber.text.toString()
                updateUserData.userdata.phone_number.country_code =
                    sheetBinding.ccp.selectedCountryCode
            } else if (key == "dob") {
                if (sheetBinding.emailEdt.text.isBlank()
                ) {
                    sheetBinding.emailEdt.error = requireContext().getString(R.string.err_dob)
                    return@setOnClickListener
                }
                updateUserData.userdata.dob =
                    sheetBinding.emailEdt.text.toString()
            } else if (key == "citizenship") {
                if (citizens != null) {
                    for (i in citizens.indices) {
                        if (citizens[i]!!.isEmpty()) {
                            citizens.removeAt(i)
                        }
                    }
                }
                updateUserData.userdata.citizenship =
                    citizens
            } else if (key == ("grad_year")) {
                if (sheetBinding.emailEdt.text.isBlank()
                ) {
                    sheetBinding.emailEdt.error = requireContext().getString(R.string.err_classof)
                    return@setOnClickListener
                }
                updateUserData.userdata.grad_year =
                    sheetBinding.emailEdt.text.toString()
            } else if (key == ("gap_year")) {
                if (sheetBinding.emailEdt.text.isBlank()
                ) {
                    sheetBinding.emailEdt.error = requireContext().getString(R.string.err_gapyear)
                    return@setOnClickListener
                }
                if (sheetBinding.emailEdt.text.isNotBlank()) {
                    val encoder: Base64.Encoder =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Base64.getEncoder()
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                    val encoded: String = encoder.encodeToString(
                        sheetBinding.emailEdt.text.toString().toByteArray()
                    )

                    updateUserData.userdata.gap_year_note =
                        encoded
                }
                updateUserData.userdata.gap_year =
                    "1"
            } else if (key == ("application_year")) {
                if (sheetBinding.spinner.selectedItem == ("Select application year")) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_application_year),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                updateUserData.userdata.application_year =
                    sheetBinding.spinner.selectedItem.toString()
            } else if (key == ("gender")) {
                when {
                    sheetBinding.spinner.selectedItem == ("Select Gender") -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.err_gender),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    sheetBinding.spinner.selectedItem.toString() == ("Female") -> updateUserData.userdata.gender =
                        "F"
                    sheetBinding.spinner.selectedItem.toString() == ("Male") -> updateUserData.userdata.gender =
                        "M"
                    sheetBinding.spinner.selectedItem.toString() == ("Non-Binary") -> updateUserData.userdata.gender =
                        "X"
                }
            } else if (key == ("ethnicity")) {
                for (i in ethinicitiesList.indices) {
                    if (ethinicityList.contains(ethinicitiesList[i]?.name)) {
                        citizens?.add(ethinicitiesList[i]?.id)
                    }
                }
                if (citizens == null || citizens.size == 0) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_ethnicity),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                updateUserData.userdata.ethnicity =
                    citizens
            } else if (key == ("country")) {
                when {
                    sheetBinding.spinner.selectedItem == ("Select country") -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.err_country),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    sheetBinding.secondSpinner.selectedItem == ("Select state") -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.err_state),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    else -> {
                        updateUserData.userdata.country =
                            country
                        updateUserData.userdata.administrative_area =
                            administrative_area
                        updateUserData.userdata.thoroughfare =
                            sheetBinding.addressEdt.text.toString()
                        updateUserData.userdata.premise = sheetBinding.address2Edt.text.toString()
                        updateUserData.userdata.postal_code =
                            sheetBinding.postalcodeEdt.text.toString()
                        updateUserData.userdata.locality = sheetBinding.cityEdt.text.toString()
                    }
                }
            }
            progress.show()
            updateEmail(updateUserData)
        }
    }

    private fun showDatePicker(emailEdt: EditText) {

        val date =
            OnDateSetListener { _, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel(emailEdt)
            }
        DatePickerDialog(
            requireActivity(),
            date,
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        ).show()

    }

    private val myCalendar = Calendar.getInstance()

    private fun updateLabel(emailEdt: EditText) {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        emailEdt.setText(dateFormat.format(myCalendar.time))
    }

    private fun updateEmail(updateUserData: UpdateUserData) {
        SharedHelper(requireContext()).authkey?.let {
            viewModel.updateEmail("Bearer $it", updateUserData)
        }
    }

    private fun isEmailIdValid(emailId: String): Boolean {
        val regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(emailId)
        return matcher.find()
    }

    private fun logout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onClick(positiion: Int) {
        Log.e("pos", " >> $positiion")
    }

    private fun ethinicityClick(ethinicity: String, checked: Boolean) {
        if (checked) {
            if (!ethinicityList.contains(ethinicity))
                ethinicityList.add(ethinicity)
        } else
            ethinicityList.remove(ethinicity)
    }
}