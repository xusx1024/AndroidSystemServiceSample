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

package com.shengxing.clipboardmanagersample

import android.os.IBinder
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Fun: 反射的方式获取对应的service
 * Created by sxx.xu on 2/9/2018.
 */
class ServiceManager {

  object instance
  companion object {
    private var sGetServiceMethod: Method? = null
    private var sCacheService: MutableMap<String, IBinder>? = null
    private var sServiceManager: Class<ServiceManager>? = null
  }

  init {
    try {
      sServiceManager = Class.forName("android.os.ServiceManager") as Class<ServiceManager>
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun getService(serviceName: String): IBinder? {
    if (sServiceManager == null) {
      return null
    }
    if (sGetServiceMethod == null) {
      try {
        sGetServiceMethod = sServiceManager!!.getDeclaredMethod("getService", String.javaClass)
        sGetServiceMethod!!.isAccessible = true
      } catch (e: NoSuchMethodException) {
        e.printStackTrace()
      }
    }
    return null
  }

  fun setService(serviceName: String, service: IBinder) {
    if (sServiceManager == null)
      return
    if (sCacheService == null) {
      try {
        val sCache: Field = sServiceManager!!.getDeclaredField("sCache")
        sCache.isAccessible = true
        sCacheService = sCache.get(null) as MutableMap<String, IBinder>?
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    sCacheService!!.remove(serviceName)
    sCacheService!!.put(serviceName, service)
  }
}