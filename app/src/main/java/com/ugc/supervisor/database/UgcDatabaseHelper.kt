package com.ugc.supervisor.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ugc.supervisor.core.LOGGER_TAG
import com.ugc.supervisor.model.Room
import com.ugc.supervisor.websocket.model.MessageEmitter
import com.ugc.supervisor.websocket.model.MessageFrom

class UgcDatabaseHelper(
    context: Context
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        null, DATABASE_VERSION
    ) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ugc.db"
        val TABLE_MESSAGE = "message"

        val COLUMN_ID = "_id"
        val COLUMN_EMMITER = "emmiter"
        val COLUMN_MESSAGE = "message"
        val COLUMN_DATE = "date"
        val COLUMN_ROOM = "room"

    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MESSAGES_TABLE = ("CREATE TABLE " +
                TABLE_MESSAGE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_EMMITER + " TEXT,"
                + COLUMN_MESSAGE + " TEXT,"
                + COLUMN_DATE + " INT,"
                + COLUMN_ROOM + " TEXT" + ")")

        db.execSQL(CREATE_MESSAGES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE)
        onCreate(db)
    }

    fun addMessage(message: MessageFrom, room: Room) {
        val values = ContentValues()
        values.put(COLUMN_EMMITER, message.emitter.name)
        values.put(COLUMN_MESSAGE, message.message)
        values.put(COLUMN_DATE, message.date)
        values.put(COLUMN_ROOM, room.name)


        val db = this.writableDatabase
        db.insert(TABLE_MESSAGE, null, values)
        db.close()
    }

    fun getRoomMessages(room : Room): List<MessageFrom> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * " +
                "FROM message " +
                "WHERE room LIKE '" + room.name + "' " +
                " ORDER BY date ASC LIMIT 50", null)

        var messages = arrayListOf<MessageFrom>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    var message = MessageFrom(
                        MessageEmitter.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_EMMITER))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_DATE))
                    )

                    messages.add(message)


                } while (cursor.moveToNext());
            }
        }catch (e: Exception){
            Log.e(LOGGER_TAG, e.toString())
        }

        return messages
    }
}

