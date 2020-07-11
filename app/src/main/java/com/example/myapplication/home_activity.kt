package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_activity.*

class home_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)

        val viewPagerAdapter=ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.apply {

            add(main())
            add(all_stats())
        }

        pager.adapter= viewPagerAdapter

    }
}