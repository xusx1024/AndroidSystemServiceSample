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

import android.app.Fragment
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.ListPreference

/**
 * Fun:
 * Created by sxx.xu on 5/2/2017.
 */
class WechatSettingsActivity : BaseSettingsActivity() {
  override fun getSettingsFragment(): Fragment {
    return WechatSettingsFragment()
  }

  companion object
  class WechatSettingsFragment : BaseSettingsFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      addPreferencesFromResource(R.xml.wechat_settings)

      //微信红包模式
      val wxMode: ListPreference = findPreference(Config.KEY_WECHAT_MODE) as ListPreference
      wxMode.setOnPreferenceChangeListener { preference, newValue ->
        var value = newValue.toString().toInt()
        preference.setSummary(wxMode.entries[value])
        SREApplication.eventStatistics(activity, "wx_mode", newValue.toString())
        true
      }
      wxMode.setSummary(wxMode.entries[wxMode.value.toInt()])

      //打开微信红包后
      val wxAfterOpenPre: ListPreference = findPreference(
          Config.KEY_WECHAT_AFTER_OPEN_RED_ENVELOP) as ListPreference
      wxAfterOpenPre.setOnPreferenceChangeListener { preference, newValue ->
        var value = newValue.toString().toInt()
        preference.setSummary(wxAfterOpenPre.entries[value])
        SREApplication.eventStatistics(activity, "wx_after_open", newValue.toString())
        true
      }
      wxAfterOpenPre.setSummary(wxAfterOpenPre.entries[wxAfterOpenPre.value.toInt()])

      val wxAfterGetPre: ListPreference = findPreference(
          Config.KEY_WECHAT_AFTER_GET_RED_ENVELOP) as ListPreference
      wxAfterGetPre.setOnPreferenceChangeListener { preference, newValue ->
        var value = newValue.toString().toInt()
        preference.setSummary(wxAfterGetPre.entries[value])
        SREApplication.eventStatistics(activity, "wx_after_get", newValue.toString())
        true
      }
      wxAfterGetPre.setSummary(wxAfterGetPre.entries[wxAfterGetPre.value.toInt()])

      val delayEditTextPre: EditTextPreference = findPreference(
          Config.KEY_WECHAT_DELAY_TIME) as EditTextPreference
      delayEditTextPre.setOnPreferenceChangeListener { preference, newValue ->

        if ("0".equals(newValue)) {
          preference.setSummary("")
        } else {
          preference.setSummary("已延时" + newValue + "毫秒")
        }
        SREApplication.eventStatistics(activity, "wx_delay_time", newValue.toString())
        true
      }

      var delay = delayEditTextPre.text
      if ("0" == delay) {
        delayEditTextPre.setSummary("")
      } else {
        delayEditTextPre.setSummary("已延时" + delay + "毫秒")
      }
    }
  }
}