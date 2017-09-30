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

package com.shunwang.snatchredenvelope.util

import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.Vibrator
import com.shunwang.snatchredenvelope.Config
import java.util.Calendar

/**
 * Fun:
 * Created by sxx.xu on 4/21/2017.
 */
class NotifyHelper {
  companion object {
    var sVibrator: Vibrator? = null
    var sKeyguardManager: KeyguardManager? = null
    var sPowerManager: PowerManager? = null

    fun sound(context: Context) {
      try {
        var player: MediaPlayer = MediaPlayer.create(context,
            Uri.parse("file:///system/media/audio/ui/camera_click.ogg"))
        player.start()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    fun vibrator(context: Context) {
      if (sVibrator == null) {
        sVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
      }
      sVibrator!!.vibrate(longArrayOf(100, 10, 100, 1000), -1)
    }

    fun isNightTime(): Boolean {
      var cal: Calendar = Calendar.getInstance()
      var hour: Int = cal.get(Calendar.HOUR_OF_DAY)
      if (hour >= 23 || hour < 7) {
        return true
      }
      return false
    }

    fun getKeyguardManager(context: Context): KeyguardManager {
      if (sKeyguardManager == null) {
        sKeyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
      }
      return sKeyguardManager!!
    }

    fun getPowerManager(context: Context): PowerManager {
      if (sPowerManager == null) {
        sPowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
      }
      return sPowerManager!!
    }

    /** 是否为锁屏或黑屏状态 */
    fun isLockScreen(context: Context): Boolean {
      val km = getKeyguardManager(context)

      return km.inKeyguardRestrictedInputMode() || !isScreenOn(context)
    }

    fun isScreenOn(context: Context): Boolean {
      val pm = getPowerManager(context)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
        return pm.isInteractive
      } else {
        return pm.isScreenOn
      }
    }

    /** 播放效果、声音与震动 */
    fun playEffect(context: Context, config: Config) {
      //夜间模式，不处理
      if (NotifyHelper.isNightTime() && config.isNotifyNight()) {
        return
      }

      if (config.isNotifySound()) {
        sound(context)
      }
      if (config.isNotifyVibrate()) {
        vibrator(context)
      }
    }

    /** 显示通知 */
    fun showNotify(context: Context, title: String, pendingIntent: PendingIntent) {

    }

    /** 执行PendingIntent事件 */
    fun send(pendingIntent: PendingIntent) {
      try {
        pendingIntent.send()
      } catch (e: PendingIntent.CanceledException) {
        e.printStackTrace()
      }

    }
  }
}