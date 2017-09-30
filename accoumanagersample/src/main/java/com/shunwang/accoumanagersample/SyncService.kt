package com.shunwang.accoumanagersample

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Fun:把SyncAdapter的binder对象传给framework
 * Created by sxx.xu on 9/29/2017.
 */
class SyncService : Service() {

  companion object {
    var sSyncAdapter: SyncAdapter? = null
    val sSyncAdapterLock: Object = Object()
  }

  /**
   * Called by the system when the service is first created.  Do not call this method directly.
   */
  override fun onCreate() {
    super.onCreate()
    synchronized(sSyncAdapterLock) {
      if (sSyncAdapter == null) {
        sSyncAdapter = SyncAdapter(applicationContext, true)
      }
    }
  }

  override fun onBind(intent: Intent?): IBinder {
    return sSyncAdapter!!.syncAdapterBinder
  }
}