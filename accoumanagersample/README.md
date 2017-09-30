# AccountManager示例项目的问题
#### 关于 ####
本项目实现了：在android手机系统的'账户'功能里，可以增加一个用于同步账户信息的自定义账户。

功能：
- 增
- 删
- 查
- 验
- 同步

#### 使用步骤 ####
- 实现AbstractAccountAuthenticator
	- addAccount
	- getAuthToken //从服务端获取token，demo中为随机数
	- confirmCredentials //验证token是否失效
- 实现一个验证服务，在`onBind`中返回上一步的`iBinder`
- 清单文件中配置改服务，示例如下：

 	 	<service android:name=".AuthenticatorService">
            <intent-filter>
                <action
                    android:name="android.accounts.AccountAuthenticator"/>
            </intent-filter>
            <meta-data
                android:name="android.accounts.AccountAuthenticator"
                android:resource="@xml/authenticator"/>
        </service>

- 元数据文件：xml/authenticator，配置`accountType`和图标资源

		<account-authenticator xmlns:android="http://schemas.android.com/apk/res/android"
	                       android:accountType="com.shunwang.accountmanagerdemo"
	                       android:icon="@android:drawable/ic_menu_camera"
	                       android:label="@string/app_name"
	                       android:smallIcon="@android:drawable/ic_menu_camera"
		/>
#### 开发问题 ####
##### 动态请求授权，没有授权对话框出现 #####
检查`Manifest.permission.GET_ACCOUNTS`的值是否为`PackageManager.PERMISSION_GRANTED`，即是否为0。
在否定的情况，使用`ActivityCompat.requestPermissions`调用授权相关API，结果没有任何反应。
排查发现，在`AndroidManifest.xml`中，忘记添加对改权限的声明。
添加`<uses-permission android:name="android.permission.GET_ACCOUNTS"/>`即可。
注：`ActivityCompat.requestPermissions`在API>=23的情况有不同的实现。

##### 使用系统已有主题问题 #####
`java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.`
在Activity中，打算使用系统提供的主题，得到该异常。
因为你的Activity继承了`AppCompatActivity`，所以在要使用的资源前增加`Theme.AppCompat`。
如果这个包下没有你需要的资源，那么可参考系统的自行实现。

##### 直接添加账户 #####
在调用`addAccountExplicitly`方法是，看到API的注释：
<p>
If targeting your app to work on API level 22 and before,
AUTHENTICATE_ACCOUNTS permission is needed for those platforms. See docs
for this function in API level 22.
</P>

我的编译SDK版本为25，在这个版本里已经移除了所需要的`AUTHENTICATE_ACCOUNTS`.据`stackoverflow`是在23移除，未验证。

#### 动态权限相关 ####
##### 分组的权限，得一即得所有 #####
1. 联系人：`group:android.permission-group.CONTACTS`
	- permission:android.permission.WRITE_CONTACTS
	- permission:android.permission.GET_ACCOUNTS    
    - permission:android.permission.READ_CONTACTS
2. 电话：`group:android.permission-group.PHONE`
	- permission:android.permission.READ_CALL_LOG
	- permission:android.permission.READ_PHONE_STATE
	- permission:android.permission.CALL_PHONE
	- permission:android.permission.WRITE_CALL_LOG
	- permission:android.permission.USE_SIP
	- permission:android.permission.PROCESS_OUTGOING_CALLS
	- permission:com.android.voicemail.permission.ADD_VOICEMAIL
3. 日历：`group:android.permission-group.CALENDAR`
	- permission:android.permission.READ_CALENDAR
	- permission:android.permission.WRITE_CALENDAR
4. 相机：`group:android.permission-group.CAMERA`
	- permission:android.permission.CAMERA
5. 传感器：`group:android.permission-group.SENSORS`
	- permission:android.permission.BODY_SENSORS
6. 磁盘：`group:android.permission-group.STORAGE`
	- permission:android.permission.READ_EXTERNAL_STORAGE
    - permission:android.permission.WRITE_EXTERNAL_STORAGE
7. 话筒：`group:android.permission-group.MICROPHONE`
	- permission:android.permission.RECORD_AUDIO
8. 短信：`group:android.permission-group.SMS`
	- permission:android.permission.READ_SMS
    - permission:android.permission.RECEIVE_WAP_PUSH
    - permission:android.permission.RECEIVE_MMS
    - permission:android.permission.RECEIVE_SMS
    - permission:android.permission.SEND_SMS
    - permission:android.permission.READ_CELL_BROADCASTS
