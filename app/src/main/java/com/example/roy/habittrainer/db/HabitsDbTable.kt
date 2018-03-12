package com.example.roy.habittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.roy.habittrainer.Habit
import com.example.roy.habittrainer.db.HabitEntry.DESCRIPTION_COLUMN
import com.example.roy.habittrainer.db.HabitEntry.TABLE_NAME
import com.example.roy.habittrainer.db.HabitEntry.TITLE_COLUMN
import com.example.roy.habittrainer.db.HabitEntry._ID
import java.io.ByteArrayOutputStream

/**
 * Created by Roy on 3/6/18.
 */

class HabitsDbTable(context: Context) {

    private val dbHelper = HabitTrainerDb(context)

    fun store(habit: Habit): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(TITLE_COLUMN, habit.title)
            put(DESCRIPTION_COLUMN, habit.description)
            put(HabitEntry.IMAGE_COLUMN, toByteArray(habit.image))
        }

        val id = db.transaction {
            insert(HabitEntry.TABLE_NAME, null, values)
        }

        return id
    }

    fun readAllHabits(): List<Habit> {
        val columns = arrayOf(_ID, TITLE_COLUMN, DESCRIPTION_COLUMN,
                HabitEntry.IMAGE_COLUMN)

        val order = "${_ID} ASC"

        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.doQuery(TABLE_NAME, columns, orderBy = order)

        return parseHabitsFrom(cursor)
    }

    private fun parseHabitsFrom(cursor: Cursor): MutableList<Habit> {
        val habits = mutableListOf<Habit>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(TITLE_COLUMN)
            val description = cursor.getString(DESCRIPTION_COLUMN)
            val bitmap = cursor.getBitmap(HabitEntry.IMAGE_COLUMN)
            habits.add(Habit(title, description, bitmap))
        }
        cursor.close()
        return habits
    }


    private fun toByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}

private fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))

private fun Cursor.getBitmap(columnName: String): Bitmap {
    val bytes = getBlob(getColumnIndex(columnName))
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}


private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                                   selectionArgs: Array<String>? = null, groupBy: String? = null,
                                   having: String? = null, orderBy: String? = null): Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    val result = try {
        val returnValue = function()
        setTransactionSuccessful()
        returnValue
    } finally {
        endTransaction()
    }
    close()

    return result
}