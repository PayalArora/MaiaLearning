package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.ApperentshipLayoutBinding
import com.maialearning.databinding.NysCareerLayoutBinding
import com.maialearning.model.NYSCareerResponse
import com.maialearning.model.SkillsItem
import com.maialearning.model.StudentCareerReviewItem
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder

class ApperentshipFragment : Fragment() {
    lateinit var mBinding:ApperentshipLayoutBinding
    private lateinit var dialog: Dialog


    val content="<p><b>Apprenticeships combine a full-time job with training—and prepare workers to enter in-demand careers.</b></p><p>Apprenticeships provide affordable pathways to high-paying jobs and careers without the typical student debt associated with college.</p><p>Career seekers can find apprenticeships in industries such as information technology, finance and business, healthcare, hospitality, transportation, and manufacturing.</p><p _ngcontent-ysa-c333=\"\">94 percent of apprentices who complete an apprenticeship retain employment, with an average annual salary of \$70,000.</p><p>To find apprenticeship opportunities that match your interests and skills, visit the new Apprenticeship Finder on <a href=\"https://www.apprenticeship.gov/apprenticeship-job-finder\" target=\"_blank\">Apprenticeship.gov</a> — a one-stop source to connect career seekers, employers, and education partners with apprenticeship resources.</p>"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ApperentshipLayoutBinding.inflate(inflater, container, false)

        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.title.text = Html.fromHtml(content)

    }




}
