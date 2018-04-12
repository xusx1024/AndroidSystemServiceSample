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

package com.shengxing.kotlinapi.code;

/**
 * Fun:
 * Created by sxx.xu on 4/11/2018.
 */
public class DCLSingleTon {
  private static boolean flag = true;
  private static volatile DCLSingleTon instance = null;

  private DCLSingleTon() {
    if (flag) {
      flag = !flag;
    } else {
      throw new RuntimeException("refect second instance error");
    }
  }

  public static DCLSingleTon getInstance() {
    if (instance == null) {
      synchronized (DCLSingleTon.class) {
        if (instance == null) {
          instance = new DCLSingleTon();
        }
      }
    }
    return instance;
  }

  private Object readResolve() {
    return instance;
  }
}

