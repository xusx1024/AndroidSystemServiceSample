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

package com.shunwang.audiomanagersample

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils

/**
 * Fun:
 * Created by sxx.xu on 1/10/2018.
 */
class MediaPlaybackService : MediaBrowserServiceCompat() {
  companion object {
    val MY_MEDIA_ROOT_ID = "media_root_id"
    val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    val EMPTY_MEDIA_ROOT_ID = "nooooop"
    val LOG_TAG = "MediaPlaybackService"
  }

  var mMediaSession: MediaSessionCompat? = null
  var mStateBuilder: PlaybackStateCompat.Builder? = null

  override fun onCreate() {
    super.onCreate()
    mMediaSession = MediaSessionCompat(this, LOG_TAG)
    mMediaSession!!.setFlags(
        MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
    mStateBuilder = PlaybackStateCompat.Builder().setActions(
        PlaybackStateCompat.ACTION_PLAY or
            PlaybackStateCompat.ACTION_PLAY_PAUSE
    )
    mMediaSession!!.setPlaybackState(mStateBuilder!!.build())
//    mMediaSession!!.setCallback(MySessionCallback())
    sessionToken = mMediaSession!!.sessionToken
  }

  override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaItem>>) {
      if(TextUtils.equals(EMPTY_MEDIA_ROOT_ID,parentId)){
        result.sendResult(null)
        return
      }
  }

  override fun onGetRoot(clientPackageName: String, clientUid: Int,
      rootHints: Bundle?): BrowserRoot? {
    return if (allowBrowsing(clientPackageName, clientUid)) {
      BrowserRoot(MY_MEDIA_ROOT_ID, null)
    } else {
      BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

  }

  /**
   * 控制指定包名的访问等级
   */
  private fun allowBrowsing(clientPackageName: String, clientUid: Int): Boolean {
    return clientPackageName != EMPTY_MEDIA_ROOT_ID
  }
}