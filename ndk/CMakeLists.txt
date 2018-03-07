# 要指示CMake从原生源代码创建一个原生库,请将`cmake_minimum_required()`和`add_library()`命令添加到您的构建脚本中.

cmake_minimum_required(VERSION 3.4.1)

add_library(# 指定库的名字
            native-lib

            # 设置为可共享的库
            SHARED

            # C++文件所在的相对地址
            src/main/cpp/native-lib.cpp)

            # 指定本地头文件的地址
           # includes_directories(src/main/cpp/)

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )