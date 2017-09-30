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

package com.shunwang.snatchredenvelope.job

import android.view.accessibility.AccessibilityEvent
import com.shunwang.snatchredenvelope.IStatusBarNotification
import com.shunwang.snatchredenvelope.SnatchRedEnvelopeService

/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
interface AccessbilityJob {
  fun getTargetPackageName(): String
  fun onCreateJob(service: SnatchRedEnvelopeService)
  fun onReceiveJob(event: AccessibilityEvent)
  fun onStopJob()
  fun onNotificationPosted(service: IStatusBarNotification)
  fun isEnable(): Boolean
}