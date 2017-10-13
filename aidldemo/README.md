# AIDL
  研究源码必须了解AIDL的使用，否则会云山雾罩。
  
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

#### IPC
* 如果不需要IPC，直接通过基础Binder类来实现C-S通信
* 如果需要，但是无需处理多线程，那么通过Messenger来实现，Messenger保证了消息是串行处理的，内部为AIDL实现
* 实在有IPC需求，并且需要处理多线程，使用AIDL

  ### IPC的手段简介
  ### IPC的作用
  ### android中的IPC
 
#### binder

#### 问题
##### ConstraintLayout 
* 最低支持api9
* 成为约束布局，可替代relativelayout
* 不需要滑动的简单UI可以使用，用以减低UI层级