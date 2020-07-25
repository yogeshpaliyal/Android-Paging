package com.example.paginglibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.paginglibrary.adapter.Adapter
import com.example.paginglibrary.R
import com.example.paginglibrary.databinding.ActivityMainBinding
import com.example.paginglibrary.viewmodel.MainActivityFactory
import com.example.paginglibrary.viewmodel.MainActivityViewModel

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class MainActivity : AppCompatActivity() {
    val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this, MainActivityFactory()).get(MainActivityViewModel::class.java)
    }

    lateinit var binding: ActivityMainBinding

    val mAdapter by lazy {
        Adapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        binding.recyclerView.adapter = mAdapter
        vm.dataList?.observe(this, Observer {
            t ->
            mAdapter.submitList(t)
        })
    }
}