package com.android.example.projectsparks.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.example.projectsparks.database.TransactionContract.TransactionEntry

class TransactionHelper(context: Context?) :
    SQLiteOpenHelper(context, "transaction.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {

        val SQL_CREATE_TRANSACTION_TABLE =
            ("CREATE TABLE " + TransactionEntry.TABLE_NAME + " ("
                    + TransactionEntry.COLUMN_TRANS_FROM + " VARCHAR, "
                    + TransactionEntry.COLUMN_TRANS_TO + " VARCHAR, "
                    + TransactionEntry.COLUMN_TRANS_AMT + " INTEGER, "
                    + TransactionEntry.COLUMN_TRANS_STATUS + " INTEGER);")

        db.execSQL(SQL_CREATE_TRANSACTION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun insertTransferData(
        fromName: String,
        toName: String,
        amount: String,
        status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TransactionEntry.COLUMN_TRANS_FROM, fromName)
        contentValues.put(TransactionEntry.COLUMN_TRANS_TO, toName)
        contentValues.put(TransactionEntry.COLUMN_TRANS_AMT, amount)
        contentValues.put(TransactionEntry.COLUMN_TRANS_STATUS, status)
        val result = db.insert(TransactionEntry.TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun readAllTransactionData(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from " + TransactionContract.TransactionEntry.TABLE_NAME, null)
    }
}