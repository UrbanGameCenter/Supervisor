package com.ugc.supervisor.supervisor.adapter

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ugc.supervisor.R
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.supervisor.ui.RoomFragment

class RoomPagerAdapter(val fragmentManager: FragmentManager, val context : Context) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return Room.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return Room.values()[position].readableName
    }

    override fun getItem(position: Int): Fragment {
        return RoomFragment.newInstance(Room.values()[position], context)
    }
}