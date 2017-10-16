# AIDL
  研究源码必须了解AIDL的使用，否则会云山雾罩。
  
  终将发现，AIDL只是定义或者描述接口的语言，需要了解Binder的原理才好。
  
  Demo1是同一个应用程序中不同进程的通信。Demo2是不同应用程序间通信。
  
#### 名词
- AIDL：android interface definition language。android接口定义语音，不涉及具体实现，只是定义用。
- IPC： inner-process communication

#### 语法
* 文件后缀.aidl
* 数据类型支持8大基本数据类型，list，map，String，CharSequence，Parcelable
* 定向tag，表示跨进程通信中数据的流向
  * in，对象由C到S，S接收到完整对象，对象在C端的状态不会因为S端对该对象的修改而变动
  * out，对象由C到S，S接收到空白对象，对象在C端的状态会和S端同步变动
  * inout，对象由C到S，S接收到完整对象，对象在C端的状态会和S端同步变动
* AIDL 文件分类
  * parcelable 对象定义文件
  * 定义方法接口
* 要确保客户端、服务端都有要用到的AIDL文件，包括自己的parcelable对象类，注意包名也要一直

#### 步骤
1. 编写.aidl文件，定义需要的接口
2. 实现该接口
3. 将接口暴露给客户端使用

#### AIDL源码
     
     我们的AIDL文件只有一个get方法
     
     编译器根据AIDL文件自动生成的JAVA文件
     
     AIDL语言的作用就是简化我们写这样一个文件的工作
     
     可以自己按照该文件写一个，也是可以进行跨进程通信的
     
     .\aidldemo\build\generated\source\aidl\debug\com\shunwang\aidldemo\IMyAidlInterface.java
      
     ```
     /*
      * This file is auto-generated.  DO NOT MODIFY.
      * Original file: E:\\OtherProjects\\AndroidSystemServiceSample\\aidldemo\\src\\main\\aidl\\com\\shunwang\\aidldemo\\IMyAidlInterface.aidl
      */
     package com.shunwang.aidldemo;
     // 传递默认支持的数据类型
     // 8大基本类型、String、List、Map、CharSequence
     // 一个基本的AIDL，Messenger即使用此种，当然比这个复杂。。。
     
     public interface IMyAidlInterface extends android.os.IInterface {
       public java.lang.String getName() throws android.os.RemoteException;
     
       /** Local-side IPC implementation stub class. */
       /** 自定义服务的onBind方法，返回的Binder就是这个Stub. */
       public static abstract class Stub extends android.os.Binder
           implements com.shunwang.aidldemo.IMyAidlInterface {// 此处实现了我们自定义的AIDL接口
         static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
         private static final java.lang.String DESCRIPTOR = "com.shunwang.aidldemo.IMyAidlInterface";
     
         /** Construct the stub at attach it to the interface. */
         public Stub() {
           this.attachInterface(this, DESCRIPTOR);
         }
     
         /**
          * Cast an IBinder object into an com.shunwang.aidldemo.IMyAidlInterface interface,
          * generating a proxy if needed.
          */
          /**
          * 客户端的ServiceConnection，调用获取该类的代理类/实例
          */
         public static com.shunwang.aidldemo.IMyAidlInterface asInterface(android.os.IBinder obj) {
           if ((obj == null)) {
             return null;
           }
           android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
           if (((iin != null) && (iin instanceof com.shunwang.aidldemo.IMyAidlInterface))) {// 是否已存在实例
             return ((com.shunwang.aidldemo.IMyAidlInterface) iin);
           }
           return new com.shunwang.aidldemo.IMyAidlInterface.Stub.Proxy(obj);
         }
     
         @Override public android.os.IBinder asBinder() {
           return this;
         }
     
         /** 服务端处理数据. */
         /** 由Binder驱动决定何时调用. */
         @Override
         public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
             throws android.os.RemoteException {
           switch (code) {
             case INTERFACE_TRANSACTION: {
               reply.writeString(DESCRIPTOR);
               return true;
             }
             case TRANSACTION_getName: {// 处理我们AIDL中定义的方法，对应Proxy中的transact中的参数1
               data.enforceInterface(DESCRIPTOR);
               java.lang.String _result = this.getName();
               reply.writeNoException();
               reply.writeString(_result);
               return true;
             }
           }
           return super.onTransact(code, data, reply, flags);
         }
     
         /** 客户端调用. */
         private static class Proxy implements com.shunwang.aidldemo.IMyAidlInterface {
           private android.os.IBinder mRemote;
     
           Proxy(android.os.IBinder remote) {
             mRemote = remote;
           }
     
           @Override public android.os.IBinder asBinder() {
             return mRemote;
           }
     
           public java.lang.String getInterfaceDescriptor() {
             return DESCRIPTOR;
           }
     
           @Override public java.lang.String getName() throws android.os.RemoteException {
             android.os.Parcel _data = android.os.Parcel.obtain();// 客户端 2 服务端
             android.os.Parcel _reply = android.os.Parcel.obtain();// 服务端响应客户端
             java.lang.String _result;
             try {
               _data.writeInterfaceToken(DESCRIPTOR);
               mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);// 传递
               _reply.readException();
               _result = _reply.readString();// 取出响应结果
             } finally {
               _reply.recycle();
               _data.recycle();
             }
             return _result;
           }
         }
       }
     }
     
     ```
    #### 科普
    * parcel：用来存取数据的容器，用它来进行客户端和服务器之间的数据传输，只可传输可序列化的数据，同时有大小上限。
    * transact方法：客户端和服务端通信的核心方法。调用后，客户端会挂起当前线程，等候服务端的处理并接受_reply数据流
      * 参数1：方法ID，客户端与服务端约定好的给方法的编码，彼此一一对应。在AIDL文件转化为Java文件时，系统分配该ID
      * 参数2：客户端发送数据容器
      * 参数3：服务端存放数据容器
      * 参数4：int值，设置IPC模式，0表示可以双向流通，即可以在参数3里带数据回来，如果为1，则只能单向流通数据，参数3里无数据
    * onTransact方法：服务端处理transact方法传入的参数，根据方法ID，封装不同的_reply返回
     
#### IPC
* 如果不需要IPC，直接通过基础Binder类来实现C-S通信
* 如果需要，但是无需处理多线程，那么通过Messenger来实现，Messenger保证了消息是串行处理的，内部为AIDL实现
* 实在有IPC需求，并且需要处理多线程，使用AIDL

  ### Linux的IPC手段
  1. 管道：在创建时，分配一个page大小的内存，缓存区大小比较有限
  2. 消息队列：信息复制两次，额外的CPU消耗，不适合频繁或数据量大的通信
  3. 共享内存：无需复制，共享缓冲区直接附加到进程虚拟地址空间，速度快；但进程间的同步问题操作系统无法解决，必须各进程利用同步工具解决
  4. 套接字：作为更通用的接口，传输效率低，主要用于不同机器或跨网络的通信
  5. 信号量：常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。主要作为进程间以及同一进程内不同线程之间的同步手段
  6. 信号：不适用于信息交换，更适用于进程中断控制，比如非法内存访问，杀死某个进程等
  
  ### IPC的作用
  ### android中的IPC
 
#### binder

#### 问题
##### ConstraintLayout 
* 最低支持api9
* 成为约束布局，可替代relativelayout
* 不需要滑动的简单UI可以使用，用以减低UI层级