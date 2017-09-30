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

import android.annotation.TargetApi
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR2
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.shunwang.snatchredenvelope.BuildConfig
import com.shunwang.snatchredenvelope.Config
import com.shunwang.snatchredenvelope.IStatusBarNotification
import com.shunwang.snatchredenvelope.SREApplication
import com.shunwang.snatchredenvelope.SnatchRedEnvelopeService
import com.shunwang.snatchredenvelope.util.AccessibilityHelper
import com.shunwang.snatchredenvelope.util.NotifyHelper


/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
class WechatAccessbilityJob : BaseAccessbilityJob() {


  companion object {
    val TAG: String = "WechatAccessbilityJob"
    val WECHAT_PACKAGENAME: String = "com.tencent.mm"
    val RED_ENVELOPE_TEXT_KEY: String = "[微信红包]"
    val BUTTON_CLASS_NAME: String = "android.widget.Button"
    val USE_ID_MIN_VERSION: Int = 700
    val WINDOW_NONE: Int = 0
    val WINDOW_LUCKYMONEY_RECEIVEUI: Int = 1
    val WINDOW_LUCKYMONEY_DETAIL: Int = 2
    val WINDOW_LAUNCHER: Int = 3
    val WINDOW_OTHER: Int = -1

  }

  private var mCurrentWindow: Int = WINDOW_NONE
  private var isReceivingRedEnvelope: Boolean? = false
  private var mWechatPackageInfo: PackageInfo? = null
  private var mHandler: Handler? = null

