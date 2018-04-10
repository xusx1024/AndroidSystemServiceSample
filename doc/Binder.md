# Binder

#### Linux IPC手段 

1. 管道：在创建时，分配一个page大小的内存，缓存区大小比较有限
2. 消息队列：信息复制两次，额外的CPU消耗，不适合频繁或数据量大的通信
3. 共享内存：无需复制，共享缓冲区直接附加到进程虚拟地址空间，速度快；但进程间的同步问题操作系统无法解决，必须各进程利用同步工具解决
4. 套接字：作为更通用的接口，传输效率低，主要用于不同机器或跨网络的通信
5. 信号量：常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。主要作为进程间以及同一进程内不同线程之间的同步手段
6. 信号：不适用于信息交换，更适用于进程中断控制，比如非法内存访问，杀死某个进程等

  ##### 传统IPC方式传递数据 - 存储转发机制
  发送将准备好的数据放在发送方缓存区中，调用API通过系统调用进入内核中。内核服务程序在内核空间为数据分配内存，将
  数据从发送方缓存区拷贝到内核缓存区中。接收方读数据时也要准备一块缓存区，内核将数据从内核缓存区复制到接收方缓存
  区中并唤醒接收线程，完成一次数据发送。
  
  ##### Binder传递数据
  Linux内核实际上没有从一个用户空间到另一个用户空间直接拷贝的函数，需要先用copy_from_user拷贝到内核空间，再用
  copy_to_user拷贝到另一个用户空间。为了实现用户空间到用户空间的拷贝，mmap函数分配的内存除了映射进了接收方进程
  里，也映射进了内核空间里。所以copy_from_user将数据拷贝进内核空间也当于拷贝进了接收方的用户空间，这就是Binder
  只拷贝一次的秘密。
  
  传统的Linux通信机制，比如Socket，管道等都是内核支持的；但是Binder并不是Linux内核的一部分，它是怎么做到访问内
  核空间的呢？    Linux的动态可加载内核模块(Loadable Kernel Module，LKM)机制解决了该问题；模块是具有独立功能
  的程序，它可以被单独编译，但不能独立运行。它在运行时被连接到内核被作为内核的一部分在内核空间运行。这样，Android
  系统可以通过添加一个内核模块运行在内核空间，用户进程直接通过这个模块作为桥梁，就可以完成通信了。
  
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
 
 通信的核心，尽管名为‘驱动’，实际上和硬件设备没有关系，只是实现方式和设备驱动程序是一样的：工作于内核态，
 
 支持open(), mmap(),poll(),ioctl()
 
 ##### Service Manager & 实名Binder
 和DNS类似。
 
 Service Manager的作用是将字符形式的Binder名字转化为Client中的对该Binder的引用，从而Client可以通过Binder名字获得Server中对Binder实体的引用。
 
 注册了名字的Binder叫做实名Binder。Server将名字发送给Service Manager来注册，Binder驱动为这个穿过进程边界的Binder在内核空间创建实体节点[驱动中的Binder实体也叫‘节点’]
 以及Service Manager对Binder实体的引用，将名字和新建引用发送给Service Manager，Service Manager取出名字和引用填入一张查找表中。
 
 以上过程也是通过进程间通信传递的数据，在实现进程间通信的过程中使用了进程间通信，因为我们预定义了一个Binder。
 
 该Binder比较特殊。没有名字，不需要注册，当一个进程使用`BINDER_SET_CONTEXT_MGR`命令将自己注册成Service Manager时，Binder驱动会自动为它创建Binder实体。
 
 该Binder的引用在所有的Client中都固定为0，无需通过其它手段获得。
 
 也就是说，一个Server若向Service Manager注册自己的Binder，必须通过0这个引用号和Service Manager的Binder通信。
 
 一个Server相对于它的Client来说是Service，但相对于Service Manager来说是一个Client。
 
 ##### Client & 实名Binder
 
 Server向Service Manager注册了Binder实体及其名字后，Client就可以通过名字获取该Binder的引用了。
 
 Client也通过引用号为0的Binder向Service Manager请求访问某个Binder。Service Manager收到该请求，从查找表找到该名字对应的Binder引用，回复给Client。
 
 如果有别的Client请求访问该Binder，也会得到指向该Binder的引用，就行Java里一个对象存在多个引用一样。
 
 这些引用是强类型，确保只要有引用Binder实体就不会被释放掉。
 
 ##### 匿名Binder
 
 并不是所以的Binder都需要向Service Manager注册的。
 
 Server端可以通过已建立的连接将创建的Binder传递给Client。从而建立一条私密通道。
 
 ##### Binder引用在驱动中的表述
  就像一个对象有很多指针一样，同一个Binder实体可能有很多引用，分布在不同的进程中。和Binder实体一样，每个进程使用
  红黑树存放所有正在使用的引用。不同的是Binder的引用可以通过两个键值索引：
  * 对应实体在内核中的地址。注意，这里指的是驱动创建于内核中的binder_node实体，并非Binder实体在用户进程中的地址，
  实体在内核中的地址是唯一的，用做索引不会产生二义性；但实体可能来自不同用户进程，而实体在不同用户进程中的地址可能
  重合，因此不能用来做索引。驱动利用该红黑树，在一个进程中快速查找某个Binder实体所对应的应用。
  * 引用号。引用号是驱动为引用分配的一个32位的标识，在一个进程内是唯一的，而在不同进程中可能有相同的值。引用号将
  返回给应用程序，可以看做Binder引用在用户进程中的句柄。除了0号引用在所有进程里固定保留给Service Manager，其它
  值由驱动动态分配。
 
  
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
5. [Binder学习指南](http://weishu.me/2016/01/12/binder-index-for-newer/)


#### 如何介绍Binder
 1. Binder的通信模型
 2. Binder的一次通信



































