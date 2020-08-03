package com.example.paginglibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagedList
import com.example.paginglibrary.adapter.Adapter
import com.example.paginglibrary.R
import com.example.paginglibrary.adapter.ReposLoadStateAdapter
import com.example.paginglibrary.databinding.ActivityMainBinding
import com.example.paginglibrary.extension.isVisible
import com.example.paginglibrary.viewmodel.MainActivityFactory
import com.example.paginglibrary.viewmodel.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
@ExperimentalCoroutinesApi
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
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        binding.recyclerView.adapter =  mAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { mAdapter.retry() }
        )

        lifecycleScope.launch {
            vm.hitApi()?.collectLatest {
                mAdapter.submitData(it)
            }
        }

        binding.retryButton.setOnClickListener { mAdapter.retry()    }



        mAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.recyclerView.isVisible(loadState.source.refresh is LoadState.NotLoading)
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible(loadState.source.refresh is LoadState.Loading)
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible(loadState.source.refresh is LoadState.Error)

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}