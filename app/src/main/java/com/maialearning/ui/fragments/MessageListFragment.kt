package com.maialearning.ui.fragments

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
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.ui.activity.MessageDetailActivity
import com.maialearning.ui.adapter.MessageAdapter
import com.maialearning.util.SwipeHelper


class MessageListFragment : Fragment(), OnItemClickDelete {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    private var recyclerDataArrayList: ArrayList<String> = arrayListOf()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerDataArrayList.add("abc")
        recyclerDataArrayList.add("abc")
        recyclerDataArrayList.add("abc")
        recyclerDataArrayList.add("abc")
        recyclerDataArrayList.add("abc")
        recyclerDataArrayList.add("abc")
        setAdapter()
    }

    private fun setAdapter() {
        mBinding.recyclerList.adapter = MessageAdapter(this, recyclerDataArrayList)
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder,
//            ): Boolean {
//                // this method is called
//                // when the item is moved.
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                // this method is called when we swipe our item to right direction.
//                // on below line we are getting the item at a particular position.
//                (mBinding.recyclerList.adapter as MessageAdapter).showDelete(viewHolder.adapterPosition)
//                (mBinding.recyclerList.adapter as MessageAdapter).notifyItemRemoved(viewHolder.adapterPosition)
//            } // at last we are adding this
//            // to our recycler view.
//        }).attachToRecyclerView(mBinding.recyclerList)

        val swipeHelper: SwipeHelper = object : SwipeHelper(context, mBinding.recyclerList) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder,
                underlayButtons: MutableList<UnderlayButton>,
            ) {
                underlayButtons.add(UnderlayButton(
                    "Delete",
                    0,
                    Color.parseColor("#10E94235"), SwipeHelper.UnderlayButtonClickListener {
                        onDelete(it)
                    }
                ))
            }

        }
    }

    override fun onClick(positiion: Int) {
        val intent = Intent(requireActivity(), MessageDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onDelete(position: Int) {
        // this method is called when item is swiped.
        // below line is to remove item from our array list.
        recyclerDataArrayList.removeAt(position)

        // below line is to notify our item is removed from adapter.
        (mBinding.recyclerList.adapter as MessageAdapter).notifyItemRemoved(position)

    }

}

