`把线程间通信安排地明明白白`

---

android 应用程序的处理机制包括三部分:消息发送,消息循环,消息处理.

线程间通信涉及到的类:handler,message,looper,MessageQueue

---

handler持有looper实例

handler的发送,移除消息,是通过looper找到对应的MessageQueue,然后消息入队,出队.

handler主要的方法有:

sendMessage 发送消息

dispatchMessage 分发消息

handleMessage 处理消息

---

一个线程只有一个MessageQueue,对应一个looper

MessageQueue的主要作用是消息的入队和出队

以链表数据结构存储消息

---

looper持有MessageQueue实例

looper内部的关键方法是loop

loop里做了三件事情:里面无限循环取出MessageQueue中的Message

然后通过Message的target找到对应的handler,进行消息的分发和处理

最后回收该消息

---

一个线程只能由一个Looper,也只能有一个MessageQueue

handler内存泄露处理.

通常handler 用于工作线程和主线程之间通信,更多情形下是被用做任意线程间通信的工具.