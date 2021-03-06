package com.devstart.hymn.hymn_detail.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.devstart.hymn.databinding.ActivityHymnDetailBinding
import com.devstart.hymn.home.ui.MainActivity
import com.devstart.hymn.data.model.Song
import com.devstart.hymn.data.model.SongResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HymnDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHymnDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val hymnSong = intent.getParcelableExtra<SongResponse>("extra_item")
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.textView.text = Html.fromHtml(hymnSong?.song)

        supportActionBar!!.title = hymnSong?.title

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}