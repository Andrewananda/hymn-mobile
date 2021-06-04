package com.devstart.hymn


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.devstart.hymn.adapter.HymnAdapter
import com.devstart.hymn.api.Failure
import com.devstart.hymn.api.Success
import com.devstart.hymn.databinding.ActivityMainBinding
import com.devstart.hymn.model.Response
import com.devstart.hymn.model.Song
import com.devstart.hymn.viewModel.HymnViewModel
import javax.inject.Inject

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
                   showProgressBar()
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
        (application as Hymn).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showProgressBar()
        setUpView()
        setUpAdapter()
    }


    private fun setUpView() {
        hymnViewModel.getHymnData().observe(this, { response ->
            Log.i("OBSERVABLE", response.toString())
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

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showData() {
        binding.recyclerview.visibility = View.VISIBLE
    }

    private fun hideData() {
        binding.recyclerview.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun displayData(data: Response) {
        if(data.data.isEmpty()) {
            binding.hymnLabel.text = "Hymns Not Found"
            binding.hymnLabel.visibility = View.VISIBLE
            hideData()
            hideProgressBar()
        }else {
            binding.hymnLabel.visibility = View.GONE
            val hymns = data.data
            adapter.submitList(hymns)
            hideProgressBar()
            showData()
        }
    }

    private fun displayError(error: Throwable) {
        Log.e("MAIN_ACTIVITY_ERROR", error.message.toString())
    }
}