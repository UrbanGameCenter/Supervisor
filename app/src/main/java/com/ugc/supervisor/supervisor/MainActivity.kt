package com.ugc.supervisor.supervisor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.ugc.supervisor.R
import com.ugc.supervisor.core.AbstractActivity
import com.ugc.supervisor.supervisor.adapter.RoomPagerAdapter
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AbstractActivity() {

    companion object {

        public fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        viewpager.setAdapter(RoomPagerAdapter(this.supportFragmentManager, baseContext))
        viewpager.offscreenPageLimit = 3

        tabs.setupWithViewPager(viewpager)
        tabs.setTabMode(TabLayout.MODE_FIXED)
    }

}