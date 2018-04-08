`把window安排的明明白白`
----------------

---
WindowManager的用途:

1. 获取屏幕参数信息
2. 在根视图上添加悬浮层

WindowManager是一个接口,继承自ViewManager

具体实现在WindowManagerImpl类来实现

在WindowManagerImpl类中,引用了WindowManagerGlobal中的add方法

WindowManagerGlobal 中引用ViewRootImpl中add方法

ViewRootImpl引用了IWindowSession中的add方法

IWindowSession是一个Binder实现

一个Session类继承了IWindowSession.Stub类,并且调用了WindowManagerService中的add方法

---

WindowManagerService中的添加流程:
- 判断是否有添加权限
- 如果有权限,继续
