package com.sandbox.androidsandbox.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sandbox.androidsandbox.models.Notes

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notes_db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Notes.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Notes.TABLE_NAME)
        onCreate(db)
    }

    fun insertNote(note: String?): Long {
        // get writable database as we want to write data
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        // insert row
        // close db connection
        // return newly inserted row id
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Notes.COLUMN_NOTE, note)
        val id = db.insert(Notes.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getNote(id: Long): Notes? {
        // get readable database as we are not inserting anything
        val db = this.readableDatabase
        val cursor: Cursor? = db.query(
            Notes.TABLE_NAME,
            arrayOf(Notes.COLUMN_ID, Notes.COLUMN_NOTE, Notes.COLUMN_TIMESTAMP),
            Notes.COLUMN_ID + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        // prepare note object
        val note = Notes(
            cursor!!.getInt(cursor.getColumnIndex(Notes.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(Notes.COLUMN_NOTE)),
            cursor.getString(cursor.getColumnIndex(Notes.COLUMN_TIMESTAMP))
        )
        // close the db connection
        cursor.close()
        return note
    }

    fun getAllNotes(): List<Notes>? {
        val notes: MutableList<Notes> = ArrayList()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM " + Notes.TABLE_NAME + " ORDER BY " +
                    Notes.COLUMN_TIMESTAMP + " DESC"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val note = Notes(
                    id = cursor.getInt(cursor.getColumnIndex(Notes.COLUMN_ID)),
                    note = cursor.getString(cursor.getColumnIndex(Notes.COLUMN_NOTE)),
                    timestamp = cursor.getString(cursor.getColumnIndex(Notes.COLUMN_TIMESTAMP))
                )
                notes.add(note)
            } while (cursor.moveToNext())
        }
        // close db connection
        db.close()
        // return notes list
        return notes
    }

    fun getNotesCount(): Int {
        val countQuery = "SELECT * FROM " + Notes.TABLE_NAME
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val count: Int = cursor.count
        cursor.close()
        // return count
        return count
    }

    fun updateNote(note: Notes): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Notes.COLUMN_NOTE, note.note)
        // updating row
        return db.update(
            Notes.TABLE_NAME,
            values,
            Notes.COLUMN_ID.toString() + " = ?",
            arrayOf(java.lang.String.valueOf(note.id))
        )
    }

    fun deleteNote(note: Notes) {
        val db = this.writableDatabase
        db.delete(
            Notes.TABLE_NAME,
            Notes.COLUMN_ID.toString() + " = ?",
            arrayOf(java.lang.String.valueOf(note.id))
        )
        db.close()
    }

}