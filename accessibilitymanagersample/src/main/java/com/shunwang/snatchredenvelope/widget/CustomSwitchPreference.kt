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

package com.shunwang.snatchredenvelope.widget

import android.content.Context
import android.os.Build
import android.preference.SwitchPreference
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Switch

/**
 * Fun:
 * Created by sxx.xu on 5/3/2017.
 */
class CustomSwitchPreference : SwitchPreference {

  constructor(ctx: Context) : super(ctx, null) {
  }

  constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
  }

  constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle) {
  }

  override fun onBindView(view: View?) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      var viewGroup = view as ViewGroup
      clearListenerInViewGroup(viewGroup)
    }
    super.onBindView(view)
  }

  fun clearListenerInViewGroup(viewGroup: ViewGroup) {
    if (null == viewGroup) return
    var count = viewGroup.childCount
    for (n in 0..count) {
      var childView = viewGroup.getChildAt(n)
      if (childView is Switch) {
        val switchView = childView
        switchView.setOnCheckedChangeListener(null)
        return
      } else if (childView is ViewGroup) {
        var childGroup = childView
        clearListenerInViewGroup(childGroup)
      }
    }
  }

}

























