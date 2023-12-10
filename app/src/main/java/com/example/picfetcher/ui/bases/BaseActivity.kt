package com.example.picfetcher.ui.bases

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.example.picfetcher.R

abstract class BaseActivity : AppCompatActivity() {

    fun displayFragment(fragmentContainerId: Int, fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .add(fragmentContainerId, fragment)
            .commit()
    }


}