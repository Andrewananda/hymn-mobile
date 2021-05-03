package com.example.hymn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.hymn.adapter.HymnAdapter
import com.example.hymn.api.ApiResponse
import com.example.hymn.api.Failure
import com.example.hymn.api.Success
import com.example.hymn.databinding.ActivityMainBinding
import com.example.hymn.model.Response
import com.example.hymn.model.Song
import com.example.hymn.viewModel.HymnViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var hymnViewModel: HymnViewModel

    lateinit var adapter : HymnAdapter
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Hymn).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpView()
        setUpAdapter()
    }


    private fun setUpView() {
        hymnViewModel.getHymnData().observe(this, { response ->
            when(response) {
                is Failure -> {
                    displayError(response.throwable)
                }
                is Success<*> -> {
                    displayData(response.data as Response)
                }
            }
        })
    }

    private fun setUpAdapter() {
        adapter = HymnAdapter()
        binding.recyclerview.adapter = adapter
    }

    private fun displayData(data: Response) {
        val hymns = data.data
        adapter.submitList(hymns)
    }

    private fun displayError(error: Throwable) {
        Log.e("MAIN_ACTIVITY_ERROR", error.message.toString())
    }
}