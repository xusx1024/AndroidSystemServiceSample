package com.shunwang.accoumanagersample

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.shunwang.accoumanagersample.AuthedUserActivity.Companion.ACCOUNT_TYPE
import kotlinx.android.synthetic.main.register_activity.button_register
import kotlinx.android.synthetic.main.register_activity.edit_name
import kotlinx.android.synthetic.main.register_activity.edit_psw

/**
 * Fun:
 * Created by sxx.xu on 9/28/2017.
 */
class RegisterActivity : AppCompatActivity() {
  companion object {
    val REQUEST_ACTIVITY_CODE = 0
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.register_activity)

    button_register.setOnClickListener {
      var userName = edit_name.text.toString()
      var psw = edit_psw.text.toString()
      if (userName.isBlank()) {
        Toast.makeText(this@RegisterActivity, "账号不能为空", Toast.LENGTH_LONG).show()
        return@setOnClickListener
      }
      if (psw.isBlank()) {
        Toast.makeText(this@RegisterActivity, "密码不能为空", Toast.LENGTH_LONG).show()
        return@setOnClickListener
      }

      /**
       * 去服务端注册，成功后添加，此处省略
       */
      var account = Account(userName, ACCOUNT_TYPE)
      var am = AccountManager.get(this@RegisterActivity)
//            在本demo的SDK25版本里，已经移除`AUTHENTICATE_ACCOUNTS`权限
//            if (Build.VERSION.SDK_INT < 22) {
//                if (ActivityCompat.checkSelfPermission(this@RegisterActivity, AUTHENTICATE_ACCOUNTS)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this@RegisterActivity,
//                            arrayOf(Manifest.permission.GET_ACCOUNTS), LoginActivity.ACCOUNT_PERMISSION_CODE)
//                } else {
//                    am.addAccountExplicitly(account, psw, null)
//                }
//            } else {
//                am.addAccountExplicitly(account, psw, null)
//            }
      am.addAccountExplicitly(account, psw, null)
      finish()
    }
  }

  /**
   * Take care of popping the fragment back stack or finishing the activity
   * as appropriate.
   */
  override fun onBackPressed() {
    super.onBackPressed()
    setResult(Activity.RESULT_OK)
  }
}