9. 位置：`group:android.permission-group.LOCATION`
	- permission:android.permission.ACCESS_FINE_LOCATION
    - permission:android.permission.ACCESS_COARSE_LOCATION
##### 普通权限，声明即可获取 #####
- android.permission.ACCESS_LOCATION_EXTRA_COMMANDS
- android.permission.ACCESS_NETWORK_STATE
- android.permission.ACCESS_NOTIFICATION_POLICY
- android.permission.ACCESS_WIFI_STATE
- android.permission.ACCESS_WIMAX_STATE
- android.permission.BLUETOOTH
- android.permission.BLUETOOTH_ADMIN
- android.permission.BROADCAST_STICKY
- android.permission.CHANGE_NETWORK_STATE
- android.permission.CHANGE_WIFI_MULTICAST_STATE
- android.permission.CHANGE_WIFI_STATE
- android.permission.CHANGE_WIMAX_STATE
- android.permission.DISABLE_KEYGUARD
- android.permission.EXPAND_STATUS_BAR
- android.permission.FLASHLIGHT
- android.permission.GET_ACCOUNTS
- android.permission.GET_PACKAGE_SIZE
- android.permission.INTERNET
- android.permission.KILL_BACKGROUND_PROCESSES
- android.permission.MODIFY_AUDIO_SETTINGS
- android.permission.NFC
- android.permission.READ_SYNC_SETTINGS
- android.permission.READ_SYNC_STATS
- android.permission.RECEIVE_BOOT_COMPLETED
- android.permission.REORDER_TASKS
- android.permission.REQUEST_INSTALL_PACKAGES
- android.permission.SET_TIME_ZONE
- android.permission.SET_WALLPAPER
- android.permission.SET_WALLPAPER_HINTS
- android.permission.SUBSCRIBED_FEEDS_READ
- android.permission.TRANSMIT_IR
- android.permission.USE_FINGERPRINT
- android.permission.VIBRATE
- android.permission.WAKE_LOCK
- android.permission.WRITE_SYNC_SETTINGS
- com.android.alarm.permission.SET_ALARM
- com.android.launcher.permission.INSTALL_SHORTCUT
- com.android.launcher.permission.UNINSTALL_SHORTCUT

#### kotlin数组 ####
是容器类，kotlin提供了八大基本类型的数组类型，比如：IntArray。

#### kotlin列表 ####
List是不可变的，如果需要clear等操作，List要声明为MutableList类型。

#### kotlin的internal修饰词 ####
`public`,`private`,`protected`作用类似。`internal`作用在同一model里，比如AS中的一个model，看起来
要作用域大于`protected`，小于`public`.

#### Activity在API21以后的持久化 ####

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)

    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)

这些方法提供了app在关机重启后恢复现场的能力，需要在清单文件中配置：`android:persistableMode="persistAcrossReboots"`

#### space控件 ####

以前写占位UI，使用View标签，或者干脆一个空的TextView，现在有了专业的space 。

#### android platform原生资源 ####

这些都是极好的，符合设计原则的资源，尽量复用。

#### javadoc的hide ####
如果一个`public static final`修饰的域无法引用，会发现他的javadoc有`/** @hide */`.
由于Google api变化较快，这些域不稳定，所以没有暴露，使用hide说明程序有着较差的兼容性。
如果要使用hide的API，可以编译android framework，得到class.jar文件，然后引入工程 。

#### 代码格式化 ####
使用square公司的规范：[SquareAndroid.xml](https://raw.githubusercontent.com/square/java-code-styles/master/configs/codestyles/SquareAndroid.xml)

#### 代码风格检查 ####
使用Google公司的规范：[google_check.xml](https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml)

#### javadoc的{@link} ####
类注释上的link没有下划线，也没有跳转。
`kotlin`的文档语言叫做：[KDoc](http://www.liying-cn.net/kotlin/docs/reference/kotlin-doc.html)。结合了javaDoc和MarkDown。

多练练。

#### KDoc一些基本小点 ####
- KDoc 不支持 @deprecated 标签. 请使用 @Deprecated 注解来代替.
- 上一条说的`@link`没有生效，是因为`.kt`文件的内联标记是使用`[]`来实现的
- javaDoc中的`#` 即KDoc中的`.`

#### OSGI ####
- 面向Java的动态模型系统
- 代表为：Apache Felix
- android本来是运行在`dalvik`或者`art`虚拟机上，并非标准的JVM
- 如何把android app跑在ApacheFelix，参考：[在Android中使用OSGi框架](http://log4think.com/use-apache-felix-in-android/)