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

package com.shunwang.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Fun:
 * Created by sxx.xu on 10/13/2017.
 */

public class MessageService extends Service {

  static final String TAG = "msgaidl";
  private List<HelloMsg> mMsgs = new ArrayList<>();
  private MyBinder mBinder;

  @Override public void onCreate() {
    super.onCreate();
    mBinder = new MyBinder();
    for (int i = 0, j = 3; i < j; i++) {
      HelloMsg msg = new HelloMsg("msg-" + i, i);
      mMsgs.add(msg);
    }
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }

  class MyBinder extends MessageManager.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
        String aString) throws RemoteException {

    }

    @Override public List<HelloMsg> getMsg() throws RemoteException {
      synchronized (this) {
        if (mMsgs != null) {
          return mMsgs;
        }
      }
      return new ArrayList<>();
    }

    @Override public void addMsg(HelloMsg msg) throws RemoteException {
      if (mMsgs == null) {
        mMsgs = new ArrayList<>();
      } else {
        Log.i(TAG, msg.toString());
        mMsgs.add(msg);
      }
    }
  }
}
