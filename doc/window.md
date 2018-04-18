`把window安排的明明白白`
----------------

---
WindowManager的用途:

1. 获取屏幕参数信息
2. 在根视图上添加悬浮层

---
主动调用WindowManager的引用链:

WindowManager是一个接口,继承自ViewManager

具体实现在WindowManagerImpl类来实现

在WindowManagerImpl类中,引用了WindowManagerGlobal中的add方法

WindowManagerGlobal 中引用ViewRootImpl中add方法

ViewRootImpl引用了IWindowSession中的add方法

IWindowSession是一个Binder实现

一个Session类继承了IWindowSession.Stub类,并且调用了WindowManagerService中的add方法

---

Activity的创建过程
- Activity的实例
- Window的创建
- xml的填充
- view的绘制

---

开启一个Activity,调用startActivity方法

其中涉及到Instrumentation的execStartActivity,AMS的startActivity

接着调用到Activity的onCreate方法

在onCreate中,我们通常setContentView

其实是通过window的具体实现类PhoneWindow把xml填充到了以DecorView为父视图里.






