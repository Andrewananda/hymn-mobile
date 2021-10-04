package com.devstart.hymn.home.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.devstart.hymn.hymn_detail.ui.HymnDetailActivity
import com.devstart.hymn.R
import com.devstart.hymn.api.Failure
import com.devstart.hymn.api.Success
import com.devstart.hymn.databinding.ActivityMainBinding
import com.devstart.hymn.data.model.Response
import com.devstart.hymn.data.model.SongResponse
import com.devstart.hymn.home.viewModel.HymnViewModel
import com.devstart.hymn.util.hide
import com.devstart.hymn.util.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var hymnViewModel: HymnViewModel

    lateinit var adapter : HymnAdapter
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.search_bar)

        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                if (newText != null) {
                    binding.progressBar.show()
                   hymnViewModel.searchHymn(newText)
                   observeSearchResponse()
                }else {
                    hymnViewModel.searchHymn("")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.progressBar.show()
                hymnViewModel.searchHymn(newText)
                observeSearchResponse()
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.progressBar.show()
        binding.errorLayout.hide()
        observeHymns()
        setUpAdapter()
    }


    private fun observeHymns() {
        hymnViewModel.getHymnData().observe(this, { response ->
            when(response) {
                is Failure -> {
                    displayError(response.throwable)
                }
                is Success<*> -> {
                   displayData(response.data as List<SongResponse>)
                }
            }
        })
    }

    private fun observeSearchResponse() {
        hymnViewModel.getSearchData().observe(this, {response ->
            Log.i("SearchResponse", response.toString())
            when(response) {
                is Failure -> {
                    displayError(response.throwable)
                }
                is Success<*> -> {
                    displayData(response.data as List<SongResponse>)
                }
            }
        })
    }

    private fun setUpAdapter() {
        adapter = HymnAdapter(HymnAdapter.OnClickListener {
            navigateToHymn(it)
        })
        binding.recyclerview.adapter = adapter
    }

    private fun navigateToHymn(hymn: SongResponse) {
        val intent = Intent(this, HymnDetailActivity::class.java)
        intent.putExtra("extra_item", hymn)
        startActivity(intent)
    }

    private fun displayData(data: List<SongResponse>) {
        if(data.isNullOrEmpty()) {
            binding.hymnLabel.text = getText(R.string.no_hymn_found)
            binding.hymnLabel.visibility = View.VISIBLE
            binding.recyclerview.hide()
            binding.progressBar.hide()
        }else {
            binding.hymnLabel.visibility = View.GONE
            adapter.submitList(data)
            binding.progressBar.hide()
            binding.recyclerview.show()
        }
    }

    private fun displayError(error: Throwable) {
        binding.progressBar.hide()
        binding.errorMessage.text = getString(R.string.error_occurred)
        binding.errorLayout.show()
        binding.retry.setOnClickListener {
            observeHymns()
        }
    }
}