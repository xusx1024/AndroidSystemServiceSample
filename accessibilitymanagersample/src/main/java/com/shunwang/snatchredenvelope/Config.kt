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

import android.content.Context
import android.content.SharedPreferences

/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
class Config constructor(context: Context) {

  private var mContext: Context? = null
  private var preferences: SharedPreferences? = null

  companion object {
    val ACTION_SANTCH_RED_ENVELOP_SERVICE_DISCONNECT = "com.shunwang.snatchredenvelope.ACCESSBILITY_DISCONNECT"
    val ACTION_SANTCH_RED_ENVELOP_SERVICE_CONNECT = "com.shunwang.snatchredenvelope.ACCESSBILITY_CONNECT"

    val ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT = "com.shunwang.snatchredenvelope.NOTIFY_LISTENER_DISCONNECT"
    val ACTION_NOTIFY_LISTENER_SERVICE_CONNECT = "com.shunwang.snatchredenvelope.NOTIFY_LISTENER_CONNECT"

    val PREFERENCE_NAME = "config"
    val KEY_ENABLE_WECHAT = "KEY_ENABLE_WECHAT"
    val KEY_WECHAT_AFTER_OPEN_RED_ENVELOP = "KEY_WECHAT_AFTER_OPEN_RED_ENVELOP"
    val KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME"
    val KEY_WECHAT_AFTER_GET_RED_ENVELOP = "KEY_WECHAT_AFTER_GET_RED_ENVELOP"
    val KEY_WECHAT_MODE = "KEY_WECHAT_MODE"

    val KEY_NOTIFICATION_SERVICE_ENABLE = "KEY_NOTIFICATION_SERVICE_ENABLE"

    val KEY_NOTIFY_SOUND = "KEY_NOTIFY_SOUND"
    val KEY_NOTIFY_VIBRATE = "KEY_NOTIFY_VIBRATE"
    val KEY_NOTIFY_NIGHT_ENABLE = "KEY_NOTIFY_NIGHT_ENABLE"

    private val KEY_AGREEMENT = "KEY_AGREEMENT"

    val WX_AFTER_OPEN_RED_ENVELOP = 0//拆红包
    val WX_AFTER_OPEN_SEE = 1 //看大家手气
    val WX_AFTER_OPEN_NONE = 2 //静静地看着

    val WX_AFTER_GET_GOHOME = 0 //返回桌面
    val WX_AFTER_GET_NONE = 1


    val WX_MODE_0 = 0//自动抢
    val WX_MODE_1 = 1//抢单聊红包,群聊红包只通知
    val WX_MODE_2 = 2//抢群聊红包,单聊红包只通知
    val WX_MODE_3 = 3//通知手动抢


    private var current: Config? = null

    @Synchronized
    fun getConfig(context: Context): Config {
      if (current == null) {
        current = Config(context.applicationContext)
      }
      return current!!
    }
  }

  init {
    mContext = context
    preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
  }

  /** 是否启动微信抢红包 */
  fun isEnableWechat(): Boolean {
    return preferences!!.getBoolean(KEY_ENABLE_WECHAT, true)
  }

  /** 微信打开红包后的事件 */
  fun getWechatAfterOpenRedEnvelopeEvent(): Int {
    val defaultValue = 0
    val result = preferences!!.getString(KEY_WECHAT_AFTER_OPEN_RED_ENVELOP, defaultValue.toString())
    try {
      return Integer.parseInt(result)
    } catch (e: Exception) {
    }

    return defaultValue
  }

  /** 微信抢到红包后的事件 */
  fun getWechatAfterGetRedEnvelopeEvent(): Int {
    val defaultValue = 1
    val result = preferences!!.getString(KEY_WECHAT_AFTER_GET_RED_ENVELOP, defaultValue.toString())
    try {
      return Integer.parseInt(result)
    } catch (e: Exception) {
    }
    return defaultValue
  }

  /** 微信打开红包后延时时间 */
  fun getWechatOpenDelayTime(): Int {
    val defaultValue = 0
    val result = preferences!!.getString(KEY_WECHAT_DELAY_TIME, defaultValue.toString())
    try {
      return Integer.parseInt(result)
    } catch (e: Exception) {
    }

    return defaultValue
  }

  /** 获取抢微信红包的模式 */
  fun getWechatMode(): Int {
    val defaultValue = 0
    val result = preferences!!.getString(KEY_WECHAT_MODE, defaultValue.toString())
    try {
      return Integer.parseInt(result)
    } catch (e: Exception) {
    }

    return defaultValue
  }

  /** 是否启动通知栏模式 */
  fun isEnableNotificationService(): Boolean {
    return preferences!!.getBoolean(KEY_NOTIFICATION_SERVICE_ENABLE, false)
  }

  fun setNotificationServiceEnable(enable: Boolean) {
    preferences!!.edit().putBoolean(KEY_NOTIFICATION_SERVICE_ENABLE, enable).apply()
  }

  /** 是否开启声音 */
  fun isNotifySound(): Boolean {
    return preferences!!.getBoolean(KEY_NOTIFY_SOUND, true)
  }

  /** 是否开启震动 */
  fun isNotifyVibrate(): Boolean {
    return preferences!!.getBoolean(KEY_NOTIFY_VIBRATE, true)
  }

  /** 是否开启夜间免打扰模式 */
  fun isNotifyNight(): Boolean {
    return preferences!!.getBoolean(KEY_NOTIFY_NIGHT_ENABLE, false)
  }

  /** 免费声明 */
  fun isAgreement(): Boolean {
    return preferences!!.getBoolean(KEY_AGREEMENT, false)
  }

  /** 设置是否同意 */
  fun setAgreement(agreement: Boolean) {
    preferences!!.edit().putBoolean(KEY_AGREEMENT, agreement).apply()
  }

}