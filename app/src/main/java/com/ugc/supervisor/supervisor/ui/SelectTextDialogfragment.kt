package com.ugc.ugctv.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ugc.supervisor.R
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.supervisor.callback.SelectTextCallback


class SelectTextDialogfragment(val room : Room) : DialogFragment()  {

    lateinit var callback: SelectTextCallback


    fun setCallBack(callBack: SelectTextCallback): SelectTextDialogfragment {
        this.callback = callBack
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {

        super.onCreateView(inflater, parent, state)
        val thisView: View =
            inflater.inflate(R.layout.list_dialog, parent, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        val textAdapter = TextAdapter(room, callback, activity!!.baseContext)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        val simple_recyclerview : RecyclerView =  thisView.findViewById(R.id.simple_recyclerview)

        simple_recyclerview.setLayoutManager(linearLayoutManager)

        simple_recyclerview.addItemDecoration(
            DividerItemDecoration(
                simple_recyclerview.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        simple_recyclerview.setAdapter(textAdapter)

        return thisView
    }


}