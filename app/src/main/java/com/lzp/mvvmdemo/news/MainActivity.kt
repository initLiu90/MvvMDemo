package com.lzp.mvvmdemo.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzp.mvvmdemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_content, MainFragment.newInstance(), MainFragment.TAG)
            .commit()
    }
}