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

package com.shunwang.aidldemo2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.shunwang.aidldemo.HelloMsg;
import com.shunwang.aidldemo.MessageManager;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  TextView add;
  TextView content;
  MessageManager mMessageManager;
  List<HelloMsg> mMsgs;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    add = (TextView) findViewById(R.id.add);
    content = (TextView) findViewById(R.id.content);

    bind();
    add.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addMsg();
      }
    });

    content.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        try {
          mMsgs = mMessageManager.getMsg();
          content.setText(mMsgs.toString());
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }
    });
  }

  void addMsg() {
    HelloMsg msg = new HelloMsg("add", new Random().nextInt(100));
    try {
      mMessageManager.addMsg(msg);
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    Runnable r1 = new Runnable() {
      @Override public void run() {
        // todo
      }
    };
    Runnable r2 = new Runnable() {
      @Override public void run() {
        // todo
      }
    };
    Thread t = new Thread(r1);

    t.start();
    t.run();
  }

  void bind() {
    Intent intent = new Intent();
    intent.setAction("com.sw.msgaidl");
    intent.setPackage("com.shunwang.aidldemo");
    bindService(intent, new MyConnection(), Context.BIND_AUTO_CREATE);
  }

  class MyConnection implements ServiceConnection {

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      mMessageManager = MessageManager.Stub.asInterface(service);
    }

    @Override public void onServiceDisconnected(ComponentName name) {

    }
  }
}

