package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickDelete
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.InboxResponse
import com.maialearning.ui.activity.MessageDetailActivity
import com.maialearning.ui.adapter.MessageAdapter
import com.maialearning.util.SwipeHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.MessageViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageSentFragment : Fragment(), OnItemClickDelete {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    private var recyclerDataArrayList: ArrayList<InboxResponse.MessagesItem> = arrayListOf()
    private val messageViewModel: MessageViewModel by viewModel()
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
        mBinding.layoutRecycler.setBackgroundColor(getResources().getColor(R.color.white))
        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        requireActivity().getDrawable(R.drawable.divider)?.let { decor.setDrawable(it) }
        mBinding.recyclerList.addItemDecoration(decor)
        return mBinding.root
    }

    private fun observer() {
        messageViewModel.inboxObserver.observe(viewLifecycleOwner) {
            it?.let {
                dialog?.dismiss()
                val json = JSONObject(it.toString()).getJSONObject("Item")
                val array = json.getJSONArray("messages")
                for (j in 0 until array.length()) {
                    val objectProgram = array.getJSONObject(j)
                    recyclerDataArrayList.add(
                        InboxResponse.MessagesItem(
                            objectProgram.optString("senderName"),
                            objectProgram.optString("subject"),
                            objectProgram.optString("sentTimestamp"),
                            objectProgram.optString("messageId"),
                            objectProgram.optString("shortMessageBody"),
                            0
                        )
                    )
                }
                setAdapter()
            }
        }
        messageViewModel.showError.observe(viewLifecycleOwner) {
            dialog?.dismiss()
        }
        messageViewModel.delObserver.observe(viewLifecycleOwner) {
            dialog?.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = showLoadingDialog(requireContext())
        dialog.show()
        messageViewModel.getSent()
        observer()

    }


    private fun setAdapter() {
        mBinding.recyclerList.adapter = MessageAdapter(this, recyclerDataArrayList, 1)
        val swipeHelper: SwipeHelper = object : SwipeHelper(context, mBinding.recyclerList) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder,
                underlayButtons: MutableList<UnderlayButton>,
            ) {
                underlayButtons.add(UnderlayButton(
                    "Delete",
                    0,
                    Color.parseColor("#10E94235"), UnderlayButtonClickListener {
                        onDelete(it)
                    }
                ))
            }

        }

    }

    override fun onClick(positiion: Int, id: String) {
        val intent = Intent(requireActivity(), MessageDetailActivity::class.java).putExtra("id", id)
            .putExtra("type", "true")
            .putExtra("DATA", recyclerDataArrayList.get(positiion).messageId)
        startActivity(intent)
    }

    override fun onDelete(position: Int) {
//        recyclerDataArrayList.removeAt(position)
//        (mBinding.recyclerList.adapter as MessageAdapter).notifyItemRemoved(position)
        dialog.show()
        messageViewModel.delMessage(recyclerDataArrayList[position].messageId ?: "")
        recyclerDataArrayList.removeAt(position)

        // below line is to notify our item is removed from adapter.
        (mBinding.recyclerList.adapter as MessageAdapter).notifyItemRemoved(position)
    }


}

