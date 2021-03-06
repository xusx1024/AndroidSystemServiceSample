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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fun:
 * Created by sxx.xu on 10/13/2017.
 */

public class MainActivity extends Activity {

  static final String TAG = "MainActivity";
  static int color = Color.parseColor("#0090CC");
  TextView textView;
  View.OnClickListener mListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
    }
  };
  private IMyAidlInterface myInterface;
  private MyServiceConnection mMyServiceConnection;

  //给TextView设置部分字体大小和颜色
  public static void setPartialSizeAndColor(TextView tv, int start, int end, int textSize,
      int textColor, final View.OnClickListener clickListener) {
    String s = tv.getText().toString();
    Spannable spannable = new SpannableString(s);
    //spannable.setSpan(new AbsoluteSizeSpan(textSize, false), start, end,
    //    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    //spannable.setSpan(new ForegroundColorSpan(Color.BLUE), start, end,
    //    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannable.setSpan(new ClickableSpan() {

      @Override public void updateDrawState(TextPaint ds) {
        ds.setColor(color);
      }

      @Override public void onClick(View widget) {
        clickListener.onClick(widget);
      }
    }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv.setText(spannable);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = (TextView) findViewById(R.id.tv_msg);
    String text = "123456789023456789";
    textView.setText(text);
    textView.setClickable(true);

    setPartialSizeAndColor(textView, text.length() - 6, text.length(), 50, color, mListener);
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    mMyServiceConnection = new MyServiceConnection();
    textView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        bindService(new Intent(MainActivity.this, MyService.class), mMyServiceConnection,
            Context.BIND_AUTO_CREATE);
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbindService(mMyServiceConnection);
  }

  class MyServiceConnection implements ServiceConnection {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      myInterface = IMyAidlInterface.Stub.asInterface(service);
      Log.i(TAG, "链接service成功");
      try {
        String s = myInterface.getName();
        textView.setText(s);
        Log.i(TAG, "从service得到的字符串：" + s);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override public void onServiceDisconnected(ComponentName name) {
      Log.i(TAG, "链接service断开");
    }
  }
}
