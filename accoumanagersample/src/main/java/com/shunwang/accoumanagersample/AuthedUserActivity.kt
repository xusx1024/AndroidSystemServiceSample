package com.shunwang.accoumanagersample

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*


class AuthedUserActivity : AppCompatActivity() {
  companion object {
    val KEY_ACCOUNT = "KEY_ACCOUNT"
    val ACCOUNT_TYPE = "com.shunwang.accountmanagerdemo"
    val AUTH_TOKEN_TYPE = "SWROOT"
    val AUTHORITY = "com.shunwang.accountmanagerdemo.provider"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    init()
  }

  private fun init() {
    val account: Account = intent.getParcelableExtra(KEY_ACCOUNT)
    val am: AccountManager = AccountManager.get(this)
    button_logout.setOnClickListener {
      var callback: AccountManagerCallback<Bundle> = AccountManagerCallback { future ->
        try {
          var token = future.result.getString(AccountManager.KEY_AUTHTOKEN)
          am.invalidateAuthToken(ACCOUNT_TYPE, token)
          finish()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
      am.getAuthToken(account, AUTH_TOKEN_TYPE, null, this@AuthedUserActivity, callback, null)
    }
    var callback: AccountManagerCallback<Bundle> = AccountManagerCallback { future ->
      try {
        var token = future.result.getString(AccountManager.KEY_AUTHTOKEN)
        var accountName = future.result.getString(AccountManager.KEY_ACCOUNT_NAME)
        name.text = accountName
        text_token.text = token
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    am.getAuthToken(account, AUTH_TOKEN_TYPE, null, this@AuthedUserActivity, callback, null)
  }
}
