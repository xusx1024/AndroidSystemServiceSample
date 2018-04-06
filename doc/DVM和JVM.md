`好文多读`

[Dalvik 虚拟机和 Sun JVM 在架构和执行方面有什么本质区别](https://www.zhihu.com/question/20207106)

作者:[天光](https://www.zhihu.com/people/tgsunny/activities)

    区别

(1) Dalvik VM和JVM 的第一个区别是 Dalvik VM是基于寄存器的架构（reg based），而JVM是栈机（stack based）。

reg based VM的好处是可以做到更好的提前优化（ahead-of-time optimization）。 另外reg based的VM执行起来更快，但是代价是更大的代码长度。

(2) 另外一个区别是Dalvik可以允许多个instance 运行，也就是说每一个Android 的App是独立跑在一个VM中.

这样做的好处是一个App crash只会影响到自身的VM，不会影响到其他。 Dalvik的设计是每一个Dalvik的VM都是Linux下面的一个进程。那么这就需要高效的IPC。

另外每一个VM是单独运行的好处还有可以动态active/deactive自己的VM而不会影响到其他VM

(3) 既然reg based VM有那么多好处，为什么之前设计JAVA的人没有采用reg based而是采用stack based的呢？

原来stack based的VM也有其优点，就是它不对host平台的reg数量做假设，有利于移植到不同的平台。而Dalvik则不关心这些，因为它本来就是为ARM这样的多reg平台设计的。

另外Dalvik被移植到x86也说明，即使是x86这种reg很少的平台，reg based的VM也是没有问题的。

---

    DVM的优势

1. 在编译时提前优化代码而不是等到运行时
2. 虚拟机很小，使用的空间也小；被设计来满足可高效运行多种虚拟机实例
3. 常量池已被修改为只使用32位的索引，以简化解释器















