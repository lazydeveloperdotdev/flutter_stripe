package com.rajyadavnp.oppia

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rajyadavnp.oppia.fragments.AccountFragment
import com.rajyadavnp.oppia.fragments.HomeFragment
import com.rajyadavnp.oppia.fragments.NotificationFragment
import com.rajyadavnp.oppia.fragments.StatFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                changeFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_stats -> {
                changeFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                changeFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_account -> {
                changeFragment(3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun changeFragment(position: Int) {

        var newFragment: Fragment = HomeFragment.newInstance()

        if (position == 0) {
            newFragment = HomeFragment.newInstance()
        } else if (position == 1) {
            newFragment = StatFragment.newInstance()
        } else if (position == 2) {
            newFragment = NotificationFragment.newInstance()
        } else if (position == 3) {
            newFragment = AccountFragment.newInstance()
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, newFragment).commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this).load(R.drawable.bg).into(background_image)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragment(0)
    }
}