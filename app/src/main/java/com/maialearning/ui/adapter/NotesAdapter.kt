package com.maialearning.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.model.NotesModel
import com.maialearning.util.formateDateFromstring
import com.maialearning.util.getAbbreviatedFromDateTime
import com.maialearning.util.getDate
import com.maialearning.util.replaceNextLine


class NotesAdapter(val onItemClick: OnItemClickType,val array: NotesModel?) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemNotesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (array!= null) {
            viewHolder.binding.textTitle.text = array[position].noteTitle
//            viewHolder.binding.textDescription.text = Html.fromHtml(array[position].noteDescription)
            array.get(position).noteDescription.let {
                val spanned = Html.fromHtml(it)
                val chars = CharArray(spanned.length)
                if (spanned.length < 200)
                    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                else
                    TextUtils.getChars(spanned, 0, 198, chars, 0)
                TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                val plainText = String(chars)
                viewHolder.binding.textDescription.setText(plainText.trim().replaceNextLine())

        }
            Log.e("orig Data:- ${array[position].noteCreationDate} ","  -- Converted data > ${ getAbbreviatedFromDateTime(array[position].noteCreationDate ,"MM/dd/YYYY","MMM-dd-yyyy")}")
            viewHolder.binding.textDate.text =
               formateDateFromstring("MM/dd/YYYY","MMM dd yyyy",array[position].noteCreationDate )+ " " + array[position].authorName
            viewHolder.binding.textDescription.setOnClickListener {
                onItemClick.onClickOpt(position,
                    "root")
            }
            viewHolder.binding.apply {
                commentList.setOnClickListener { onItemClick.onClickOpt(position, "comment") }
            }
        }

    }

    override fun getItemCount(): Int {
        return array?.size?:0
    }

}

