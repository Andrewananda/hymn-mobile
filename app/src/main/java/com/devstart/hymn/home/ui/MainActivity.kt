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
import com.devstart.hymn.data.model.Song
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
                Toast.makeText(this@MainActivity, "$newText", Toast.LENGTH_LONG).show()
                if (newText != null) {
                    binding.progressBar.show()
                   hymnViewModel.searchHymn(newText)
                   observeSearchResponse()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
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
                    displayData(response.data as Response)
                }
            }
        })
    }

    private fun observeSearchResponse() {
        hymnViewModel.getSearchData().observe(this, {response ->
            Log.i("SearchResponse", response.toString())
            when(response) {
                is Failure -> {
                    Log.i("ERROR", "ERROR")
                    displayError(response.throwable)
                }
                is Success<*> -> {
                    Log.i("SUCCESS","SUCCESSFULL")
                    displayData(response.data as Response)
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

    private fun navigateToHymn(hymn: Song) {
        val intent = Intent(this, HymnDetailActivity::class.java)
        intent.putExtra("extra_item", hymn)
        startActivity(intent)
    }

    private fun displayData(data: Response) {
        if(data.data.isEmpty()) {
            binding.hymnLabel.text = "Hymn Not Found"
            binding.hymnLabel.visibility = View.VISIBLE
            binding.recyclerview.hide()
            binding.progressBar.hide()
        }else {
            binding.hymnLabel.visibility = View.GONE
            val hymns = data.data
            adapter.submitList(hymns)
            binding.progressBar.hide()
            binding.recyclerview.show()
        }
    }

    private fun displayError(error: Throwable) {
        binding.progressBar.hide()
        binding.errorMessage.text = "An Error Occurred"
        binding.errorLayout.show()
        binding.retry.setOnClickListener {
            observeHymns()
        }
    }
}