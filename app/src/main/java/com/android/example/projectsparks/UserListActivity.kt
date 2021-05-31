package com.android.example.projectsparks

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projectsparks.adapter.UserAdapter
import com.android.example.projectsparks.database.TransactionHelper
import com.android.example.projectsparks.database.UserContract.UserEntry
import com.android.example.projectsparks.database.UserHelper
import com.android.example.projectsparks.helper.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListActivity : AppCompatActivity() {
    lateinit var userRecyclerView: RecyclerView
    private var isTransfer = false
    private var from: String? = null
    private var amount: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        isTransfer = intent.extras!!.getBoolean("isTransfer")

        val titleTV: TextView = findViewById(R.id.titleText)
        val transButton: FloatingActionButton = findViewById(R.id.transactionButton)

        if (isTransfer){
            titleTV.text = getString(R.string.select_recipient)
            transButton.visibility = View.GONE

            from = intent.extras?.getString("FROM_USER_ACCOUNT_NAME").toString()
            amount = intent.extras?.getString("TRANSFER_AMOUNT").toString()
        }
        else{
            titleTV.text = getString(R.string.all_users)
            transButton.visibility = View.VISIBLE
            transButton.setOnClickListener {
                startActivity(Intent(this, TransactionActivity::class.java))
            }
        }

        userRecyclerView = findViewById(R.id.userRV)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = UserAdapter(this, listOfUsers(), isTransfer, from, amount)
    }

    private fun listOfUsers(): ArrayList<User> {

        val userList = ArrayList<User>()
        val cursor: Cursor = if (isTransfer) {
            val accNo: String = intent.extras?.getString("FROM_USER_ACCOUNT_NO").toString()
            UserHelper(this).readAllUserDataExceptSender(Integer.parseInt(accNo))
        }
        else
            UserHelper(this).readAllUserData()

        val accountNumberColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_NUMBER)
        val nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME)
        val emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL)
        val ifscCodeColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE)
        val phoneNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_PHONE_NO)
        val accountBalanceColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE)

        while (cursor.moveToNext()) {
            val currentName = cursor.getString(nameColumnIndex)
            val accountNumber = cursor.getInt(accountNumberColumnIndex)
            val email = cursor.getString(emailColumnIndex)
            val phoneNumber = cursor.getString(phoneNoColumnIndex)
            val ifscCode = cursor.getString(ifscCodeColumnIndex)
            val accountBalance = cursor.getInt(accountBalanceColumnIndex)

            userList.add(
                User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email)
            )
        }

        return userList
    }

    override fun onBackPressed() {
        if (isTransfer){
            val builderExitButton = AlertDialog.Builder(this)
            builderExitButton.setTitle("Do you want to cancel the transaction?")
                .setCancelable(false)
                .setPositiveButton("yes") { _, _ ->
                    val fromName = intent.extras?.getString("FROM_USER_ACCOUNT_NAME").toString()
                    val toName = " - "
                    val transAmt = intent.extras?.getString("TRANSFER_AMOUNT").toString()

                    val isSuccess = TransactionHelper(applicationContext).insertTransferData(fromName, toName, transAmt, 0)
                    if (isSuccess)
                        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_LONG).show()

                    super.onBackPressed()
                }.setNegativeButton("No", null)

            builderExitButton.create().show()
        } else {
            super.onBackPressed()
        }
    }
}