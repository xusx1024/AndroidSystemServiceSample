# ActivityManager示例项目的问题 
#### 关于

- ActivityManagerService启动过程
  - [系统启动过程 - init进程启动 - zygote启动 - SystemServer启动](../doc/zygote.md)
  - SystemServer启动ActivityManagerService过程
- ActivityManager应用
  - 负责启动、切换、调度系统和应用程序的四大组件
  - 内存管理
  - 进程管理
 

#### 问题
##### Execution failed for task ':app:compileDebugJavaWithJavac'

尝试：<br>
- `gradlew compileDebugJavaWithJavac --debug`
- `gradlew compileDebugJavaWithJavac --stacktrace`

#####  Circular dependency between the following tasks
<p>
* Exception is:<br/>
org.gradle.api.CircularReferenceException: Circular dependency between the following tasks:<br/>
:app:compileReleaseKotlin<br/>
\--- :app:kaptReleaseKotlin<br/>
     \--- :app:compileReleaseKotlin (*)

</p>

- `build.gradle:1.1.2-2`，或者`build.gradle:3.0.0-alpha3`。总之不要使用1.1.2-4就好了。
- 这是一个bug。
- 使用3.0.0，需要AS3.0版本，到目前为止，尚未释出稳定版
- [stackoverflow](https://stackoverflow.com/questions/44035504/how-to-use-data-binding-and-kotlin-in-android-studio-3-0-0)

##### 熟悉activity标签所有的属性
##### 熟悉application标签所有的属性
##### 熟悉adb中am相关的命令
##### 熟悉adb中pm相关的命令
##### 熟悉adb中input相关的命令
##### 熟悉adb中logcat相关的命令
##### zygot 
- 为大部分应用程序复刻一个虚拟机实例
- 系统引导进程，init进程不是由zygote创建的


