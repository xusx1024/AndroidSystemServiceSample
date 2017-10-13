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

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.accounts.AccountManager.ERROR_CODE_BAD_ARGUMENTS
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.shunwang.accoumanagersample.AuthedUserActivity.Companion.AUTH_TOKEN_TYPE
import java.util.Random

/**
 * Fun:
 * Created by sxx.xu on 9/28/2017.
 */
class MyAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {
  private var mContext: Context = context

  override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?,
      authTokenType: String?, requiredFeatures: Array<out String>?,
      options: Bundle?): Bundle {
    var intent = Intent(mContext, LoginActivity::class.java)
    intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
    var bundle = Bundle()
    bundle.putParcelable(AccountManager.KEY_INTENT, intent)
    return bundle
  }

  /**
   * 在此处连接服务器获取token-本例中模拟直接返回
   */
  override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?,
      authTokenType: String?, options: Bundle?): Bundle {
    var bundle = Bundle()
    if (!authTokenType.equals(AUTH_TOKEN_TYPE)) {
      bundle.putInt(AccountManager.KEY_ERROR_CODE, ERROR_CODE_BAD_ARGUMENTS)
      bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authToken")
      return bundle
    }

    var am = AccountManager.get(mContext)
    var psw = am.getPassword(account)
    if (psw.isNotBlank()) {//请求服务器的token，此处模拟
      var random = Random()
      bundle.putString(AccountManager.KEY_AUTHTOKEN, random.nextLong().toString())
      bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account!!.type)
      bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
      return bundle
    }
    var intent = Intent(mContext, LoginActivity::class.java)
    bundle.putParcelable(AccountManager.KEY_INTENT, intent)
    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account!!.type)
    bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
    intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
    return bundle
  }

  override fun editProperties(response: AccountAuthenticatorResponse?,
      accountType: String?): Bundle? {
    return null
  }

  override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?,
      authTokenType: String?, options: Bundle?): Bundle? {
    return null
  }

  override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?,
      features: Array<out String>?): Bundle? {
    return null
  }

  override fun getAuthTokenLabel(authTokenType: String?): String? {
    return null
  }

  /**
   * 在此处连接服务器验证token
   */
  override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?,
      options: Bundle?): Bundle? {
    return null
  }
}