# zygot 孵化器

- [系统启动 zygote 进程](#系统启动-zygote-进程)
- [系统进入 zygote 进程](#系统进入-zygote-进程)
- [孵化 SystemServer 进程](#孵化-SystemServer-进程)
- [孵化 Application 进程](#孵化-Application-进程)
- [ Tips ](#-Tips-)

### 系统启动 zygote 进程
即android系统的启动，分为以下几步：
- _启动电源以及系统启动_
  按下电源时，引导芯片代码开始从预定义的地方(固化在ROM)开始执行。加载引导程序Bootloader到RAM，然后执行
- _引导程序Bootloader_
  引导程序是在Android OS开始运行前的一个小程序，它的主要作用是把OS拉起来并运行
- _Linux内核启动_
  内核启动时，设置缓存，被保护存储器，加载列表，加载驱动。当内核完成系统设置，它首先在系统文件中寻找init文件，
  然后启动root进程或者系统的第一个进程
- _init进程启动_
    - 创建一些文件夹并挂载设备
    - 初始化和启动属性服务(类似win平台的注册表)
    - 解析init.rc配置文件并启动zygote进程
    
[init.rc代码启动zygote进程](http://blog.csdn.net/fu_kevin0606/article/details/53469076)

[Android初始化语言](http://blog.csdn.net/hongbochen1223/article/details/56331690)

[init.rc源码](https://github.com/StephenRJ/cm12_system_core_rootdir)，基于aosp的，是cm的，cm现在也没了。。

![p](./img/app_main.png)

### 系统进入 zygote 进程
即zygote的初始化

zygote服务是在init进程中由命令启动的，init.rc文件中的main方法执行。
```
com.android.internal.os.ZygoteInit
```
  #### main 
  - registerZygoteSocket(socketName);//注册socket，用于进程间通信
  - startSystemServer(abiList, socketName);//启动系统服务


### 孵化 SystemServer 进程
okkk

### 孵化 Application 进程
okkk

### Tips   
- 为所有Java程序复刻一个虚拟机实例
- 应用程序的入口是`ActivityThread$main()`，而zygote就是为应用程序创建进程的
- 大部分应用程序由zygote创建进程，但是系统引导进程、init进程不是
  #### android中的zygote分两块
  1. C/C++编写的zygote，主要用来为系统服务和应用程序复刻进程的
  2. Java编写的zygote接口，负责为系统服务和应用程序调用C/C++ zygote接口执行复刻，创建虚拟机进程
  
  #### android中的service分两种
  1. NativeService
  2. SystemService，即本project示例集所研究