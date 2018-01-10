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

package com.shunwang.pat.Lev_YI;

import java.util.Scanner;

/**
 * Fun: 3n + 1 猜想
 * Created by sxx.xu on 12/21/2017.
 */

public class Num1001 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int num = in.nextInt();
    int times = 0;

    if (num == 1) {
      System.out.println(times);
    } else if (num % 2 == 0) {
      while (num != 1) {
        num = num / 2;
        times++;
      }
      System.out.println(times);
    } else {
      while (num % 2 != 0) num = 3 * num + 1;
      while (num != 1) {
        num = num / 2;
        times++;
      }
      System.out.println(times);
    }
  }
}