  private var broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      updatePackageInfo()
    }
  }

  override fun onCreateJob(service: SnatchRedEnvelopeService) {
    super.onCreateJob(service)
    updatePackageInfo()

    var filter: IntentFilter = IntentFilter()
    filter.addDataScheme("package")
    filter.addAction("android.intent.action.PACKAGE_ADDED")
    filter.addAction("android.intent.action.PACKAGE_REPLACED")
    filter.addAction("android.intent.action.PACKAGE_REMOVED")

    getContext().registerReceiver(broadcastReceiver, filter)
  }

  override fun getTargetPackageName(): String {
    return WECHAT_PACKAGENAME
  }

  override fun onReceiveJob(event: AccessibilityEvent) {

    val eventType: Int = event.eventType
    if (eventType.equals(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)) {
      var data: Parcelable = event.parcelableData
      if (data == null || data !is Notification) return

      if (SnatchRedEnvelopeService.isNotificationServiceRunning() && getConfig().isEnableNotificationService()) {
        return
      }

      var texts: List<CharSequence> = event.text
      if (texts.isNotEmpty()) {
        var text: String = texts.get(0).toString()
        notificationEvent(text, data as Notification)
      }
    } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
      openRedEnvelope(event)
    } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
      if (mCurrentWindow != WINDOW_LAUNCHER) {
        return
      }
      if (isReceivingRedEnvelope!!) {
        handleChatListRedEnvelope()
      }
    }
  }

  @TargetApi(JELLY_BEAN_MR2)
  fun handleChatListRedEnvelope() {
    var mode = getConfig().getWechatMode()
    if (mode == Config.WX_MODE_3) return
    var nodeInfo: AccessibilityNodeInfo? = service!!.rootInActiveWindow ?: return

    if (mode != Config.WX_MODE_0) {
      var isMember = isMemberChatUi(nodeInfo!!)
      if (mode == Config.WX_MODE_1 && isMember) {
        return
      } else if (mode == Config.WX_MODE_2 && !isMember) {
        return
      }
    }

    var list = nodeInfo!!.findAccessibilityNodeInfosByText("领取红包")
    if (list != null && list.isNotEmpty()) {
      var node = AccessibilityHelper.findNodeInfoByText(nodeInfo!!, "[微信红包]")
      if (node != null) {
        if (BuildConfig.DEBUG) {
          Log.i(TAG, "--->微信红包:$node")
        }
        isReceivingRedEnvelope = true
        AccessibilityHelper.performClick(nodeInfo)
      }
    } else if (list != null) {
      if (isReceivingRedEnvelope!!) {
        var node = list.get(list.size - 1)
        AccessibilityHelper.performClick(node)
        isReceivingRedEnvelope = false
      }
    }
  }

  fun isMemberChatUi(nodeInfo: AccessibilityNodeInfo): Boolean {
    if (nodeInfo == null)
      return false
    var id = "com.tencent.mm:id/ces"
    var wv = getWechatVersion()
    if (wv < 680) {
      id = "com.tencent.mm:id/ew"
    } else if (wv <= 700) {
      id = "com.tencent.mm:id/cbo"
    }

    var title: String? = null
    var target = AccessibilityHelper.findNodeInfoById(nodeInfo, id)
    if (target != null) {
      title = target.text.toString()
    }

    var list: List<AccessibilityNodeInfo> = nodeInfo.findAccessibilityNodeInfosByText("返回")

    if (list != null && list.isNotEmpty()) {
      var parent: AccessibilityNodeInfo? = null
      for (node: AccessibilityNodeInfo in list) {
        if ("android.widget.ImageView" != node.className) {
          continue
        }
        var desc = node.contentDescription
        if ("返回" != desc) {
          continue
        }
        parent = node.parent
        break
      }

      if (parent != null) {
        parent = parent.parent
      }
      if (parent != null) {
        if (parent.childCount >= 2) {
          var node = parent.getChild(1)
          if ("android.widget.TextView" == node.className) {
            title = node.text.toString()
          }
        }
      }
    }

    if (title != null && title.endsWith(")")) return true
    return false
  }

  fun getWechatVersion(): Int {
    if (mWechatPackageInfo == null)
      return 0
    return mWechatPackageInfo!!.versionCode
  }

  fun openRedEnvelope(event: AccessibilityEvent) {
    if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI" == event.className) {
      mCurrentWindow = WINDOW_LUCKYMONEY_RECEIVEUI
      handleLuckyMoneyReceive()
    } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI" == event.className) {
      mCurrentWindow = WINDOW_LUCKYMONEY_DETAIL
      if (getConfig().getWechatAfterGetRedEnvelopeEvent() == Config.WX_AFTER_GET_GOHOME) {
        AccessibilityHelper.performHome(service!!)
      }
    } else if ("com.tencent.mm.ui.LauncherUI" == event.className) {
      mCurrentWindow = WINDOW_LAUNCHER
      handleChatListRedEnvelope()
    } else {
      mCurrentWindow = WINDOW_OTHER
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  fun handleLuckyMoneyReceive() {
    var nodeInfo: AccessibilityNodeInfo? = service!!.rootInActiveWindow ?: return

    var targetNode: AccessibilityNodeInfo? = null
    var event = getConfig().getWechatAfterOpenRedEnvelopeEvent()
    var wechatVersion = getWechatVersion()
    if (event == Config.WX_AFTER_OPEN_RED_ENVELOP) {
      if (wechatVersion < USE_ID_MIN_VERSION) {
        targetNode = AccessibilityHelper.findNodeInfoByText(nodeInfo!!, "拆红包")
      } else {
        var buttonId = "com.tencent.mm:id/b43"
        if (wechatVersion == 700) {
          buttonId = "com.tencent.mm:id/b2c"
        }
        if (buttonId != null) {
          targetNode = AccessibilityHelper.findNodeInfoById(nodeInfo!!, buttonId)
        }

        if (targetNode == null) {
          var textNode = AccessibilityHelper.findNodeInfosByTexts(nodeInfo!!, "发了一个红包", "给你发了一个红包",
              "发了一个红包，金额随机")
          if (textNode != null) {
            for (i in 0..textNode.childCount) {
              var node = textNode.getChild(i)
              if (BUTTON_CLASS_NAME == node.className) {
                targetNode = node
                break
              }
            }
          }
        }
        if (targetNode == null) {
          targetNode = AccessibilityHelper.findParentNodeInfosByClassName(nodeInfo,
              BUTTON_CLASS_NAME)
        }
      }
    } else if (event == Config.WX_AFTER_OPEN_SEE) {
      if (getWechatVersion() < USE_ID_MIN_VERSION) {
        targetNode = AccessibilityHelper.findNodeInfoByText(nodeInfo!!, "看看大家的手气")
      }
    } else if (event == Config.WX_AFTER_OPEN_NONE) {
      return
    }

    if (targetNode != null) {
      val n: AccessibilityNodeInfo = targetNode
      var sDelayTime = getConfig().getWechatOpenDelayTime()
      if (sDelayTime != 0) {
        getHandler().postDelayed(Runnable { AccessibilityHelper.performClick(n) },
            sDelayTime as Long)
      } else {
        AccessibilityHelper.performClick(n)
      }
      if (event == Config.WX_AFTER_OPEN_RED_ENVELOP) {
        SREApplication.eventStatistics(getContext(), "open_red_envelope")
      } else {
        SREApplication.eventStatistics(getContext(), "open_see")
      }
    }

  }

  fun getHandler(): Handler {
    if (mHandler == null) {
      mHandler = Handler(Looper.getMainLooper())
    }
    return mHandler!!
  }

  override fun onStopJob() {
    try {
      getContext().unregisterReceiver(broadcastReceiver)
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }

  @TargetApi(JELLY_BEAN_MR2)
  override fun onNotificationPosted(sbn: IStatusBarNotification) {
    var nf: Notification = sbn.getNotification()
    var text: String = sbn.getNotification().tickerText as String
    notificationEvent(text, nf)
  }

  fun notificationEvent(ticker: String, nf: Notification) {
    var text: String = ticker
    var index: Int = text.indexOf(":")
    if (index != -1) {
      text = text.substring(index + 1)
    }
    text = text.trim()
    if (text.contains(RED_ENVELOPE_TEXT_KEY)) {
      newRedEnvelopeNotification(nf)
    }

  }

  fun newRedEnvelopeNotification(notification: Notification) {
    isReceivingRedEnvelope = true
    var pendingIntent: PendingIntent = notification.contentIntent
    var lock: Boolean = NotifyHelper.isLockScreen(getContext())
    if (!lock) {
      //以下是精华，将微信的通知栏消息打开
      NotifyHelper.send(pendingIntent)
    } else run {
      NotifyHelper.showNotify(getContext(), notification.tickerText.toString(), pendingIntent)
    }

    if (lock || getConfig().getWechatMode() !== Config.WX_MODE_0) {
      NotifyHelper.playEffect(getContext(), getConfig())
    }

  }

  override fun isEnable(): Boolean {
    return getConfig().isEnableWechat()
  }

  fun updatePackageInfo() {
    try {
      mWechatPackageInfo = getContext().packageManager.getPackageInfo(WECHAT_PACKAGENAME, 0)
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
  }
}