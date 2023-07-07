package com.submission.dicodingstory.util

import android.content.Context

import com.submission.dicodingstory.model.Account
class AccountPref(context: Context) {




    private val accPref = context.getSharedPreferences(ACC_NAME, Context.MODE_PRIVATE)




    fun setAcc(data: Account){
        val user = accPref.edit()
        user.putString(ID, data.userId)
        user.putString(NAME, data.name)
        user.putString(EMAIL, data.email)
        user.putString(TOKEN, data.token)
        user.apply()
    }

    fun getAcc(): Account =
        Account(
            userId = accPref.getString(ID, "").toString(),
            name = accPref.getString(NAME, "").toString(),
            email = accPref.getString(EMAIL, "").toString(),
            token = accPref.getString(TOKEN, "").toString()
        )


    fun setSignIn(value: Boolean){
        val user = accPref.edit()
        user.putBoolean(LOGIN, value)
        user.apply()
    }

    fun getSignIn(): Boolean =
        accPref.getBoolean(LOGIN, false)

    fun signOut()
    {
        val user = accPref.edit()

        user.clear()

        user.apply()
    }
    companion object{
        private const val ACC_NAME = "prefs_name"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "emial"
        private const val TOKEN = "token"
        private const val LOGIN = "login"
    }
}