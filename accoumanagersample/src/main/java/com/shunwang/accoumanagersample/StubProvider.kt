package com.shunwang.accoumanagersample

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Fun:与sync adapter协作
 * 可以用来存储本地数据
 * 也可以作为一个虚拟实现，通过sync adapter安装自己的存储形式来传输数据
 * Created by sxx.xu on 9/29/2017.
 */
class StubProvider : ContentProvider() {

  override fun insert(uri: Uri?, values: ContentValues?): Uri? {
    return null
  }

  override fun query(uri: Uri?, projection: Array<out String>?, selection: String?,
      selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
    return null
  }

  override fun onCreate(): Boolean {
    return true
  }

  override fun update(uri: Uri?, values: ContentValues?, selection: String?,
      selectionArgs: Array<out String>?): Int {
    return 0
  }

  override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
    return 0
  }

  override fun getType(uri: Uri?): String {
    return String()
  }
}