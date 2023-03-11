package com.example.test11.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.test11.R
import com.example.test11.databinding.ActivityRealTimeBinding
import com.example.test11.fragment.AddRealTimeFragment
import com.example.test11.fragment.ViewContactsFragment

class RealTimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRealTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val view = AddRealTimeFragment()
        makeCurrentFragment(view)
    }
    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container2, fragment).addToBackStack(null)
            commit()
        }
    }
}