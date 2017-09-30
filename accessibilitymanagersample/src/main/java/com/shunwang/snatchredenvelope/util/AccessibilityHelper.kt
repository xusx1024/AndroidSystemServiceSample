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

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.text.TextUtils
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.reflect.Field

/**
 * Fun:
 * Created by sxx.xu on 4/24/2017.
 */
object AccessibilityHelper {


  fun findNodeInfoById(nodeInfo: AccessibilityNodeInfo, resId: String): AccessibilityNodeInfo {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      var list: List<AccessibilityNodeInfo> = nodeInfo.findAccessibilityNodeInfosByViewId(resId)
      if (list != null && list.isNotEmpty()) {
        return list!!.get(0)
      }
    }
    return null!!
  }

  fun findNodeInfoByText(nodeInfo: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      var list: List<AccessibilityNodeInfo> = nodeInfo.findAccessibilityNodeInfosByText(text)
      if (list != null && list.isNotEmpty()) {
        return list!!.get(0)
      }
    }
    return null
  }

  fun findNodeInfosByText(nodeInfo: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
    val list = nodeInfo.findAccessibilityNodeInfosByText(text)
    if (list == null || list.isEmpty()) {
      return null
    }
    return list[0]
  }

  fun findNodeInfosByTexts(nodeInfo: AccessibilityNodeInfo,
      vararg texts: String): AccessibilityNodeInfo? {
    for (key in texts) {
      val info = findNodeInfosByText(nodeInfo, key)
      if (info != null) {
        return info
      }
    }
    return null
  }


  fun findNodeInfosByClassName(nodeInfo: AccessibilityNodeInfo,
      className: String): AccessibilityNodeInfo? {
    if (TextUtils.isEmpty(className)) {
      return null
    }
    for (i in 0..nodeInfo.childCount - 1) {
      val node = nodeInfo.getChild(i)
      if (className == node.className) {
        return node
      }
    }
    return null
  }

  fun findParentNodeInfosByClassName(nodeInfo: AccessibilityNodeInfo?,
      className: String): AccessibilityNodeInfo? {
    if (nodeInfo == null) {
      return null
    }
    if (TextUtils.isEmpty(className)) {
      return null
    }
    if (className == nodeInfo.className) {
      return nodeInfo
    }
    return findParentNodeInfosByClassName(nodeInfo.parent, className)
  }

  var sSourceNodeField: Field? = null

  init {
    try {
      sSourceNodeField = AccessibilityNodeInfo::class.java.getDeclaredField("mSourceNodeId")
      sSourceNodeField!!.isAccessible = true
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun getSourceNodeId(nodeInfo: AccessibilityNodeInfo): Long {
    if (sSourceNodeField == null) return -1
    try {
      return sSourceNodeField!!.getLong(nodeInfo)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return -1
  }

  fun getViewIdResourceName(nodeInfo: AccessibilityNodeInfo): String? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      return nodeInfo.viewIdResourceName
    }
    return null
  }

  fun performHome(service: AccessibilityService) {
    if (service == null)
      return
    service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
  }

  fun performBack(service: AccessibilityService) {
    if (service == null) return
    service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
  }

  fun performClick(nodeInfo: AccessibilityNodeInfo) {
    if (nodeInfo == null) return
    if (nodeInfo.isClickable)
      nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    else
      performClick(nodeInfo.parent)
  }

}



