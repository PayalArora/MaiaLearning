package com.maialearning.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.databinding.SummaryTraficBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.TraficSubAdapter
import com.maialearning.ui.adapter.WorkActivityAdapter
import java.util.stream.Collectors


class SummaryFragment(var response: SelectedCareerResponse) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SummaryTraficBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = SummaryTraficBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = ArrayList<String?>()
        array.addAll(response.responsibilities!!)
        mBinding.progressView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.progressView.adapter = TraficSubAdapter(array)

        val arrayAct = ArrayList<WorkActivitiesItem?>()
        arrayAct.addAll(response.workActivities!!)
        mBinding.actView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.actView.adapter = WorkActivityAdapter(arrayAct, null, null, null, "work")

        val acdeAct = ArrayList<AcademicKnowledgeItem?>()
        acdeAct.addAll(response.academicKnowledge!!)
        mBinding.acdeView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.acdeView.adapter = WorkActivityAdapter(null, acdeAct, null, null, "aca")

        val skillAct = ArrayList<SkillItem?>()
        skillAct.addAll(response.skill!!)
        mBinding.skillsView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.skillsView.adapter = WorkActivityAdapter(null, null, skillAct, null, "skill")

        val interAct = ArrayList<InterestItem?>()
        interAct.addAll(response.interest!!)
        mBinding.interView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.interView.adapter = WorkActivityAdapter(null, null, null, interAct, "inter")


        mBinding.text2.text = response.salary
        mBinding.knowText.text =
            response.educationLevel?.stream()?.collect(Collectors.joining("\n-", "", ""))
        val uri: Uri = Uri.parse(response.careeronestopVideo)

        mBinding.videoView.setVideoURI(uri)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(mBinding.videoView)
        mediaController.setMediaPlayer(mBinding.videoView)
        mBinding.videoView.setMediaController(mediaController)

        mBinding.videoView.setOnPreparedListener {
            // do something when video is ready to play, you want to start playing video here
            mBinding.playBtn.setOnClickListener {
                mBinding.playBtn.visibility=View.GONE
                mBinding.videoView.requestFocus()
                mBinding.videoView.start()
            }
        }
    }

}
