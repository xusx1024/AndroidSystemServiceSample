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

/**
 * Fun:
 * Created by sxx.xu on 10/13/2017.
 */

public class MyService extends Service {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return new Proxy().asBinder();
  }

  static class MyBinder extends IMyAidlInterface.Stub {
    @Override public String getName() throws RemoteException {
      Log.i("MainActivity", "service get Name");
      return "test";
    }
  }
}

class Proxy implements com.shunwang.aidldemo.IMyAidlInterface{

  @Override public String getName() throws RemoteException {
    return "test";
  }

  @Override public IBinder asBinder() {
    return null;
  }
}
