package com.android.example.projectsparks.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.example.projectsparks.database.UserContract.UserEntry

class UserHelper(context: Context?) : SQLiteOpenHelper(context, "User.db", null, 1) {

    private var TABLE_NAME: String = UserEntry.TABLE_NAME

    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the user table
        val SQL_CREATE_USER_TABLE = ("CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER PRIMARY KEY, "
                + UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);")

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE)

        // Insert Into Table
        db.execSQL("insert into $TABLE_NAME values(1001,'Virat Kohli', 'virat@gmail.com','XXIN1234567','9876543210', 26100)")
        db.execSQL("insert into $TABLE_NAME values(1002,'Darshan Raval', 'darshan@gmail.com','XXIN1234567','9876543210', 19000)")
        db.execSQL("insert into $TABLE_NAME values(1003,'Shraddha Kapoor', 'shraddha@gmail.com','XXIN1234567','9876543210', 21150)")
        db.execSQL("insert into $TABLE_NAME values(1004,'Krati Saini', 'krati@gmail.com','XXIN1234567','9876543210', 5150)")
        db.execSQL("insert into $TABLE_NAME values(1005,'Shahrukh Khan', 'shahrukh@gmail.com','XXIN1234567','9876543210', 10580)")
        db.execSQL("insert into $TABLE_NAME values(1006,'Shruti Hassan', 'shruti@gmail.com','XXIN1234567','9876543210', 9820)")
        db.execSQL("insert into $TABLE_NAME values(1007,'Kajal Aggarwal', 'kajal@gmail.com','XXIN1234567','9876543210', 7500)")
        db.execSQL("insert into $TABLE_NAME values(1008,'Hrithik Roshan', 'hrithik@gmail.com','XXIN1234567','9876543210', 24400)")
        db.execSQL("insert into $TABLE_NAME values(1009,'A B Devilliers', 'mr360@gmail.com','XXIN1234567','9876543210', 15610)")
        db.execSQL("insert into $TABLE_NAME values(1010,'Disha Patani', 'disha@gmail.com','XXIN1234567','9876543210', 17180)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun readAllUserData(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from " + UserEntry.TABLE_NAME, null)
    }

    fun readOneUserData(accountNo: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery(
            "select * from " + UserEntry.TABLE_NAME + " where " +
                    UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo, null
        )
    }

    fun readAllUserDataExceptSender(accountNo: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery(
            "select * from " + UserEntry.TABLE_NAME + " where " +
                    UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " != " + accountNo, null
        )
    }

    fun updateAmount(accountNo: Int, amount: Int) {
        val db = this.writableDatabase
        db.execSQL(
            "update " + UserEntry.TABLE_NAME + " set " + UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount.toString() + " where " +
                    UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo
        )
    }
}