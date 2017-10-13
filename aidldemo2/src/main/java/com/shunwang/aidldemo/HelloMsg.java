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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Fun:
 * Created by sxx.xu on 10/13/2017.
 */

public class HelloMsg implements Parcelable {
  public static final Creator<HelloMsg> CREATOR = new Creator<HelloMsg>() {
    @Override public HelloMsg createFromParcel(Parcel in) {
      return new HelloMsg(in);
    }

    @Override public HelloMsg[] newArray(int size) {
      return new HelloMsg[size];
    }
  };
  private String name;
  private int age;

  protected HelloMsg(Parcel in) {
    name = in.readString();
    age = in.readInt();
  }

  public HelloMsg(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(age);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public String toString() {
    return "HelloMsg{" + "name='" + name + '\'' + ", age=" + age + '}';
  }

  /**
   * 此处read的顺序，应该和write的顺序相同
   */
  public void readFromParcel(Parcel dest) {// AIDL的通信TAG为out的标记
    name = dest.readString();
    age = dest.readInt();
  }
}
