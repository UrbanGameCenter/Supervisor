package com.ugc.supervisor.supervisor

import android.content.Context
import android.content.Intent
import com.ugc.supervisor.core.AbstractActivity

class SupervisorActivity : AbstractActivity() {

    companion object{

        public fun newIntent(context: Context) : Intent{
            return Intent(context, SupervisorActivity::class.java)
        }
    }
}