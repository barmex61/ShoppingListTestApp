package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fatih.youtubeshoppinglisttestapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: CustomFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=fragmentFactory
        setContentView(R.layout.activity_main)
    }
}