package com.shunwang.accoumanagersample

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Fun:
 * Created by sxx.xu on 9/28/2017.
 */
class AuthenticatorService : Service() {

  private var mAuthenticator = MyAuthenticator(this)
  override fun onBind(intent: Intent?): IBinder {
    return mAuthenticator.iBinder
  }
}