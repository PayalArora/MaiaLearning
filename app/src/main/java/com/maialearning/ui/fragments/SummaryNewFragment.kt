package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.SummaryFactsheetLayoutBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.FactsheetCareerClusterAdapter
import com.maialearning.ui.adapter.FactsheetPathwayAdapter
import com.maialearning.ui.adapter.RelatedAdapter
import com.maialearning.ui.adapter.WorkActivityCareerAdapter
import com.maialearning.util.checkNonNull
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SummaryNewFragment(var response: CareerFactsheetResponse) : Fragment() {

    var dialog: BottomSheetDialog? = null
    private val careerViewModel: CareerViewModel by viewModel()

    private lateinit var mBinding: SummaryFactsheetLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = SummaryFactsheetLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkNonNull(response?.title)){
            mBinding.title.text= "What is ${response?.title}"
        }
        if (checkNonNull(response?.description)){
            mBinding.description.text= "What is ${response?.description}"
        }


        mBinding.careerClusterList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.careerClusterList.adapter = FactsheetCareerClusterAdapter(response.categories)

        mBinding.pathwayList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.pathwayList.adapter = FactsheetPathwayAdapter(response.pathways)

        mBinding.actView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.actView.adapter = WorkActivityCareerAdapter(response.workActivities as ArrayList<String>?)

        mBinding.relatedCareersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.relatedCareersList.adapter = RelatedAdapter(response.relatedCareers as ArrayList<CareerFactsheetResponse.RelatedCareersItem>?)
        mBinding.txtRegion.text = "${getString(R.string.default_selection)}${response.regionLevel}"
//
//        val arrayAct = ArrayList<WorkActivitiesItem?>()
//        arrayAct.addAll(response.workActivities!!)
//        mBinding.actView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.actView.adapter = WorkActivityAdapter(arrayAct, null, null, null, "work")
//
//        val acdeAct = ArrayList<AcademicKnowledgeItem?>()
//        acdeAct.addAll(response.academicKnowledge!!)
//        mBinding.acdeView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.acdeView.adapter = WorkActivityAdapter(null, acdeAct, null, null, "aca")
//
//        val skillAct = ArrayList<SkillItem?>()
//        skillAct.addAll(response.skill!!)
//        mBinding.skillsView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.skillsView.adapter = WorkActivityAdapter(null, null, skillAct, null, "skill")
//
//        val interAct = ArrayList<InterestItem?>()
//        interAct.addAll(response.interest!!)
//        mBinding.interView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.interView.adapter = WorkActivityAdapter(null, null, null, interAct, "inter")
//
//
//        mBinding.text2.text = response.salary
//        mBinding.knowText.text =
//            response.educationLevel?.stream()?.collect(Collectors.joining("\n-", "", ""))
//        val uri: Uri = Uri.parse("https://cdn.careeronestop.org/OccVids/OccupationVideos/${response.yearWiseCode?.jsonMember2019}.mp4")
//
//
//        val mediaController = MediaController(requireContext())
//        mediaController.setAnchorView(mBinding.videoView)
//        mediaController.setMediaPlayer(mBinding.videoView)
//        mBinding.videoView.setVideoURI(uri)
//        mBinding.videoView.setMediaController(mediaController)
//        mBinding.playBtn.setOnClickListener {
//            mBinding.playBtn.visibility=View.GONE
//            mBinding.videoView.requestFocus()
//            mBinding.videoView.start()
//        }
//        mBinding.videoView.setOnPreparedListener {
//            // do something when video is ready to play, you want to start playing video here
//
//        }
//
//
//        careerViewModel.getVideoCode(response.careeronestopVideo.toString())
//        careerViewModel.getVideoCodeObserver.observe(requireActivity()){
//            Log.e("Code"," "+it.toString())
//        }

    }

}
