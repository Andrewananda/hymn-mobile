package com.example.hymn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.hymn.api.ApiResponse
import com.example.hymn.api.Failure
import com.example.hymn.api.Success
import com.example.hymn.model.Response
import com.example.hymn.model.Song
import com.example.hymn.viewModel.HymnViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var hymnViewModel: HymnViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Hymn).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
    }


    private fun setUpView() {
        hymnViewModel.getHymnData().observe(this, { response ->
            when(response) {
                is Failure -> {
                    displayError(response.throwable)
                }
                is Success<*> -> {
                    displayText(response.data as Response)
                }
            }
        })
    }

    private fun displayText(data: Response) {
        val textView = findViewById<TextView>(R.id.textView)
        data.data.forEach { res ->
            textView.text = res.chorus
        }
    }


    private fun displayError(error: Throwable) {
        Log.e("MAINACTIVITYERROR", error.message.toString())
    }
}