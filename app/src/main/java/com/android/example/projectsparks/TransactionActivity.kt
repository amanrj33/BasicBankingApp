package com.android.example.projectsparks

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projectsparks.adapter.TransactionAdapter
import com.android.example.projectsparks.database.TransactionContract
import com.android.example.projectsparks.database.TransactionHelper
import com.android.example.projectsparks.database.UserContract
import com.android.example.projectsparks.database.UserHelper
import com.android.example.projectsparks.helper.Transaction
import com.android.example.projectsparks.helper.User

class TransactionActivity : AppCompatActivity() {
    lateinit var userRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        userRecyclerView = findViewById(R.id.transactionRV)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = TransactionAdapter(this, listOfTransactions())
    }

    private fun listOfTransactions(): ArrayList<Transaction> {
        val transList = ArrayList<Transaction>()
        val cursor: Cursor = TransactionHelper(this).readAllTransactionData()

        val fromColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANS_FROM)
        val toColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANS_TO)
        val amountColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANS_AMT)
        val statusColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANS_STATUS)

        while (cursor.moveToNext()) {
            val from = cursor.getString(fromColumnIndex)
            val to = cursor.getString(toColumnIndex)
            val amount = cursor.getInt(amountColumnIndex)
            val status = cursor.getInt(statusColumnIndex)

            transList.add(
                Transaction(from, to, amount, status)
            )
        }

        return transList
    }
}