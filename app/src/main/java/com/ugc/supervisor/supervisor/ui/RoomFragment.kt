package com.ugc.supervisor.supervisor.ui

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ugc.supervisor.R
import com.ugc.supervisor.core.LOGGER_TAG
import com.ugc.supervisor.core.PreferenceManager
import com.ugc.supervisor.database.UgcDatabaseHelper
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.supervisor.adapter.SimpleMessageAdapter
import com.ugc.supervisor.supervisor.callback.SelectTextCallback
import com.ugc.supervisor.websocket.core.WebsocketManager
import com.ugc.supervisor.websocket.model.MessageEmitter
import com.ugc.supervisor.websocket.model.MessageFrom
import com.ugc.supervisor.websocket.model.MessageTo
import com.ugc.ugctv.settings.SelectTextDialogfragment
import kotlinx.android.synthetic.main.room_fragment.*

class RoomFragment(val room: Room, context: Context) : Fragment() {

    private val SELECT_TEXT_DIALOG = "SELECT_TEXT_DIALOG"

    private val adapter: SimpleMessageAdapter
    private lateinit var selectTextDialogfragment: SelectTextDialogfragment

    companion object {
        fun newInstance(room: Room, context: Context): RoomFragment {
            return RoomFragment(room, context)
        }
    }

    init {
        adapter = SimpleMessageAdapter(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL)
        linearLayoutManager.setStackFromEnd(true)

        message_listview.addItemDecoration(
            DividerItemDecoration(
                context,
                linearLayoutManager.getOrientation()
            )
        )

        message_listview.setLayoutManager(linearLayoutManager)
        adapter.setData(UgcDatabaseHelper(activity!!.applicationContext).getRoomMessages(room))
        message_listview.setAdapter(adapter)

        send_button.isEnabled = false

        list_text.setOnClickListener {
            selectTextDialogfragment = SelectTextDialogfragment(room)
                .setCallBack(object : SelectTextCallback {
                    override fun onTextSelected(text: String) {
                        selectTextDialogfragment.dismiss()
                        textmessage_edittext.setText(text)
                    }
                })

            selectTextDialogfragment.show(activity!!.supportFragmentManager, SELECT_TEXT_DIALOG)
        }

        textmessage_edittext.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    enabledButton(p0!!.trim().length != 0)
                }
            })

        send_button.setOnClickListener {
            if(PreferenceManager(context).isSessionStarted(room)){
                sendMessage()
            }else{
                Snackbar.make(it,getString(R.string.session_not_started),Snackbar.LENGTH_LONG).show()
            }
        }

        initChronometer()

        action_button.setOnClickListener {
            doStartStop()
        }
    }

    private fun enabledButton(shouldEnable: Boolean) {
        if(shouldEnable){
            send_button.setColorFilter(ContextCompat.getColor(activity!!.baseContext, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
        }else{
            send_button.setColorFilter(ContextCompat.getColor(activity!!.baseContext, R.color.light_grey), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        send_button.isEnabled = shouldEnable
    }

    private fun doStartStop() {

        if(PreferenceManager(context).isSessionStarted(room)){
            WebsocketManager.instance.stopRoom(room)
            PreferenceManager(context).finishSessionForRoom(room)
            action_indicator.setImageResource(R.drawable.ic_play)
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()

            addMessage(
                MessageFrom(
                    MessageEmitter.system,
                    "Fin de session",
                    System.currentTimeMillis()
                )
            )
        }else{
            WebsocketManager.instance.startRoom(room)
            PreferenceManager(context).startSessionForRoom(room)
            action_indicator.setImageResource(R.drawable.ic_stop)
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

            addMessage(
                MessageFrom(
                    MessageEmitter.system,
                    "Une session à été démarrée",
                    System.currentTimeMillis()
                )
            )
        }
    }

    private fun sendMessage() {

        WebsocketManager.instance.sendMessage(
            MessageTo(
                textmessage_edittext.getText().toString(),
                room.name
            )
        )

        addMessage(
            MessageFrom(
                MessageEmitter.supervisor,
                textmessage_edittext.getText().toString(),
                System.currentTimeMillis()
            )
        )

        textmessage_edittext.getText().clear()
    }

    private fun initChronometer() {

        if(PreferenceManager(context).isSessionStarted(room)) {
            val elapsedRealtimeOffset =
                System.currentTimeMillis() - SystemClock.elapsedRealtime()
            chronometer.base =   PreferenceManager(context).getStartTimeForRoom(room) - elapsedRealtimeOffset
            action_indicator.setImageResource(R.drawable.ic_stop)
            chronometer.start()
        }else{
            chronometer.base = SystemClock.elapsedRealtime()
            action_indicator.setImageResource(R.drawable.ic_play)
        }
    }

    private fun addMessage(messageFrom: MessageFrom) {
        Log.d(
            LOGGER_TAG,
            "Message from " + messageFrom.emitter + " : " + messageFrom.message
        )

        UgcDatabaseHelper(activity!!.applicationContext).addMessage(messageFrom, room)
        adapter.addData(messageFrom)
    }

}
