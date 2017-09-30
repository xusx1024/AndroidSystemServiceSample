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

package com.shunwang.accoumanagersample

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

/**
 * Fun:
 * Created by sxx.xu on 9/28/2017.
 */
class AccountAdapter(context: Context, resource: Int, objects: MutableList<Account>) : ArrayAdapter<Account>(context, resource, objects) {

    private var mData: MutableList<Account>? = null
    private var mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mManager: AccountManager = AccountManager.get(context)
    private var mResource: Int? = null

    init {
        mResource = resource
        mData = objects
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = mInflater.inflate(mResource!!, parent, false)
        }
        val textName = convertView!!.findViewById(R.id.text_name) as TextView
        val btnDelete = convertView!!.findViewById(R.id.button_delete) as ImageButton

        textName.text = mData!![position].name
        btnDelete.setOnClickListener{
            if (Build.VERSION.SDK_INT < 22) {
                mManager.removeAccount(mData!![position], null, null)
            } else {
                mManager.removeAccountExplicitly(mData!![position])
            }
        }
        return convertView!!
    }

    fun getData(): MutableList<Account> {
        return mData!!
    }

}