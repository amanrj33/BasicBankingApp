package com.android.example.projectsparks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projectsparks.R
import com.android.example.projectsparks.helper.Transaction
import com.android.example.projectsparks.helper.User
import java.util.ArrayList

class TransactionAdapter(private val context: Context, list: ArrayList<Transaction>): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val userArrayList: ArrayList<Transaction> = list

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var from: TextView = itemView.findViewById(R.id.fromTV)
        var to: TextView = itemView.findViewById(R.id.toTV)
        var amount: TextView = itemView.findViewById(R.id.amountTV)
        var status: TextView = itemView.findViewById(R.id.successTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTransaction = userArrayList[position]

        holder.apply {
            from.text = currentTransaction.fromUser
            to.text = currentTransaction.toUser
            amount.text = currentTransaction.amountTransferred.toString()
            status.text = currentTransaction.status.toString()
        }

    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }
}