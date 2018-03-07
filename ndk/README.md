##### ndk or jni

ndk开发是Android中无法绕开的重要环节.

由于涉及到C/C++,只会Java的我们也许会望而却步.

那一天,还是会到来.

[官方教程](https://developer.android.com/studio/projects/add-native-code.html?hl=zh-cn#create-cmake-script)

##### NDK的用处
1. 加密
2. 音视频解码
3. 图形渲染
4. 安全相关
5. 增量更新
6. 游戏开发
7. p2ps

##### jni 

Java native interface,Java本地接口.使得Java与本地其他类型语言(如C/C++)交互.

即,在Java代码里调用C/C++代码,或C/C++代码调用Java代码

jni是Java的,和Android无直接关系.

##### ndk

Native Development Kit, 是Android的一个工具开发包.

ndk属于Android,与Java并无直接关系.

可以快速开发C/C++的动态库,并自动将so和应用一起打包成APK.

即,通过ndk在Android中使用JNI与本地代码交互.

##### 什么是CMAKE
一个跨平台的编译工具,可以用简单的语句描述所有平台的编译过程.

##### 构建和运行的过程
1. Gradle调用您的外部构建脚本CMakeLists.txt
2. CMake按照构建脚本中的命令将C++源文件`native-lib.cpp`编译到共享的对象中,并命名为`libnative-lib.so`,Gradle随后会将其打包到apk中.(此处复习apk的打包过程)
3. 运行时,应用的MainActivity会使用`System.loadLibrary()`加载原生库.现在,应用可以使用库的原生函数`stringFromJNI()`
4. `ManiActivity.onCreat()`调用`stringFromJNI()`,这将返回"Hello from C++"并使用这些文字更新TextView

    注:Instant Run与使用原生代码的项目不兼容.Android Studio会自动挺有此功能.

