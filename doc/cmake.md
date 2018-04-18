##### 什么是CMAKE
一个跨平台的编译工具,可以用简单的语句描述所有平台的编译过程.

##### 构建和运行的过程
1. Gradle调用您的外部构建脚本CMakeLists.txt
2. CMake按照构建脚本中的命令将C++源文件`native-lib.cpp`编译到共享的对象中,并命名为`libnative-lib.so`,Gradle随后会将其打包到apk中.(此处复习apk的打包过程)
3. 运行时,应用的MainActivity会使用`System.loadLibrary()`加载原生库.现在,应用可以使用库的原生函数`stringFromJNI()`
4. `ManiActivity.onCreat()`调用`stringFromJNI()`,这将返回"Hello from C++"并使用这些文字更新TextView

    注:Instant Run与使用原生代码的项目不兼容.Android Studio会自动挺有此功能.



















