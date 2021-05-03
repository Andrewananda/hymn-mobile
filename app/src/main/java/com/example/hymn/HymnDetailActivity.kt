package com.example.hymn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.hymn.databinding.ActivityHymnDetailBinding
import com.example.hymn.model.Song

class HymnDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHymnDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val hymnSong = intent.getParcelableExtra<Song>("extra_item")
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.textView.text = Html.fromHtml(hymnSong?.song)

        supportActionBar!!.title = hymnSong?.title
    }
}