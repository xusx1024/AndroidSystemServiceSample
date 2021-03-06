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