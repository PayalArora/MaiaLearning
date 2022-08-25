package com.maialearning.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemShorcutsBinding


class ShortcutAdapter(var context: Context) : RecyclerView.Adapter<ShortcutAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemShorcutsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemShorcutsBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position == 0) {
            viewHolder.binding.textUpcomingVisits.text = "My Drive"
        } else {
            viewHolder.binding.textUpcomingVisits.text = "Youtube "
        }
        viewHolder.binding.textDescription.text = "N/A"
        viewHolder.binding.layout.setOnClickListener {
            if (position == 1) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"))
                intent.setPackage("com.google.android.youtube")
                context.startActivity(intent)
            }else{
//                val pdfUri = Uri.parse("file://sdcard/sdcard0/test.pdf")
//                val shareIntent = ShareCompat.IntentBuilder.from(context)
//                    .setText("Share PDF doc")
//                    .setType("application/pdf")
//                    .setStream(pdfUri)
//                    .intent
//                    .setPackage("com.google.android.apps.docs")
//                context.startActivity(shareIntent)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://drive.google.com/"))
                intent.setPackage("com.google.android.apps.docs")
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}

