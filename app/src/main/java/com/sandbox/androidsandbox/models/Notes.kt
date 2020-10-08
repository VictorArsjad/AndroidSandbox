package com.sandbox.androidsandbox.models

data class Notes(
    val id: Int = 0,
    val note: String? = null,
    val timestamp: String? = null
) {

    companion object {
        const val TABLE_NAME = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOTE = "note"
        const val COLUMN_TIMESTAMP = "timestamp"

        // Create table SQL query
        const val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")")
    }
}