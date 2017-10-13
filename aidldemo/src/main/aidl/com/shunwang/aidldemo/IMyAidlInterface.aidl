// IMyAidlInterface.aidl
package com.shunwang.aidldemo;
// 传递默认支持的数据类型
// 8大基本类型、String、List、Map、CharSequence

// 一个基本的AIDL，Messenger即使用此种，当然比这个复杂。。。
interface IMyAidlInterface {
            String getName();
}
