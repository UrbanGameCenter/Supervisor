package com.ugc.supervisor.model

import com.ugc.supervisor.R

enum class Room(val readableName: String, val listMessage : Int) {
    HOWARD_CARTER("Le secret d'Howard Carter", R.array.howard_carter_messages),
    JIG_SAW("Jig saw", R.array.jig_saw_messages),
    PRISON_BREAKOUT("Prison breakout", R.array.prison_breakout_messages)
}