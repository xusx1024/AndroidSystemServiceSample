/*
 *  Copyright (C) 2017 The  sxxxxxxxxxu's  Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.shunwang.accoumanagersample

import android.Manifest
import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Toast
import com.shunwang.accoumanagersample.AuthedUserActivity.Companion.ACCOUNT_TYPE
import com.shunwang.accoumanagersample.AuthedUserActivity.Companion.AUTH_TOKEN_TYPE
import com.shunwang.accoumanagersample.AuthedUserActivity.Companion.KEY_ACCOUNT
import com.shunwang.accoumanagersample.RegisterActivity.Companion.REQUEST_ACTIVITY_CODE
import kotlinx.android.synthetic.main.activity_auth.expandAll
import kotlinx.android.synthetic.main.activity_auth.login
import kotlinx.android.synthetic.main.activity_auth.name
import kotlinx.android.synthetic.main.activity_auth.password
import kotlinx.android.synthetic.main.activity_auth.register

/**
 * Fun:
 * Created by sxx.xu on 9/28/2017.
 */
class LoginActivity : AccountAuthenticatorActivity() {

  private var mPop: AccountPopWindow? = null
  private var mManager: AccountManager? = null


  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    setContentView(R.layout.activity_auth)
    init()
  }

  private fun init() {
    mManager = AccountManager.get(this)

    register.setOnClickListener {
      var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
      startActivityForResult(intent, REQUEST_ACTIVITY_CODE)
    }
    login.setOnClickListener {
      var userName = name.text.toString()
      var psw = password.text.toString()
      if (userName.isBlank()) {
        Toast.makeText(this@LoginActivity, "账号不能为空", Toast.LENGTH_LONG).show()
        return@setOnClickListener
      }
      if (psw.isBlank()) {
        Toast.makeText(this@LoginActivity, "密码不能为空", Toast.LENGTH_LONG).show()
        return@setOnClickListener
      }

      var account = Account(userName, ACCOUNT_TYPE)
      var callback = AccountManagerCallback<Bundle> { future ->
        try {
          //该变量没有用到，但是可以在用户名密码错误的情况下，提前触发下面的java.lang.IllegalArgumentException: getAuthToken not supported
          future.result.getString(AccountManager.KEY_AUTHTOKEN)
          var intent = Intent(this@LoginActivity, AuthedUserActivity::class.java)
          var account1 = Account(future.result.getString(AccountManager.KEY_ACCOUNT_NAME)
              , future.result.getString(AccountManager.KEY_ACCOUNT_TYPE))
          intent.putExtra(KEY_ACCOUNT, account1)
          startActivity(intent)
        } catch (e: Exception) {
          Toast.makeText(this@LoginActivity, "error:" + e.localizedMessage,
              Toast.LENGTH_LONG).show()
          e.printStackTrace()
        }
      }
      mManager!!.getAuthToken(account, AUTH_TOKEN_TYPE, null, this@LoginActivity, callback, null)
    }

    var datas: MutableList<Account> = ArrayList<Account>()
    mPop = AccountPopWindow(this, datas)

    expandAll.setOnClickListener {
      var code = ActivityCompat.checkSelfPermission(this@LoginActivity,
          Manifest.permission.GET_ACCOUNTS)
      if (code != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this@LoginActivity,
            arrayOf(Manifest.permission.GET_ACCOUNTS), ACCOUNT_PERMISSION_CODE)
      } else {
        showPop()
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?,
      grantResults: IntArray?) {
    if (requestCode == ACCOUNT_PERMISSION_CODE) {
      if (grantResults!!.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        showPop()
      }
    }
  }

  private fun showPop() {
    var accounts = mManager!!.getAccountsByType(ACCOUNT_TYPE)
    mPop!!.updateData(accounts.toMutableList())
    mPop!!.showAsDropDown(expandAll)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
  }

  inner class AccountPopWindow(context: Context, accounts: MutableList<Account>) : PopupWindow(
      context) {
    private var mAdapter: AccountAdapter? = null

    init {
      width = ViewGroup.LayoutParams.MATCH_PARENT
      height = ViewGroup.LayoutParams.WRAP_CONTENT
      var inflater: LayoutInflater = context.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      var listView = inflater.inflate(R.layout.pop_account, null) as ListView
      mAdapter = AccountAdapter(this@LoginActivity, R.layout.item_account, accounts)
      listView.adapter = mAdapter
      listView.setOnItemClickListener { parent, _, position, _ ->
        var account: Account = parent.adapter.getItem(position) as Account

        name.setText(account.name)
        password.setText(mManager!!.getPassword(account))

        dismiss()
      }
      contentView = listView
      isTouchable = true
      isOutsideTouchable = true
      setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun updateData(accounts: MutableList<Account>) {
      mAdapter!!.getData().clear()
      mAdapter!!.getData().addAll(accounts)
    }
  }

  companion object {
    var ACCOUNT_PERMISSION_CODE = 0
  }
}