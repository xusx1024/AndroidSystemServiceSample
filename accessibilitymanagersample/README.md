# 无障碍辅助服务-AccessibilityManager

#### 能做什么 ####
 
- 抢红包
- 自动安装apk(偷装流氓软件)
- UI自动化测试
- 监听短信内容
- 帮助有障碍人士(...)

#### 重要的类 ####

`android.accessibilityservice.AccessibilityService`
##### 重要的方法 #####

`onAccessibilityEvent`<br/>
`onInterrupt`

#### 清单文件固定配置 ####

        <service
            android:name=".SnatchRedEnvelopeService"
            android:enabled="true"
            android:exported="true"
            android:label="@string/service_name"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService"/>
            </intent-filter>

            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/snatch_red_envelope_service_config"/>
        </service>

##### 4.0之后配置服务参数 meta-data #####

	<?xml version="1.0" encoding="utf-8"?>
	<accessibility-service
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_description"
    android:accessibilityEventTypes="typeNotificationStateChanged|typeWindowStateChanged|typeWindowContentChanged|typeWindowsChanged"
    android:packageNames="com.tencent.mm,com.tencent.mobileqq"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:notificationTimeout="100"
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"/>

此处相当于配置一个`AccessibilityServiceInfo`,代码如下：<br/>

	@Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.tencent.mm","com.tencent.mobileqq"}; 
        serviceInfo.notificationTimeout=100;
        setServiceInfo(serviceInfo);
    }


