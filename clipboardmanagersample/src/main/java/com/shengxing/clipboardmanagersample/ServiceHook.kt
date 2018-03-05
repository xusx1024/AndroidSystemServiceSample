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
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Fun:使用JDK的动态代理类
 * Created by sxx.xu on 2/9/2018.
 */
class ServiceHook(mBase: IBinder, interfaceName: String, isStub: Boolean,
    invocationHandler: InvocationHandler) : InvocationHandler {

  companion object {
    val TAG = "ServiceHook"

    class HookHandler : InvocationHandler {
      override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }
  }

  private var mBase: IBinder = mBase
  private var mStub: Class<Any>? = null
  private var mInterface: Class<Any>? = null
  private var mInvocationHandler: InvocationHandler = invocationHandler

  override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
    if ("queryLocalInterface" == method.name) {
//      return newProxyInstance(proxy.javaClass.classLoader, Class()[]{ mInterface },
//          HookHandler())
    }
    return method!!.invoke(proxy, args)

  }

  init {
    try {
      this.mInterface = Class.forName(interfaceName) as Class<Any>?
      this.mStub = Class.forName(
          String.format("%%s", interfaceName, if (isStub) "$isStub" else "")) as Class<Any>?
    } catch (e: ClassNotFoundException) {
      e.printStackTrace()
    }
  }
}