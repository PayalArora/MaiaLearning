package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.R
import com.maialearning.databinding.LayoutTeacherBinding
import com.maialearning.databinding.RecommendationLayoutBinding
import com.maialearning.model.RecModel
import com.maialearning.model.RecomdersModel
import com.maialearning.model.TeacherListModelItem
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.parser.SearchParser
import com.maialearning.ui.adapter.RecommenderAdapter
import com.maialearning.ui.adapter.SelectTeacherAdapter
import com.maialearning.ui.adapter.UniFactAdapter
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.getDate
import com.maialearning.util.getDateTime
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.http.Field

class RecommendationFragment : Fragment(), onClick {
    private lateinit var mBinding: RecommendationLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var list: ArrayList<TeacherListModelItem> = ArrayList()
    var selectedList: ArrayList<TeacherListModelItem> = ArrayList()
    var selectedUcasList: ArrayList<TeacherListModelItem> = ArrayList()
    var jsonDeadline: JsonArray = JsonArray()
    lateinit var adapter: RecommenderAdapter
    var type_recs = REC_TYPE_RECOMENDATION
    var page: Int = 1
    var requestListUpdate: ArrayList<RecomdersModel.Data?>? = ArrayList()
    var requestList: ArrayList<RecomdersModel.Data?>? = ArrayList()
    private var isLoading = false
    var requestlistNew = ArrayList<RecomdersModel.Data?>()
    var isAttached = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = RecommendationLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAttached = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAttached)
        setListeners()
        progress = showLoadingDialog(requireContext())
        progress.show()
        SharedHelper(requireContext()).id?.let { homeModel.getTeachers(it) }
        showRecs()
        homeModel.getRecDeadline()
        mBinding.send.setOnClickListener {
            if (type_recs == REC_TYPE_RECOMENDATION) {
                sendRecomendation(type_recs)
            } else {
                sendRecomendation(type_recs)
                sendUCASRecomendation()
            }
        }
        mBinding.recipent.setOnClickListener {
            selectedList.clear()
            listTeacher(REC_TYPE_RECOMENDATION)
        }
        mBinding.ucasLetter.setOnClickListener {
            showRecs()
        }
        mBinding.recipentUcas.setOnClickListener {
            selectedUcasList.clear()
            listTeacher(REC_TYPE_UCAS)
        }
        progress.show()

        adapter = RecommenderAdapter(requireContext(), requestlistNew, this, mBinding.requestList)
        mBinding.requestList.adapter = adapter
        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                requestlistNew.add(null)
                isLoading = true
                adapter.notifyItemInserted(requestlistNew.size - 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    hitAPI(page.toString())

                }, 2000)
            }
        })
        hitAPI(page.toString())
    }

    fun hitAPI(page: String) {
        homeModel.getRecommenders(SharedHelper(requireContext()).id ?: "", page)
    }

    private fun sendUCASRecomendation() {
        val teacherId = arrayListOf<String>()
        for (i in selectedUcasList) {
            i.uid?.let { it1 -> teacherId.add(it1) }
        }
        if (mBinding.textDescription.text.toString().trim() != "" && teacherId.size
            > 0
        ) {
            progress.show()

            val recmodel = RecModel(
                mBinding.textDescription.text.toString(),
                SharedHelper(requireContext()).id ?: "",
                jsonDeadline.get(mBinding.typeValue.selectedItemPosition).asString,
                teacherId
            )
            homeModel.sendRecomUCAS(recmodel)
        }
    }

    private fun sendRecomendation(type: Int) {
        if (type == REC_TYPE_UCAS) {
            if (mBinding.textDescription.text.toString().trim() == "" || selectedUcasList.size
                == 0 || selectedList.size == 0
            ) {
                return
            }
        }
        val teacherId = arrayListOf<String>()
        for (i in selectedList) {
            i.uid?.let { it1 -> teacherId.add(it1) }
        }
        if (mBinding.textDescription.text.toString().trim() != "" && teacherId.size
            > 0
        ) {
            progress.show()

            val recmodel = RecModel(
                mBinding.textDescription.text.toString(),
                SharedHelper(requireContext()).id ?: "",
                jsonDeadline.get(mBinding.typeValue.selectedItemPosition).asString,
                teacherId
            )
            homeModel.sendRecomTeachers(recmodel)
        }

    }

    private fun showRecs() {
        if (mBinding.ucasLetter.isChecked) {
            type_recs = REC_TYPE_UCAS
            mBinding.recipentUcas.visibility = View.VISIBLE
        } else {
            type_recs = REC_TYPE_RECOMENDATION
            mBinding.recipentUcas.visibility = View.GONE
        }
    }

    private fun setListeners() {
        homeModel.applyingObserver.observe(requireActivity()) {
            progress.dismiss()
            for (i in 0 until it.size()) {
                val data = JSONObject(it.get(i).toString())
                val model = TeacherListModelItem(
                    data.getString("uid"),
                    data.getString("users_name"),
                    data.getString("school_nid"),
                    data.getString("nid"),
                    data.getString("last_name"),
                    data.getString("school_name"),
                    data.getString("first_name"),
                )
                list.add(model)
                val id = SharedHelper(requireContext()).schoolId
                list = list.filter { it.schoolNid == id } as ArrayList<TeacherListModelItem>
                list.sortBy { it.firstName?.capitalize() + " " + it.lastName?.capitalize() }

            }

        }
        homeModel.recDeadline.observe(requireActivity()) {
            jsonDeadline = it

            val array = arrayOfNulls<String>(it.size())

            for (i in 0 until it.size()) {
                array[i] = getDate(it[i].asLong, "MMM dd,yyyy")
            }
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_text, array
            )
            mBinding.typeValue.adapter = adapter

        }
        homeModel.recommdersObserver.observe(requireActivity()) {
            progress.dismiss()
            page = ((it.pager!!.current?.toInt() ?: 0) + 1)
            val totalPage = it.pager!!.last
            val last = it.pager!!.last
            progress.dismiss()
            requestList?.addAll(it.data)
            requestListUpdate?.addAll(it.data)
            if (isLoading) {
                isLoading = false
                requestlistNew.removeAt(requestlistNew.size - 1)
                adapter.notifyItemRemoved(requestlistNew.size)
            }
            //for swipe refresh page
            if (totalPage != null) {
                if (last != null) {
                    adapter.addAllLis(requestList!!, totalPage, last)
                }
            }
            adapter.setLoaded()

        }
        homeModel.recSendObserver.observe(requireActivity()) {
            progress.dismiss()
            Toast.makeText(requireContext(), it.get(0).asString, Toast.LENGTH_LONG).show()
        }
        homeModel.recUCASObserver.observe(requireActivity()) {
            progress.dismiss()
            Toast.makeText(requireContext(), it.get(0).asString, Toast.LENGTH_LONG).show()
        }
        homeModel.cancelRecommendRequestObserver.observe(requireActivity()) {
            progress.dismiss()
        }
    }

    private fun listTeacher(type: Int) {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutTeacherBinding = LayoutTeacherBinding.inflate(layoutInflater)
        //  sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.text = resources.getString(R.string.select_teachers)
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sheetBinding.recyclerView.adapter =
            SelectTeacherAdapter(list, ::click, type)
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                    val filterList =
                        findMemberByName(sheetBinding.searchText.text.toString().trim())
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(filterList, ::click, type)
                } else {
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(list, ::click, type)
                }

            }


        })
        sheetBinding.searchText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findMemberByName(sheetBinding.searchText.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
        dialog.setOnDismissListener {
            if (type == REC_TYPE_RECOMENDATION)
                setSelection(selectedList, type)
            else
                setSelection(selectedUcasList, type)
        }

    }

    fun findMemberByName(name: String?): ArrayList<TeacherListModelItem> {
        // go through list of members and compare name with given name
        var filterList: ArrayList<TeacherListModelItem> = ArrayList()
        for (member in list) {
            if ((member.firstName?.lowercase()?.contains(name?.lowercase() ?: "")
                    ?: false) || member.lastName?.lowercase()
                    ?.contains(name?.lowercase() ?: "") ?: false
            ) {
                filterList.add(member)
                // return member when name found
            }
        }
        return filterList
    }

    private fun click(data: TeacherListModelItem, type: Int) {
        if (type == REC_TYPE_RECOMENDATION)
            if (data.isSelected) {
                selectedList.add(data)
            } else {
                selectedList.remove(data)

            } else {
            if (data.isSelected) {
                selectedUcasList.add(data)
            } else {
                selectedUcasList.remove(data)
            }
        }
    }

    private fun setSelection(selectedList: ArrayList<TeacherListModelItem>, type: Int) {
        val separator = ", "

        val sb = StringBuilder()
        selectedList.forEach { sb.append(it.lastName).append(separator) }
        val string = sb.removeSuffix(separator).toString()

        if (type == REC_TYPE_RECOMENDATION)
            mBinding.selectedTeachers.text = string
        else
            mBinding.selectedTeachersUcas.text = string

        //  mBinding.textCount.text = "from " + selectedList.size + " teachers"
    }

    companion object {
        const val REC_TYPE_RECOMENDATION = 1
        const val REC_TYPE_UCAS = 2
    }

    override fun onCancelClick(data: RecomdersModel.Data?) {
        progress.show()
        homeModel.cancelRecommendRequest(data?.nid.toString())
    }

}

interface onClick {
    fun onCancelClick(data: RecomdersModel.Data?)
}
