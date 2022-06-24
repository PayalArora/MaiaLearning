package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.databinding.SummaryTraficBinding
import com.maialearning.ui.adapter.*

class SummaryFragment : Fragment() {
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

        mBinding= SummaryTraficBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array=ArrayList<String>()
        array.add("Inform pilots about nearby planes or potentially hazardous conditions, such as weather, speed and direction of wind, or visibility problems.")
        array.add("Issue landing and take-off authorizations or instructions.")
        array.add("Provide flight path changes or directions to emergency landing fields for pilots traveling in bad weather or in emergency situations.")
        array.add("Transfer control of departing flights to traffic control centers and accept control of arriving flights.")
        array.add("Alert airport emergency services in cases of emergency or when aircraft are experiencing difficulties.")
        mBinding.progressView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.progressView.adapter = TraficSubAdapter(array)
        val arrayAct=ArrayList<String>()
        arrayAct.add("Making Decisions and Solving Problems. Analyzing information and evaluating results to choose the best solution and solve problems.")
        arrayAct.add("Identifying Objects, Actions, and Events. Identifying information by categorizing, estimating, recognizing differences or similarities, and detecting changes in circumstances or events.")
        arrayAct.add("Getting Information. Observing, receiving, and otherwise obtaining information from all relevant sources.")
        arrayAct.add("Monitoring Processes, Materials, or Surroundings. Monitoring and reviewing information from materials, events, or the environment, to detect or assess problems.")
        arrayAct.add("Communicating with Supervisors, Peers, or Subordinates. Providing information to supervisors, co-workers, and subordinates by telephone, in written form, e-mail, or in person.")
        mBinding.actView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.actView.adapter = TraficSubAdapter(arrayAct)
        val acdeAct=ArrayList<String>()
        acdeAct.add("Transportation. Knowledge of principles and methods for moving people or goods by air, rail, sea, or road, including the relative costs and benefits.")
        acdeAct.add("English Language. Knowledge of the structure and content of the English language including the meaning and spelling of words, rules of composition, and grammar.")
        acdeAct.add("Public Safety and Security. Knowledge of relevant equipment, policies, procedures, and strategies to promote effective local, state, or national security operations for the protection of people, data, property, and institutions.")
        acdeAct.add("Education and Training. Knowledge of principles and methods for curriculum and training design, teaching and instruction for individuals and groups, and the measurement of training effects.")
        acdeAct.add("Customer and Personal Service. Knowledge of principles and processes for providing customer and personal services. This includes customer needs assessment, meeting quality standards for services, and evaluation of customer satisfaction.")
        mBinding.acdeView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.acdeView.adapter = TraficSubAdapter(acdeAct)
        val skillAct=ArrayList<String>()
        skillAct.add("Active Listening. Giving full attention to what other people are saying, taking time to understand the points being made, asking questions as appropriate, and not interrupting at inappropriate times.")
        skillAct.add("Speaking. Talking to others to convey information effectively.")
        skillAct.add("Critical Thinking. Using logic and reasoning to identify the strengths and weaknesses of alternative solutions, conclusions, or approaches to problems.")
        skillAct.add("Judgment and Decision Making. Considering the relative costs and benefits of potential actions to choose the most appropriate one.")
        skillAct.add("Complex Problem Solving. Identifying complex problems and reviewing related information to develop and evaluate options and implement solutions.")
        mBinding.skillsView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.skillsView.adapter = TraficSubAdapter(skillAct)
        val interAct=ArrayList<String>()
        interAct.add("Enterprising. Enterprising occupations frequently involve starting up and carrying out projects. These occupations can involve leading people and making many decisions. Sometimes they require risk taking and often deal with business.")
        interAct.add("Conventional. Conventional occupations frequently involve following set procedures and routines. These occupations can include working with data and details more than with ideas. Usually there is a clear line of authority to follow.")
        interAct.add("Realistic. Realistic occupations frequently involve work activities that include practical, hands-on problems and solutions. They often deal with plants, animals, and real-world materials like wood, tools, and machinery. Many of the occupations require working outside, and do not involve a lot of paperwork or working closely with others.")
        interAct.add("Investigative. Investigative occupations frequently involve working with ideas, and require an extensive amount of thinking. These occupations can involve searching for facts and figuring out problems mentally.")
        interAct.add("Social occupations frequently involve working with, communicating with, and teaching people. These occupations often involve helping or providing service to others.")
        mBinding.interView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.interView.adapter = TraficSubAdapter(interAct)

    }

}
