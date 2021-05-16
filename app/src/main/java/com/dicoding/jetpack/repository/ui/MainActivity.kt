package com.dicoding.jetpack.repository.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.jetpack.repository.databinding.ActivityMainBinding
import com.dicoding.jetpack.repository.ui.adapter.SectionPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        activityMainBinding.viewpager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewpager)

        supportActionBar?.elevation = 0f
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(0,77,170)))
    }
}