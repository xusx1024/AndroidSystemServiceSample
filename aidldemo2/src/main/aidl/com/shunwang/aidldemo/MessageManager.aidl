// MessageManager.aidl
package com.shunwang.aidldemo;

// Declare any non-default types here with import statements
import com.shunwang.aidldemo.HelloMsg;
interface MessageManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<HelloMsg> getMsg();
    void addMsg(in HelloMsg msg);
}
