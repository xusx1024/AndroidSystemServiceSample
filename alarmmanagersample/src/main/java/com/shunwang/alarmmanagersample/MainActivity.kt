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

package com.shunwang.alarmmanagersample

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.message
import kotlinx.android.synthetic.main.activity_main.navigation

/**
 * 一个简单的示例,具体使用,综合需求对比job scheduler
 */
class MainActivity : AppCompatActivity() {

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_home -> {
        message.setText(R.string.title_home)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        message.setText(R.string.title_dashboard)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        message.setText(R.string.title_notifications)
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    initAlarm()
    this.bindService()
  }

  private fun initAlarm() {
    var intent: Intent = Intent("ELITOR_CLOCK")
    intent.putExtra("msg", "请按时吃药")
    var pi = PendingIntent.getBroadcast(this, 0, intent, 0)
    var am: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    am.setRepeating(RTC_WAKEUP, System.currentTimeMillis(), 5000, pi)
  }
}
