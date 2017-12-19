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

package com.shunwang.alarmmanagersample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Fun:
 * Created by sxx.xu on 12/19/2017.
 */
class MyReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    var msg = intent!!.getStringExtra("msg")
    Log.e("alarm receiver:" , msg)
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
  }
}