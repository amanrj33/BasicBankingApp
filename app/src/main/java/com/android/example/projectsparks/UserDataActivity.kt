package com.android.example.projectsparks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class UserDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        //hooking up the views
        val name: TextView = findViewById(R.id.name_text)
        val email: TextView = findViewById(R.id.email_text)
        val accountNo: TextView = findViewById(R.id.account_no_text)
        val balance: TextView = findViewById(R.id.current_balance_text)
        val ifscCode: TextView = findViewById(R.id.ifsc_code_text)
        val mobileNo: TextView = findViewById(R.id.mobile_no_text)
        val transferMoney: Button = findViewById(R.id.transfer_money)

        //getting data from intent and displaying it
        val extras: Bundle = intent.extras!!
        name.text = extras.getString("NAME")
        accountNo.text = extras.getInt("ACCOUNT_NO").toString()
        email.text = extras.getString("EMAIL")
        mobileNo.text = extras.getString("PHONE_NO")
        ifscCode.text = extras.getString("IFSC_CODE")
        balance.text = extras.getString("BALANCE")

        //listener on button
        transferMoney.setOnClickListener {
            enterAmount(balance.text.toString(), accountNo.text.toString(), name.text.toString())
        }
    }

    private fun enterAmount(balance: String, accountNo: String, accountName: String) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_box, null)
        builder.setTitle("Enter Amount").setView(view).setCancelable(false)

        builder
            .setPositiveButton("SEND") { _, _ -> }
            .setNegativeButton("CANCEL", null)

        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val transferEditText: EditText = view.findViewById(R.id.enter_money)
            val transferAmount: String = transferEditText.text.toString()
            val currentBalance: Int = balance.toInt()
            when {
                transferAmount.isEmpty() -> {
                    transferEditText.error = "Amount cannot be empty"
                }
                transferAmount.toInt() > currentBalance -> {
                    transferEditText.error = "This account has insufficient balance"
                }

                else -> {
                    val intent = Intent(this, UserListActivity::class.java)
                    intent.putExtra("isTransfer", true)
                    intent.putExtra("FROM_USER_ACCOUNT_NO", accountNo)
                    intent.putExtra("FROM_USER_ACCOUNT_NAME", accountName)
                    intent.putExtra("FROM_USER_ACCOUNT_BALANCE", currentBalance)
                    intent.putExtra("TRANSFER_AMOUNT", transferEditText.text.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}