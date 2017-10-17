# Binder

#### Linux IPC手段

1. 管道：在创建时，分配一个page大小的内存，缓存区大小比较有限
2. 消息队列：信息复制两次，额外的CPU消耗，不适合频繁或数据量大的通信
3. 共享内存：无需复制，共享缓冲区直接附加到进程虚拟地址空间，速度快；但进程间的同步问题操作系统无法解决，必须各进程利用同步工具解决
4. 套接字：作为更通用的接口，传输效率低，主要用于不同机器或跨网络的通信
5. 信号量：常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。主要作为进程间以及同一进程内不同线程之间的同步手段
6. 信号：不适用于信息交换，更适用于进程中断控制，比如非法内存访问，杀死某个进程等

#### Binder的优点
* 安全
  * 传统的IPC的接收方法无法获取对方进程可靠的UID/PID，从而无法鉴别对方身份。使用传统IPC只能由用户在数据包里
  填入UID/PID，这样不可靠，容易被恶意程序利用。可靠的身份标记只有由IPC机制本身在内核中添加。
  * 传统IPC访问接入点是开放的，无法建立私有通道。比如命名管道的名称，system V的键值，socket的ip地址或文件名都
  是开放的，只要知道这些接入点的程序都可以和端建立连接，不管怎样都无法阻止恶意程序通过猜测接收方地址获得连接。
  * 基于Client-Server通信模式，传输过程只需一次拷贝，为发送方添加UID/PID身份，即支持实名Binder，也支持匿名Binder，安全性高。

* 面向对象
  * Binder使用了面向对象的思想来描述作为访问接入点(server中)和入口(client中)的Binder
  * Binder是一个实体位于Server中的对象，该对象提供了一套方法来实现对服务的请求，就像类成员函数，联想自定义Service中onBind的返回实例
  * Client中的入口，可以看成这个Binder对象的‘指针’，即引用，获得了该‘指针’，就可以调用该对象的方法访问Server。
  * 句柄，也可以用来表述Binder在Client中的存在方式。
  * 从通信角度看，Client中的Binder也可以看作是ServerBinder的‘代理’。、
  * 面向对象思想的引入将进程间通信转化为对某个Binder对象的引用调用该对象的方法，而其独特之处在于Binder对象是一
  个可跨进程引用的对象，它的实体位于一个进程中，而它的引用遍布系统的各个进程之中。
  * Binder的引用既可以是强类型，也可以是弱类型，而且可以从一个进程传给其他进程，让大家都能访问同一Server，就象
  将一个对象或引用赋值给另一个应用一样。
  * Binder模糊了进程边界，淡化了进程间通信过程，整个系统仿佛运行于同一个面向对象的程序之中。
  
#### 相关概念和数据结构
 ##### Binder系统组件
  * Service Manager 整个Android OS的所有服务管理程序，类似于DNS
  * Server，注册在Service Manager中
  * Client，与Service交互的客户端程序
  * Binder Driver，Binder驱动为面向对象的进程间通信提供底层支持
  ![Binder机制四组件关系图](../img/Binder组件.png)
  
 ###### Binder驱动
 代码位置：`drivers/misc/binder.c`
 
 通信的核心，尽管名为‘驱动’，实际上和硬件设备没有关系，只是实现方式和设备驱动程序是一样的：工作于内核态，支持open()
 mmap(),poll(),ioctl()
  
##### 其他概念  
* Bp，Binder Proxy
* Bn, Binder Native
* TLS, Thread Local Storage.这种空间每个线程有一个，而且线程间不共享这些空间。
* open(), 打开或创建文件。
* mmap(), 将一个文件或其他对象映射进内存。mumap执行相反的操作，删除特定地址区域的映射。当使用mmap映射文件到进程后，
可以直接操作这段虚拟地址进行读写操作，不必调用read、write等系统调用。注意：直接对该段内存读写，不会超过当前文件大小。
* poll(), 把当前的文件指针挂到等待队列。
* ioctl(), 设备驱动中对设备的I/O通道进行管理的函数。所谓对I/O通道管理，就是对设备的一些特性进行控制，例如串口的
传输波特率、马达的转速等。



#### 强烈推荐
1. [Android深入浅出之Binder机制](http://www.cnblogs.com/innost/archive/2011/01/09/1931456.html)
2. [Android进程间通信（IPC）机制Binder简要介绍和学习计划](http://blog.csdn.net/luoshengyang/article/details/6618363)
3. [Android Bander设计与实现 - 设计篇](http://blog.csdn.net/universus/article/details/6211589)
4. [彻底理解Android Binder通信架构](http://gityuan.com/2016/09/04/binder-start-service/)

























