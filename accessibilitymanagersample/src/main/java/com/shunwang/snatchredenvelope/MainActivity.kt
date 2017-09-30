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

import android.app.AlertDialog
import android.app.Dialog
import android.app.Fragment
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.SwitchPreference
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

/**
 *
 */
class MainActivity : BaseSettingsActivity() {

  var mTipsDialog: Dialog? = null
  var mMainFragment: MainFragment? = null


  override fun getSettingsFragment(): Fragment {
    mMainFragment = MainFragment()
    return mMainFragment!!
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    var version = ""
    try {
      var info = packageManager.getPackageInfo(packageName, 0)
      version = " v" + info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    title = "抢红包" + version

    SREApplication.activityStartMain(this)

    var filter = IntentFilter()
    filter.addAction(Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_CONNECT)
    filter.addAction(Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_DISCONNECT)
    filter.addAction(Config.ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT)
    filter.addAction(Config.ACTION_NOTIFY_LISTENER_SERVICE_CONNECT)
    registerReceiver(sreConnectReceiver, filter)
  }

  val sreConnectReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
      if (isFinishing) {
        return
      }
      var action = intent.action
      Log.d("MainActivity", "receive-->" + action)
      if (Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_CONNECT == action) {
        if (mTipsDialog != null)
          mTipsDialog!!.dismiss()
      } else if (Config.ACTION_SANTCH_RED_ENVELOP_SERVICE_DISCONNECT == action) {
        showOpenAccessibilityServiceDialog()
      } else if (Config.ACTION_NOTIFY_LISTENER_SERVICE_CONNECT == action) {
        if (mMainFragment != null) {
          mMainFragment!!.updateNotifyPreference()
        }
      } else if (Config.ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT == action) {
        if (mMainFragment != null) {
          mMainFragment!!.updateNotifyPreference()
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    if (SnatchRedEnvelopeService.isRunning()) {
      if (mTipsDialog != null) {
        mTipsDialog!!.dismiss()
      }
    } else {
      showOpenAccessibilityServiceDialog()
    }

    var isAgreement = Config.getConfig(this).isAgreement()
    if (!isAgreement) showAgreementDialog()
  }


  override fun onPause() {
    super.onPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    try {
      unregisterReceiver(sreConnectReceiver)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    mTipsDialog = null
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    return super.onCreateOptionsMenu(menu)

    var item = menu.add(0, 0, 1, R.string.open_service_button)
    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)

    var notifyItem = menu.add(0, 3, 2, R.string.open_notify_service)
    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return super.onOptionsItemSelected(item)
    when (item!!.itemId) {
      0 -> {
        openAccessibilityServiceSettings()
        SREApplication.eventStatistics(this, "menu_service")
      }

      3 -> {
        openNotificationServiceSettings()
        SREApplication.eventStatistics(this, "menu_notify")
      }
    }

    return super.onOptionsItemSelected(item)
  }


  override fun onSupportNavigateUp(): Boolean {
    return super.onSupportNavigateUp()
  }


  fun showAgreementDialog() {
    var builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setTitle(R.string.agreement_title)
    builder.setMessage(getString(R.string.agreement_message, getString(R.string.app_name)))
    builder.setPositiveButton("同意", object : DialogInterface.OnClickListener {
      override fun onClick(dialog: DialogInterface?, which: Int) {
        Config.getConfig(applicationContext).setAgreement(true)
        SREApplication.eventStatistics(this@MainActivity, "agreement", "true")
      }

    })
    builder.setNegativeButton("不同意") { dialog, which ->
      Config.getConfig(applicationContext).setAgreement(false)
      SREApplication.eventStatistics(this@MainActivity, "agreement", "false")
    }
  }

  fun showOpenAccessibilityServiceDialog() {
    if (mTipsDialog != null && mTipsDialog!!.isShowing) {
      return
    }
    var view = layoutInflater.inflate(R.layout.dialog_tips_layout, null)
    view.setOnClickListener { openAccessibilityServiceSettings() }
    var builder = AlertDialog.Builder(this)
    builder.setTitle(R.string.open_service_title)
    builder.setView(view)
    builder.setPositiveButton(
        R.string.open_service_button) { dialog, which -> openAccessibilityServiceSettings() }
    mTipsDialog = builder.show()
  }

  fun openAccessibilityServiceSettings() {
    try {
      var intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
      startActivity(intent)
      if ("Meizu" == android.os.Build.BRAND) {
        Toast.makeText(this, R.string.meizu_tips, Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(this, R.string.tips, Toast.LENGTH_LONG).show()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun openNotificationServiceSettings() {
    try {
      var intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
      startActivity(intent)
      Toast.makeText(this, R.string.tips, Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }


  companion object
  class MainFragment : BaseSettingsFragment() {
    var notificationPref: SwitchPreference? = null
    var notificationChangeByUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      addPreferencesFromResource(R.xml.main)
      var wechatPref = findPreference(Config.KEY_ENABLE_WECHAT)
      wechatPref.setOnPreferenceChangeListener { preference, newValue ->
        if (newValue as Boolean && !SnatchRedEnvelopeService.isRunning()) {
          (activity as MainActivity).showOpenAccessibilityServiceDialog()
        }
        true
      }

      notificationPref = findPreference("KEY_NOTIFICATION_SERVICE_TEMP_ENABLE") as SwitchPreference
      notificationPref!!.setOnPreferenceChangeListener { preference, newValue ->
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
          Toast.makeText(activity, "该功能只支持安卓4.3以上的系统", Toast.LENGTH_SHORT).show()
          false
        }

        if (!notificationChangeByUser) {
          notificationChangeByUser = true
          true
        }

        var enable = newValue as Boolean
        Config.getConfig(activity).setNotificationServiceEnable(enable)

        if (enable && !SnatchRedEnvelopeService.isNotificationServiceRunning()) {
          (activity as MainActivity).openNotificationServiceSettings()
          false
        }

        SREApplication.eventStatistics(activity, "notify_service", newValue.toString())
        true
      }

      findPreference(
          "WECHAT_SETTINGS").onPreferenceClickListener = Preference.OnPreferenceClickListener {
        startActivity(Intent(activity, WechatSettingsActivity::class.java))
        true
      }
      findPreference(
          "NOTIFY_SETTINGS").onPreferenceClickListener = Preference.OnPreferenceClickListener {
        startActivity(Intent(activity, NotifySettingsActivity::class.java))
        true
      }
    }

    fun updateNotifyPreference() {
      if (notificationPref == null) return
      var running = SnatchRedEnvelopeService.isNotificationServiceRunning()
      var enable = Config.getConfig(activity).isEnableNotificationService()
      if (enable && running && !notificationPref!!.isChecked) {
        SREApplication.eventStatistics(activity, "notify_service", true.toString())
        notificationChangeByUser = false
        notificationPref!!.isChecked = true
      } else if ((!enable || !running) && notificationPref!!.isChecked) {
        notificationChangeByUser = false
        notificationPref!!.isChecked = false
      }
    }

    override fun onResume() {
      super.onResume()
      updateNotifyPreference()
    }
  }

  override fun isShowBack(): Boolean {
    return false
  }
}
