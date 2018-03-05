package com.example.roy.habittrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_title.text = getString(R.string.card_title_sample)
        tv_description.text = getString(R.string.card_description_sample)
        iv_icon.setImageResource(R.drawable.water)
    }
}
