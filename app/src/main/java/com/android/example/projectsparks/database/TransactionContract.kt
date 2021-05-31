package com.android.example.projectsparks.database

import android.provider.BaseColumns

class TransactionContract private constructor() {
    object TransactionEntry : BaseColumns {
        const val TABLE_NAME = "transaction_table"

        const val COLUMN_TRANS_FROM = "from_name"
        const val COLUMN_TRANS_TO = "to_name"
        const val COLUMN_TRANS_AMT = "amount"
        const val COLUMN_TRANS_STATUS = "status"
    }
}