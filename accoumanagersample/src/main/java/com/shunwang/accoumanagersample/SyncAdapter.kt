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

package com.shunwang.accoumanagersample

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle

/**
 * SyncAdapter不会自动做数据传输，它只是封装你的代码，以便框架可以在后台调用，而不需要你的应用介入。
 * 当同步框架准备要同步应用的数据时候，他会调用 [onPerformSync]
 *
 * Created by sxx.xu on 9/29/2017.
 */
class SyncAdapter(context: Context, boolean: Boolean) : AbstractThreadedSyncAdapter(context,
    boolean) {


  private var mContentResolver = context.contentResolver

  /**
   * 连接服务器
   * 下载上次数据
   * 处理数据冲突
   * 清理临时文件和缓存
   *
   * @param account 与本次触发事件关联的[Account]对象，如果你的服务器不需要账号，直接无视即可
   * @param extras 包含一些标志位的[Bundle] 对象
   * @param authority 系统中 ContentProvider ，一般是你自己应用中的 ContentProvider 对应的authority
   * @param provider authority对应的 [ContentProviderClient]，它是ContentProvider的一个轻量接口，
   * 具有与 ContentResolver 相同的功能。如果是用ContentProvider保存的数据，你可以用这个对象连接到
   * ContentProvider，否则无视就好
   * @param syncResult 用来将同步的结果传给同步框架
   */
  override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?,
      provider: ContentProviderClient?, syncResult: SyncResult?) {
    //模拟和服务器的交互
  }
}