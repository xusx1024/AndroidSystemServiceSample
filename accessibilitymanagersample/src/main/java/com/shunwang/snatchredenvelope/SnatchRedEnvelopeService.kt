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

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import com.shunwang.snatchredenvelope.job.AccessbilityJob
import com.shunwang.snatchredenvelope.job.WechatAccessbilityJob
import java.util.ArrayList
import java.util.HashMap

/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
class SnatchRedEnvelopeService : AccessibilityService() {
  companion object {
    val TAG: String = "SnatchRedEnvelope"
    private val ACCESSBILITY_JOBS = arrayOf<Class<*>>(WechatAccessbilityJob::class.java)
    private var service: SnatchRedEnvelopeService? = null

    fun handeNotificationPosted(notificationService: IStatusBarNotification) {
      if (notificationService == null)
        return
      if (service == null || service!!.mPkgAccessbilityJobMap == null) {
        return
      }
      var pack: String = notificationService.getPackageName()
      var job: AccessbilityJob = service!!.mPkgAccessbilityJobMap!!.get(pack!!)!!

      job.onNotificationPosted(notificationService)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun isRunning(): Boolean {
      if (service == null) return false

      var accessibilitymanager: AccessibilityManager = service!!.getSystemService(
          Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
      var info: AccessibilityServiceInfo = service!!.serviceInfo
      if (info == null) return false

      var list: List<AccessibilityServiceInfo> = accessibilitymanager.getEnabledAccessibilityServiceList(
          AccessibilityServiceInfo.FEEDBACK_GENERIC)
      var iterator: Iterator<AccessibilityServiceInfo> = list.iterator()

      var isConnect: Boolean = false
      while (iterator.hasNext()) {
        var i: AccessibilityServiceInfo = iterator.next()
        if (i.id.equals(info.id)) {
          isConnect = true
          break
        }
      }
      return isConnect
    }

    fun isNotificationServiceRunning(): Boolean {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
        return false
      }
      try {
        return SRENotificationService.isRunning()
      } catch (e: Exception) {
        e.printStackTrace()
      }
      return false
    }
  }


  private var mAccessbilityJobs: ArrayList<AccessbilityJob>? = null
  private var mPkgAccessbilityJobMap: HashMap<String, AccessbilityJob>? = null

  fun getConfig(): Config {
    return Config.getConfig(this)
  }


  override fun onCreate() {
    super.onCreate()
    mAccessbilityJobs = ArrayList<AccessbilityJob>()
    mPkgAccessbilityJobMap = HashMap<String, AccessbilityJob>()

    for (clazz in ACCESSBILITY_JOBS) {
      try {
        val o = clazz.newInstance()
        if (o is AccessbilityJob) {
          (o as AccessbilityJob).onCreateJob(this)
          mAccessbilityJobs!!.add(o)
          mPkgAccessbilityJobMap!!.put(o.getTargetPackageName(), o)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.e(TAG, "snatch red envelope destory")
    if (mPkgAccessbilityJobMap != null) {
      mPkgAccessbilityJobMap!!.clear()
    }

    if (mAccessbilityJobs != null && mAccessbilityJobs!!.isNotEmpty()) {
      for (job in mAccessbilityJobs!!) {
        job.onStopJob()
      }
      mAccessbilityJobs!!.clear()
    }

    service = null
    mAccessbilityJobs = null
    mPkgAccessbilityJobMap = null
    val intent = Intent(Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_DISCONNECT)
    sendBroadcast(intent)
  }

  override fun onInterrupt() {
    Log.e(TAG, "snatch red envelope service interrupt")
    Toast.makeText(this, "中断抢红包服务", Toast.LENGTH_SHORT).show()
  }

  override fun onServiceConnected() {
    super.onServiceConnected()
    service = this
    sendBroadcast(Intent(Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_CONNECT))
    Toast.makeText(this, "已连接抢红包服务", Toast.LENGTH_SHORT).show()
  }

  override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    if (BuildConfig.DEBUG) {
      Log.d(TAG, "事件--->" + event)
    }
    val pkn = event!!.getPackageName().toString()
    if (mAccessbilityJobs != null && mAccessbilityJobs!!.isNotEmpty()) {
      if (!getConfig().isAgreement()) return

      for (job in mAccessbilityJobs!!) {
        if (pkn.equals(job.getTargetPackageName()) && job.isEnable()) {
          job.onReceiveJob(event)
        }
      }
    }
  }
}