# AIDL源码

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