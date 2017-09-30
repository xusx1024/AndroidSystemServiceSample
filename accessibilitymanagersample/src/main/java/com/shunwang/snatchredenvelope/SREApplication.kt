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

package com.shunwang.snatchredenvelope

import android.app.Activity
import android.app.Application
import android.content.Context

/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
class SREApplication : Application() {

  override fun onCreate() {
    super.onCreate()
  }

  companion object {
    fun showShare(activity: Activity) {}
    fun checkUpdate(activity: Activity) {}
    fun activityStartMain(activity: Activity) {}
    fun activityCreateStatistics(activity: Activity) {}
    fun activityResumeStatistics(activity: Activity) {}
    fun activityPauseStatistics(activity: Activity) {}
    fun eventStatistics(context: Context, event: String) {}
    fun eventStatistics(context: Context, event: String, tag: String) {}
  }
}