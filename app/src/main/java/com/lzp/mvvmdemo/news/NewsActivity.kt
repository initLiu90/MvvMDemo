package com.lzp.mvvmdemo.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzp.mvvmdemo.R

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_content, NewsFragment.newInstance(), NewsFragment.TAG)
            .commit()
    }
}