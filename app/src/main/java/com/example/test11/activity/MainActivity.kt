package com.example.test11.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.test11.R
import com.example.test11.databinding.ActivityMainBinding
import com.example.test11.fragment.AddContactsFragment
import com.example.test11.fragment.ViewContactsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val view = ViewContactsFragment()
        makeCurrentFragment(view)
    }

    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment).addToBackStack(null)
            commit()
        }
    }
}