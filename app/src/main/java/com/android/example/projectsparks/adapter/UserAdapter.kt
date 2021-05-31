package com.android.example.projectsparks.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projectsparks.R
import com.android.example.projectsparks.UserDataActivity
import com.android.example.projectsparks.UserListActivity
import com.android.example.projectsparks.database.TransactionHelper
import com.android.example.projectsparks.database.UserHelper
import com.android.example.projectsparks.helper.User
import java.util.*

class UserAdapter(
    private val context: Context,
    list: ArrayList<User>,
    private val isTransferringMoney: Boolean,
    private val transferDoneListener: TransferDoneClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val userArrayList: ArrayList<User> = list

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById(R.id.userName)
        var userAccountBalance: TextView = itemView.findViewById(R.id.balance)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentUser = userArrayList[position]
        viewHolder.userName.text = currentUser.name
        viewHolder.userAccountBalance.text = String.format("₹ %d", currentUser.balance)

        viewHolder.itemView.setOnClickListener {

            if (isTransferringMoney)
                transferDoneListener.onDoneTransfer(currentUser)
            else {

                val intent = Intent(context, UserDataActivity::class.java)
                intent.putExtra("ACCOUNT_NO", currentUser.accountNumber)
                intent.putExtra("NAME", currentUser.name)
                intent.putExtra("EMAIL", currentUser.email)
                intent.putExtra("IFSC_CODE", currentUser.IFSCCode)
                intent.putExtra("PHONE_NO", currentUser.phoneNumber)
                intent.putExtra("BALANCE", currentUser.balance.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    interface TransferDoneClickListener {
        fun onDoneTransfer(user: User)
    }

}