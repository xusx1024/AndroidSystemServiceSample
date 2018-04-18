`把Activity相关的类安排的明明白白`

----------------------

- Activity
- ActivityStarter
- ActivityInfo
- ActivityRecord
- TaskRecord
- RecentTasks
- ActivityStack
- Instrumentation
- ActivityThread
- ActivityManagerService

---

无论通过点击应用程序图标来启动`Activity`,还是通过`Activity`内部调用`startActivity`接口来启动新的`Activity`,
都要借助应用程序框架层`ActivityManagerService`服务进程.

`ActivityManagerService`不但负责启动,而且负责管理.

---

> 简述

1. 无论是通过`Launcher`来启动`Activity`,还是通过`startActivity`来启动`Activity`,都通过`Binder`进程间通信进入到`ActivityManagerService`进程中,并且
调用`ActivityManagerService.startActivity`接口

2. `ActivityManagerService`调用`ActivityStarter.startActivityMayWait`来准备要启动的`Activity`的相关信息

3. `ActivityStack`通知`ApplicationThread`要进行`Activity`启动调度了,这里的`ApplicationThread`代表的是调用`ActivityManagerService.startActivity`接口
的进程,对于通过点击应用程序图标的情景来说,这个进程就是`Launcher`了,而对于通过在`Activity`内部调用`startActivity`的情景来说,这个进程就是该`Activity`所在的进程了

4. `ApplicationThread`不执行真正的启动操作,它通过调用`ActivityManagerService.activityPaused`接口进入到`ActivityManagerService`进程中,看看是否需要创建新的进程
来启动`Activity`

5. 对于通过点击应用程序图标来启动`Activity`的情景来说,`ActivityManagerService`在这一步中,会调用`startProcessLocked`来创建一个新的进程,而对于通过`Activity`内部
调用`startActivity`来启动新的`Activity`来说,这一步是不需要执行的,因为新的`Activity`就在原来的`Activity`所在的进程中进行启动

6. `ActivityManagerService`调用`ApplicationThread.scheduleLaunchActivity`接口,通知相应的进程执行启动`Activity`的操作

7. `ApplicationThread`把这个启动`Activity`的操作转发给`ActivityThread`,`ActivityThread`通过`ClassLoader`导入相应的`Activity`类,然后把它启动起来

---

> [8.0通过应用程序图标来启动`Activity`](https://www.jianshu.com/p/fbea00880da1)

1.












































