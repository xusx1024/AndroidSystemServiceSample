# Binder

#### Linux IPC手段

1. 管道：在创建时，分配一个page大小的内存，缓存区大小比较有限
2. 消息队列：信息复制两次，额外的CPU消耗，不适合频繁或数据量大的通信
3. 共享内存：无需复制，共享缓冲区直接附加到进程虚拟地址空间，速度快；但进程间的同步问题操作系统无法解决，必须各进程利用同步工具解决
4. 套接字：作为更通用的接口，传输效率低，主要用于不同机器或跨网络的通信
5. 信号量：常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。主要作为进程间以及同一进程内不同线程之间的同步手段
6. 信号：不适用于信息交换，更适用于进程中断控制，比如非法内存访问，杀死某个进程等

#### Binder的优点

* 传统的IPC的接收方法无法获取对方进程可靠的UID/PID，从而无法鉴别对方身份。使用传统IPC只能由用户在数据包里
填入UID/PID，这样不可靠，容易被恶意程序利用。可靠的身份标记只有由IPC机制本身在内核中添加。
* 传统IPC访问接入点是开放的，无法建立私有通道。比如命名管道的名称，system V的键值，socket的ip地址或文件名都
是开放的，只要知道这些接入点的程序都可以和端建立连接，不管怎样都无法阻止恶意程序通过猜测接收方地址获得连接。
* 基于Client-Server通信模式，传输过程只需一次拷贝，为发送方添加UID/PID身份，即支持实名Binder，也支持匿名Binder，安全性高。

#### Binder系统组件
  Client、Server、Service Manager、Binder Driver

#### 强烈推荐
1. [Android深入浅出之Binder机制](http://www.cnblogs.com/innost/archive/2011/01/09/1931456.html)
2. [Android进程间通信（IPC）机制Binder简要介绍和学习计划](http://blog.csdn.net/luoshengyang/article/details/6618363)
3. [Android Bander设计与实现 - 设计篇](http://blog.csdn.net/universus/article/details/6211589)
4. [彻底理解Android Binder通信架构](http://gityuan.com/2016/09/04/binder-start-service/)

























