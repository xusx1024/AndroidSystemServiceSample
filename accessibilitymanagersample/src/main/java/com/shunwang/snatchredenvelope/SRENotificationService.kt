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

import android.annotation.TargetApi
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

/**
 * Fun:
 * Created by sxx.xu on 4/24/2017.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class SRENotificationService : NotificationListenerService() {

  /**
   * API之前为abstract必须实现，我们的MINSDK为18
   */
  override fun onNotificationPosted(sbn: StatusBarNotification?) {
    super.onNotificationPosted(sbn)
  }

  /**
   * API之前为abstract必须实现，我们的MINSDK为18
   */
  override fun onNotificationRemoved(sbn: StatusBarNotification?) {
    super.onNotificationRemoved(sbn)
  }

  companion object {
    val TAG: String = "SRENotificationService"
    var service: SRENotificationService? = null
    fun isRunning(): Boolean {
      return service != null
    }
  }

}