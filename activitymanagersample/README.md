# ActivityManager示例项目的问题 
#### 关于
`AccountManager`用来获取系统里正在运行的组件，包括进程、内存、应用程序相关的信息

#### 计划

- API调用比较简单
- 使用`Android Architecture Components`框架来动态展示进程、内存这些信息
- 考虑使用图表等展示
- 考虑做得和Windows任务管理器类似

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

##### 要熟悉activity所有的属性