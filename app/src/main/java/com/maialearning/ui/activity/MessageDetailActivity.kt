package com.maialearning.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ActivityLoginBinding
import com.maialearning.databinding.MessageDetailBinding
import com.maialearning.ui.adapter.FilesAdapter

class MessageDetailActivity : AppCompatActivity(), OnItemClick {
    private lateinit var mBinding: MessageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = MessageDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.root.setBackgroundColor(getResources().getColor(R.color.white))
        mBinding.filesList.adapter = FilesAdapter(this)
        mBinding.toolbar.backBtn.setOnClickListener { finish() }
    }


    override fun onClick(positiion: Int) {

    }


}