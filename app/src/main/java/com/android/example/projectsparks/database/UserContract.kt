package com.android.example.projectsparks.database

import android.provider.BaseColumns

class UserContract private constructor() {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"

        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_ACCOUNT_NUMBER = "accountNo"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_IFSC_CODE = "ifscCode"
        const val COLUMN_USER_PHONE_NO = "phoneNo"
        const val COLUMN_USER_ACCOUNT_BALANCE = "balance"
    }
}
