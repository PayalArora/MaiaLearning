package com.maialearning.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileViewpagerBinding
import com.maialearning.databinding.RicheditorBinding
import com.maialearning.model.*
import com.maialearning.ui.activity.LoginActivity
import com.maialearning.ui.adapter.CitizenshipAdapter
import com.maialearning.ui.adapter.EthnicityAdapter
import com.maialearning.ui.adapter.RaceAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.ProfileViewModel
import jp.wasabeef.richeditor.RichEditor
import jp.wasabeef.richeditor.RichEditor.OnTextChangeListener
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class ProfileFragment(val viewModel: ProfileViewModel) : Fragment(), OnItemClick, OnTextChangeListener {

    private lateinit var mBinding: ProfileViewpagerBinding
    var dialog: BottomSheetDialog? = null
    private lateinit var progress: Dialog
    private var profileResponse: ProfileResponse = ProfileResponse()
    private var ethinicityList = arrayListOf<String>()
    private var raceList = arrayListOf<String>()
    var usStates = arrayListOf<CountryData>()
    var gapText = ""
    lateinit var onTextChangeListener: OnTextChangeListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = ProfileViewpagerBinding.inflate(inflater, container, false)
        onTextChangeListener = this
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
        mBinding.secEmailTxt.text = it?.info?.secondaryEmail
        mBinding.nicknameTxt.text = it?.info?.nickName

        raceList.clear()
        for (i in it?.info?.race?.indices!!) {
            it.info.race[i]?.let { it1 -> raceList.add(it1.name.toString()) }
        }

        mBinding.raceTxt.text = raceList.let {
            android.text.TextUtils.join(
                ", ",
                raceList
            )
        }
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
        mBinding.addressTxt.text = it?.info?.address
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
            it?.schoolInfo?.address?.addressLine1 + "," + it?.schoolInfo?.address?.addressLine2 + "\n" + it?.schoolInfo?.address?.city + ", " + it?.schoolInfo?.address?.stateName + ", " + it?.schoolInfo?.address?.countryName
        mBinding.ceebTxt.text = it?.schoolInfo?.ceebCode
        ethinicityList.clear()
        for (i in it?.info?.ethnicity?.indices!!) {
            it.info.ethnicity[i]?.name?.let { it1 -> ethinicityList.add(it1) }
        }

        mBinding.ethincityTxt.text = ethinicityList.let {
            android.text.TextUtils.join(
                ", ",
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
        mBinding.raceLay.setOnClickListener {
            showSheet("race")
        }
        mBinding.countryLay.setOnClickListener {
            showSheet("country")
        }
        mBinding.secEmailLay.setOnClickListener {
            showSheet("secondary_email")
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
        sheetBinding.backTxt.setOnClickListener {
            dialog?.dismiss()
        }

        val mEditor = sheetBinding.gapEdt
        sheetBinding.gapEdt.apply { setEditorHeight(200);
            setEditorFontSize(14)
            setPadding(10, 10, 10, 10);
            setPlaceholder(requireContext().resources.getString(R.string.gap_year));
            setBackground(resources.getDrawable(R.drawable.white_rect_border));
        }

        setEditor(mEditor, sheetBinding.richeditor)

        clickListener(s, sheetBinding, dialog!!)
    }

    private fun setEditor(mEditor: RichEditor, richeditorBinding: RicheditorBinding) {
        richeditorBinding.actionUndo.setOnClickListener(View.OnClickListener { mEditor.undo() })

        richeditorBinding.actionRedo.setOnClickListener(View.OnClickListener { mEditor.redo() })

        richeditorBinding.actionBold.setOnClickListener(View.OnClickListener { mEditor.setBold() })

        richeditorBinding.actionItalic.setOnClickListener(View.OnClickListener { mEditor.setItalic() })

        richeditorBinding.actionSubscript.setOnClickListener(View.OnClickListener { mEditor.setSubscript() })

        richeditorBinding.actionSuperscript.setOnClickListener(View.OnClickListener { mEditor.setSuperscript() })

        richeditorBinding.actionStrikethrough.setOnClickListener(View.OnClickListener { mEditor.setStrikeThrough() })

        richeditorBinding.actionUnderline.setOnClickListener(View.OnClickListener { mEditor.setUnderline() })

        richeditorBinding.actionHeading1.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(1)
        })

        richeditorBinding.actionHeading2.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(2)
        })

        richeditorBinding.actionHeading3.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(3)
        })

        richeditorBinding.actionHeading4.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(4)
        })

        richeditorBinding.actionHeading5.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(5)
        })

        richeditorBinding.actionHeading6.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(6)
        })

        richeditorBinding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        richeditorBinding.actionBgColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        richeditorBinding.actionIndent.setOnClickListener(View.OnClickListener { mEditor.setIndent() })

        richeditorBinding.actionOutdent.setOnClickListener(View.OnClickListener { mEditor.setOutdent() })

        richeditorBinding.actionAlignLeft.setOnClickListener(View.OnClickListener { mEditor.setAlignLeft() })

        richeditorBinding.actionAlignCenter.setOnClickListener(View.OnClickListener { mEditor.setAlignCenter() })

        richeditorBinding.actionAlignRight.setOnClickListener(View.OnClickListener { mEditor.setAlignRight() })

        richeditorBinding.actionBlockquote.setOnClickListener(View.OnClickListener { mEditor.setBlockquote() })

        richeditorBinding.actionInsertBullets.setOnClickListener(View.OnClickListener { mEditor.setBullets() })

        richeditorBinding.actionInsertNumbers.setOnClickListener(View.OnClickListener { mEditor.setNumbers() })

        richeditorBinding.actionInsertCheckbox.setOnClickListener(View.OnClickListener { mEditor.insertTodo() })
    }

    //    {"userdata":{"uid":"9375","first_name":"st1003(Test)","last_name":"last nametest","phone_number":{"phone_number":"16164336312","country_code":"1"},"mail":"st1003@mailinator.com","secondary_email":null,"gender":"F","country":"US","administrative_area":"ID","locality":"","postal_code":"","thoroughfare":"address1","premise":"","middle_name":null,"nick_name":null,"citizenship":[],"grad_year":2021,"application_year":"2022","grade":"12","gap_year":"0","gap_year_note":"","efc":null,"state_of_residency":null,"race":[],"ethnicity":["9"]}}
    @SuppressLint("NotifyDataSetChanged")
    private fun clickListener(
        key: String,
        sheetBinding: PrimaryEmailSheetBinding,
        dialog: BottomSheetDialog,
    ) {
        var citizens: ArrayList<String?>? = ArrayList()
        var ethinicitiesList = arrayListOf<EthnicityResponseItem?>()
        var racesList = arrayListOf<RaceItem?>()

        var country = "Select country"
        var administrative_area = "Select state"
        var residence_area = "Select state"


        when (key) {
            "mail" -> {
                sheetBinding.emailEdt.setText(profileResponse.info?.mail)
            }
            "secondary_email" -> {
                sheetBinding.emailEdt.setText(profileResponse.info?.secondaryEmail)
                sheetBinding.filters.text = requireActivity().getString(R.string.secondary_email)
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
                sheetBinding.emailEdt.setFilters(arrayOf<InputFilter>(LengthFilter(4)))
                sheetBinding.emailEdt.setText(profileResponse.info?.gradYear)
            }
            "gap_year" -> {
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.layoutRich.visibility = View.VISIBLE
                sheetBinding.filters.text = requireActivity().getString(R.string.gap_year)
                //onTextChangeListener.onTextChange(profileResponse.info?.gapYearNote)
                sheetBinding.gapEdt.html = profileResponse.info?.gapYearNote
                //sheetBinding.gapEdt.setText(Html.fromHtml(profileResponse.info?.gapYearNote))
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
            "race" -> {
                sheetBinding.filters.text = requireActivity().getString(R.string.race)
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.spinnerLay.visibility = View.GONE
                sheetBinding.citizenShip.visibility = View.VISIBLE
                progress.show()
                SharedHelper(requireContext()).authkey?.let {
                    SharedHelper(requireContext()).ethnicityTarget?.let { it1 ->
                        viewModel.getRace(
                            "Bearer $it",
                            it1
                        )
                    }
                    viewModel.raceObserver.observe(viewLifecycleOwner) {
                        Log.e("Response: ", " " + it?.size)
                        progress.dismiss()
                        if (it != null) {
                            racesList = it
                            sheetBinding.citizenShip.adapter =
                                RaceAdapter(racesList, raceList, ::raceClick)
                        }
                    }
                }

            }
            "country" -> {
                sheetBinding.filters.text =
                    requireActivity().getString(R.string.address)
                sheetBinding.spinnerLay.visibility = View.VISIBLE
                sheetBinding.secondSpinnerLay.visibility = View.VISIBLE
                sheetBinding.emailEdt.visibility = View.GONE
                sheetBinding.addressLay.visibility = View.VISIBLE
                sheetBinding.countryTxt.visibility = View.VISIBLE
                sheetBinding.stateTxt.visibility = View.VISIBLE
                sheetBinding.residenceSpinnerLay.visibility = View.VISIBLE
                sheetBinding.addressEdt.setText(profileResponse.info?.thoroughfare)
                sheetBinding.address2Edt.setText(profileResponse.info?.premise)
                sheetBinding.cityEdt.setText(profileResponse.info?.locality)
                sheetBinding.postalcodeEdt.setText(profileResponse.info?.postalCode)

                progress.show()

                SharedHelper(requireContext()).authkey?.let { viewModel.getCountries("Bearer $it") }
                val countries = arrayListOf<CountryData>()
                var states = arrayListOf<CountryData>()

                countries.add(CountryData("Select country", "Select country"))
              //  states.add(CountryData("Select state", "Select state"))
                val adapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, countries
                )
                sheetBinding.spinner.adapter = adapter

                val stateAdapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, states
                )
                sheetBinding.secondSpinner.adapter = stateAdapter


                val resident = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, resources.getStringArray(R.array.residence)
                )
                sheetBinding.citizenSpinner.adapter = resident
                if (profileResponse.info?.stateOfResidency == null) {
                    sheetBinding.citizenSpinner.setSelection(0)
                } else if (profileResponse.info?.stateOfResidency == "not_state_resident") {
                    sheetBinding.citizenSpinner.setSelection(2)
                } else {
                    sheetBinding.citizenSpinner.setSelection(1)
                }
                sheetBinding.citizenSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long,
                    ) {
                        if (position == 1) {
                            SharedHelper(requireContext()).authkey?.let {
                                viewModel.getStates(
                                    "Bearer " + it,
                                    "US", 1
                                )
                            }
                            sheetBinding.stateResidenceSpinnerLay.visibility = View.VISIBLE
                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }

                }

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
                    var i = 1
                    var pos = 0
                    while (iter.hasNext()) {
                        val key = iter.next()
                        try {

                            val countryData =
                                CountryData(key, it.get(key).toString().replace("\"", ""))
                            if (profileResponse?.info?.administrativeAreaCode == key) {
                                pos = i
                            }
                            states.add(countryData)
                            i++;
                        } catch (e: JSONException) {
                            // Something went wrong!
                        }
                    }
                    val stateAdapter = ArrayAdapter(
                        sheetBinding.root.context,
                        R.layout.spinner_text, states
                    )
                    if (states.size == 1){
                        sheetBinding.secondSpinner.isClickable = false
                        sheetBinding.secondSpinner.setEnabled(false);
                        sheetBinding.secondSpinner.adapter = stateAdapter
                    } else {
                        sheetBinding.secondSpinner.setEnabled(true);
                        sheetBinding.secondSpinner.isClickable = true
                        sheetBinding.secondSpinner.adapter = stateAdapter
                    }
                    sheetBinding.secondSpinner.setSelection(pos)
                }
                viewModel.stateResidenceObserver.observe(viewLifecycleOwner) {
                    val iter: Iterator<String> = it.keySet().iterator()
                    usStates.add(CountryData("Select state", "Select state"))
                    progress.dismiss()
                    var i = 1
                    var pos = 0
                    while (iter.hasNext()) {
                        val key = iter.next()
                        try {
                            val countryData =
                                CountryData(key, it.get(key).toString().replace("\"", ""))
                            if (profileResponse?.info?.stateOfResidency == key) {
                                pos = i
                            }
                            usStates.add(countryData)
                            i++;
                        } catch (e: JSONException) {
                            // Something went wrong!
                        }
                    }
                    val stateAdapter = ArrayAdapter(
                        sheetBinding.root.context,
                        R.layout.spinner_text, usStates
                    )


                    sheetBinding.stateResidenceSpinner.adapter = stateAdapter
                    sheetBinding.stateResidenceSpinner.setSelection(pos)
                }

                sheetBinding.spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long,
                    ) {
                        if (position != 0) {
                            country = countries[position].key
                            progress.show()
                            SharedHelper(requireContext()).authkey?.let {
                                viewModel.getStates(
                                    "Bearer $it",
                                    country, 0
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
                        id: Long,
                    ) {
                        if (position != 0) {
                            administrative_area = states[position].key
                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }
                }

                sheetBinding.stateResidenceSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long,
                    ) {
                        if (position != 0) {
                            residence_area = usStates[position].key
                        }
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }
                }
            }
        }

        sheetBinding.gapEdt.setOnTextChangeListener(onTextChangeListener)
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
                if (gapText.isBlank()
                ) {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.err_gapyear), Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else  {
                    val encoder: Base64.Encoder =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Base64.getEncoder()
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                    val encoded: String = encoder.encodeToString(
                        gapText.toByteArray()
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
            } else if (key == ("race")) {
                for (i in racesList.indices) {
                    if (raceList.contains(racesList[i]?.name)) {
                        citizens?.add(racesList[i]?.id)
                    }
                }
                if (citizens == null || citizens.size == 0) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_race),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                updateUserData.userdata.race =
                    citizens
            } else if (key == ("secondary_email")) {
//                sheetBinding.emailEdt.setText(mBinding.emailTxt.text)
                if (sheetBinding.emailEdt.text
                        .isBlank() || !isEmailIdValid(sheetBinding.emailEdt.text.toString())
                ) {
                    sheetBinding.emailEdt.error =
                        requireContext().getString(R.string.err_invalid_email_id)
                    return@setOnClickListener

                } else {
                    updateUserData.userdata.secondary_email = sheetBinding.emailEdt.text.toString()
                }
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
                    sheetBinding.residenceSpinnerLay.isVisible && sheetBinding.stateResidenceSpinner.selectedItem == ("Select state") -> {
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
                        if (sheetBinding.citizenSpinner.selectedItemPosition == 0)
                            updateUserData.userdata.state_of_residency = null
                        else if (sheetBinding.citizenSpinner.selectedItemPosition == 1) {
                            updateUserData.userdata.state_of_residency = residence_area
                        } else if (sheetBinding.citizenSpinner.selectedItemPosition == 2) {
                            updateUserData.userdata.state_of_residency = "not_state_resident"
                        }
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
        Firebase.auth.signOut()
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

    private fun raceClick(ethinicity: String, checked: Boolean) {
        if (checked) {
            if (!raceList.contains(ethinicity))
                raceList.add(ethinicity)
        } else
            raceList.remove(ethinicity)
    }

    override fun onTextChange(text: String?) {
        if (text != null) {
            gapText =text
        }
    }
}