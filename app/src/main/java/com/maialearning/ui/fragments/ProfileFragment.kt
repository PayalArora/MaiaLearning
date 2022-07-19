package com.maialearning.ui.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
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
import com.maialearning.model.CountryData
import com.maialearning.model.ProfileResponse
import com.maialearning.model.UpdateUserData
import com.maialearning.ui.activity.LoginActivity
import com.maialearning.ui.adapter.CitizenshipAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.ProfileViewModel
import okhttp3.internal.notify
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class ProfileFragment(val viewModel: ProfileViewModel) : Fragment(), OnItemClick {

    private lateinit var mBinding: ProfileViewpagerBinding
    var dialog: BottomSheetDialog? = null
    private lateinit var progress: Dialog
    var profileResponse: ProfileResponse = ProfileResponse()


    //    private val profileModel: ProfileViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ProfileViewpagerBinding.inflate(inflater, container, false)
        // profileModel = ViewModelProvider.get(viewLifecycleOwner,ProfileViewModel::class.java)
        profileWork()
        progress = showLoadingDialog(requireContext())
        return mBinding.root
    }

    private fun profileWork() {
        viewModel.observer.observe(requireActivity(), {
            profileResponse = it
            setData(it)
        })
        viewModel.updateObserver.observe(viewLifecycleOwner, {
            Log.e("Response", "" + it.toString())
            progress.dismiss()
            SharedHelper(requireContext()).authkey?.let {
                SharedHelper(requireContext()).id?.let { it1 ->
                    viewModel.getProfile("Bearer " + it, it1)
                    dialog?.dismiss()
                    progress.dismiss()
                }
            }
        })
        viewModel.showError.observe(viewLifecycleOwner) {
            progress.dismiss()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData(it: ProfileResponse?) {
        mBinding.emailTxt.setText(it?.info?.mail)
        mBinding.phoneTxt.setText(it?.info?.primaryPhone)
        mBinding.addTxt.setText(it?.info?.citizenship?.let { it1 ->
            android.text.TextUtils.join(
                ",",
                it1
            )
        })
        mBinding.idTxt.setText(it?.info?.studentNumber)
        mBinding.stateidTxt.setText(it?.info?.studentStateId)
        mBinding.yearTxt.setText(it?.info?.gradYear)
        mBinding.dobTxt.setText(it?.info?.dob)
        mBinding.appYearTxt.setText(it?.info?.applicationYear)
        mBinding.addressTxt.setText(it?.info?.administrativeArea)
        if (it?.info?.gender.equals("F"))
            mBinding.genderTxt.setText("Female")
        else
            mBinding.genderTxt.setText("Male")

        if (it?.info?.gapYear.equals("1"))
                mBinding.gapTxt.setText(Html.fromHtml(it?.info?.gapYearNote))
        else
            mBinding.gapTxt.setText("No gap year")

        mBinding.schoolAddTxt.setText(it?.schoolInfo?.district)
        mBinding.schoolNameTxt.setText(it?.schoolInfo?.schoolName)
        mBinding.fullAddressTxt.setText(it?.schoolInfo?.address?.addressLine1 + "," + it?.schoolInfo?.address?.addressLine2)
        mBinding.addressTwo.setText(it?.schoolInfo?.address?.city + "-" + it?.schoolInfo?.address?.zipcode + ", " + it?.schoolInfo?.address?.state)
        mBinding.ceebTxt.setText(it?.schoolInfo?.ceebCode)
        var ethnicityItem: ArrayList<String?>? = ArrayList()
        for (i in it?.info?.ethnicity?.indices!!) {
            ethnicityItem?.add(it?.info?.ethnicity?.get(i)?.name)
        }

        mBinding.ethincityTxt.setText(ethnicityItem?.let { it1 ->
            android.text.TextUtils.join(
                ",",
                ethnicityItem
            )
        })
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
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, which -> logout() })
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
    private fun clickListener(
        key: String,
        sheetBinding: PrimaryEmailSheetBinding,
        dialog: BottomSheetDialog
    ) {
        var citizens: ArrayList<String?>? = ArrayList()
        var country = "Select country"
        var administrative_area = "Select state"
        if (key.equals("mail")) {
            sheetBinding.emailEdt.setText(profileResponse.info?.mail)
        }

        if (key.equals("phone")) {
            sheetBinding.emailEdt.visibility = View.GONE
            sheetBinding.filters.setText(requireActivity().getString(R.string.primary_phone))
            sheetBinding.phoneLay.visibility = View.VISIBLE
            sheetBinding.ccp.registerCarrierNumberEditText(sheetBinding.editTextCarrierNumber);
            sheetBinding.editTextCarrierNumber.setText(profileResponse.info?.primaryPhone)
        } else if (key.equals("dob")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.birthday))
            sheetBinding.emailEdt.setText(profileResponse.info?.dob)
            sheetBinding.emailEdt.setOnClickListener {
                showDatePicker(sheetBinding.emailEdt)
            }
        } else if (key.equals("citizenship")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.citizenship))
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
        } else if (key.equals("grad_year")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.class_of))
            sheetBinding.emailEdt.setInputType(InputType.TYPE_CLASS_PHONE)
            sheetBinding.emailEdt.setText(profileResponse.info?.gradYear)
        } else if (key.equals("gap_year")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.gap_year))
                sheetBinding.emailEdt.setText(Html.fromHtml(profileResponse?.info?.gapYearNote))
            sheetBinding.emailEdt.setHint(requireActivity().getString(R.string.gap_year))
        } else if (key.equals("application_year")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.app_year))
            sheetBinding.emailEdt.visibility = View.GONE
            sheetBinding.spinnerLay.visibility = View.VISIBLE
            var startingYear = Calendar.getInstance().get(Calendar.YEAR);
            if (profileResponse.info?.gradYear != null)
                startingYear = profileResponse.info?.gradYear!!.toInt()
            val array = arrayOf("", "", "", "", "", "", "")
            array[0] = "Select application year"
            array[1] = "" + startingYear
            for (i in 2 until 7) {
                startingYear = startingYear + 1
                array[i] = "" + startingYear
            }
            val adapter = ArrayAdapter(
                sheetBinding.root.context,
                R.layout.spinner_text, array
            )
            sheetBinding.spinner.adapter = adapter
        } else if (key.equals("gender")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.gender))
            sheetBinding.emailEdt.visibility = View.GONE
            sheetBinding.spinnerLay.visibility = View.VISIBLE
            val array = arrayOf("Select Gender", "Female", "Male", "Non-Binary")
            val adapter = ArrayAdapter(
                sheetBinding.root.context,
                R.layout.spinner_text, array
            )
            sheetBinding.spinner.adapter = adapter
        } else if (key.equals("ethnicity")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.ethincity))
            sheetBinding.emailEdt.visibility = View.GONE
            sheetBinding.spinnerLay.visibility = View.GONE
            sheetBinding.ethnicityLay.visibility = View.VISIBLE
        } else if (key.equals("country")) {
            sheetBinding.filters.setText(requireActivity().getString(R.string.administrative_area))
            sheetBinding.spinnerLay.visibility = View.VISIBLE
            sheetBinding.secondSpinnerLay.visibility = View.VISIBLE
            sheetBinding.emailEdt.visibility = View.GONE
            SharedHelper(requireContext()).authkey?.let { viewModel.getCountries("Bearer " + it) }
            var countries = arrayListOf<CountryData>()
            var states = arrayListOf<CountryData>()
            countries?.add(CountryData("Select country", "Select country"))
            states?.add(CountryData("Select state", "Select state"))
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

            viewModel.countryObserver.observe(viewLifecycleOwner, {
                Log.e("Response: ", " " + it)
                val iter: Iterator<String> = it.keySet().iterator()

                while (iter.hasNext()) {
                    val key = iter.next()
                    try {
                        var countryData = CountryData(key, it.get(key).toString().replace("\"", ""))
                        countries?.add(countryData)
                    } catch (e: JSONException) {
                        // Something went wrong!
                    }
                }
                sheetBinding.spinner.adapter = adapter
            })
            /*SharedHelper(requireContext()).authkey?.let {
                viewModel.getStates(
                    "Bearer " + it,
                    "US"
                )
            }*/
            viewModel.stateObserver.observe(viewLifecycleOwner, {
                Log.e("Response: ", " " + it)
                val iter: Iterator<String> = it.keySet().iterator()
                states = arrayListOf()
                states?.add(CountryData("Select state", "Select state"))
                while (iter.hasNext()) {
                    val key = iter.next()
                    try {
                        var countryData = CountryData(key, it.get(key).toString().replace("\"", ""))
                        states?.add(countryData)
                    } catch (e: JSONException) {
                        // Something went wrong!
                    }
                }
                val stateAdapter = ArrayAdapter(
                    sheetBinding.root.context,
                    R.layout.spinner_text, states
                )
                sheetBinding.secondSpinner.adapter = stateAdapter
            })

            sheetBinding.spinner.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        country = countries.get(position).key
                        SharedHelper(requireContext()).authkey?.let {
                            viewModel.getStates(
                                "Bearer " + it,
                                country
                            )
                        }

                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }

            })

            sheetBinding.secondSpinner.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        administrative_area = states.get(position).key
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            })
        }
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        sheetBinding.saveBtn.setOnClickListener {
            var updateUserData = UpdateUserData()
            updateUserData.userdata.uid = SharedHelper(requireContext()).id.toString()
            if (key.equals("mail")) {
//                sheetBinding.emailEdt.setText(mBinding.emailTxt.text)
                if (sheetBinding.emailEdt.getText()
                        .isBlank() || !isEmailIdValid(sheetBinding.emailEdt.getText().toString())
                ) {
                    sheetBinding.emailEdt.setError(requireContext().getString(R.string.err_invalid_email_id))
                    return@setOnClickListener

                } else {
                    updateUserData.userdata.mail = sheetBinding.emailEdt.text.toString()
                }
            } else if (key.equals("phone")) {
                if (sheetBinding.editTextCarrierNumber.getText()
                        .isBlank() || sheetBinding.editTextCarrierNumber.getText()
                        .toString().length < 6
                ) {
                    sheetBinding.editTextCarrierNumber.setError(requireContext().getString(R.string.err_invalid_cellphone))
                    return@setOnClickListener

                }
                updateUserData.userdata.phone_number.phone_number =
                    sheetBinding.editTextCarrierNumber.getText().toString()
                updateUserData.userdata.phone_number.country_code =
                    sheetBinding.ccp.selectedCountryCode
            } else if (key.equals("dob")) {
                if (sheetBinding.emailEdt.getText().isBlank()
                ) {
                    sheetBinding.emailEdt.setError(requireContext().getString(R.string.err_dob))
                    return@setOnClickListener
                }
                updateUserData.userdata.dob =
                    sheetBinding.emailEdt.getText().toString()
            } else if (key.equals("citizenship")) {
                if (citizens != null) {
                    for (i in citizens.indices) {
                        if (citizens.get(i)!!.isEmpty()) {
                            citizens.removeAt(i)
                        }
                    }
                }
                updateUserData.userdata.citizenship =
                    citizens
            } else if (key.equals("grad_year")) {
                if (sheetBinding.emailEdt.getText().isBlank()
                ) {
                    sheetBinding.emailEdt.setError(requireContext().getString(R.string.err_classof))
                    return@setOnClickListener
                }
                updateUserData.userdata.grad_year =
                    sheetBinding.emailEdt.getText().toString()
            } else if (key.equals("gap_year")) {
                if (sheetBinding.emailEdt.getText().isBlank()
                ) {
                    sheetBinding.emailEdt.setError(requireContext().getString(R.string.err_gapyear))
                    return@setOnClickListener
                }
                if (!sheetBinding.emailEdt.getText().isBlank()) {
                    val encoder: Base64.Encoder =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Base64.getEncoder()
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                    val encoded: String = encoder.encodeToString(
                        sheetBinding.emailEdt.getText().toString().toByteArray()
                    )

                    updateUserData.userdata.gap_year_note =
                        encoded
                }
                updateUserData.userdata.gap_year =
                    "1"
            } else if (key.equals("application_year")) {
                if (sheetBinding.spinner.selectedItem.equals("Select application year")) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_application_year),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                updateUserData.userdata.application_year =
                    sheetBinding.spinner.selectedItem.toString()
            } else if (key.equals("gender")) {
                if (sheetBinding.spinner.selectedItem.equals("Select Gender")) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_gender),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (sheetBinding.spinner.selectedItem.toString().equals("Female"))
                    updateUserData.userdata.gender = "F"
                else if (sheetBinding.spinner.selectedItem.toString().equals("Male"))
                    updateUserData.userdata.gender = "M"
                else if (sheetBinding.spinner.selectedItem.toString().equals("Non-Binary"))
                    updateUserData.userdata.gender = "N"
            } else if (key.equals("ethnicity")) {
                if (sheetBinding.notHisponic.isChecked) {
                    citizens?.add("8")
                }
                if (sheetBinding.hisponic.isChecked) {
                    citizens?.add("9")
                }
                if (sheetBinding.unknown.isChecked) {
                    citizens?.add("10")
                }
                if (citizens?.size!! < 1) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_gender),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                updateUserData.userdata.ethnicity =
                    citizens
            } else if (key.equals("country")) {
                if (sheetBinding.spinner.selectedItem.equals("Select country")) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_country),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (sheetBinding.secondSpinner.selectedItem.equals("Select state")) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.err_state),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    updateUserData.userdata.country =
                        country
                    updateUserData.userdata.administrative_area =
                        administrative_area
                }
            }
            progress.show()
            updateEmail(updateUserData)
        }
    }

    private fun showDatePicker(emailEdt: EditText) {

        var date =
            OnDateSetListener { view, year, month, day ->
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

    val myCalendar = Calendar.getInstance()

    private fun updateLabel(emailEdt: EditText) {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        emailEdt.setText(dateFormat.format(myCalendar.time))
    }

    private fun updateEmail(updateUserData: UpdateUserData) {
        SharedHelper(requireContext()).authkey?.let {
            viewModel.updateEmail("Bearer " + it, updateUserData)
        }

    }

    fun isEmailIdValid(emailId: String): Boolean {
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
        Log.e("pos", " >> " + positiion)
    }


}