package com.lzp.mvvmdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzp.mvvmdemo.calc.CalcActivity
import com.lzp.mvvmdemo.news.NewsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_news_btn.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }

        activity_main_calc_btn.setOnClickListener {
            startActivity(Intent(this, CalcActivity::class.java))
        }
    }
}