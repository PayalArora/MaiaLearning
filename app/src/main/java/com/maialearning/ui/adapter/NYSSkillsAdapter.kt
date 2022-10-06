package com.maialearning.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.KnowBaseLayoutBinding
import com.maialearning.model.SkillsItem

class NYSSkillsAdapter(var context: Context, var data: ArrayList<SkillsItem>) :
    RecyclerView.Adapter<NYSSkillsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowBaseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowBaseLayoutBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val text = "<font color=#3743BE>${data.get(position).name}: </font> <font color=#707070>${
            data.get(position).decription
        }</font>"

        viewHolder.binding.name.text = Html.fromHtml(text)
        viewHolder.binding.text1.setText(data?.get(position)?.beginningSkill.toString())
        viewHolder.binding.text2.setText(data.get(position).finalSkill)
        viewHolder.binding.text3.setText(data.get(position).experiencesActivities)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}