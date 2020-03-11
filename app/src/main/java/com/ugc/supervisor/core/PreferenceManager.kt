package com.ugc.supervisor.core

import android.content.Context
import android.content.SharedPreferences
import com.ugc.supervisor.model.Room

class PreferenceManager(private val context: Context?) {


    fun getDefaultSharedPreferences(context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(
            context.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
    }

    fun getStartTimeForRoom(room : Room): Long {
        return getDefaultSharedPreferences(context).getLong(
            makePreferenceKey(room), 0L)
    }

    fun startSessionForRoom(room : Room) {

        getDefaultSharedPreferences(context)
            .edit()
            .putLong(makePreferenceKey(room), System.currentTimeMillis())
            .apply()
    }

    fun finishSessionForRoom(room : Room) {
        getDefaultSharedPreferences(context)
            .edit()
            .putLong(makePreferenceKey(room), 0L)
            .apply()

    }

    private fun makePreferenceKey(room : Room) : String{
        return room.name.toUpperCase() + "_START_TIME"
    }

    fun isSessionStarted(room : Room): Boolean {
        return !getStartTimeForRoom(room).equals(0L)
    }

}