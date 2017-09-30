#### 关于 ####
本来这是字幕管理，示例demo有些跑偏到弹幕上去了，感觉弹幕更有意思。

#### 字幕管理 ####
 
- 字幕管理
- [Android开发无障碍指南](http://informationaccessibilityassociation.github.io/androidAccessibility/checklist.htm):
	>如果应用程序提供视频播放，必须为聋人和重听用户提供字幕和注释来帮助他们理解。视频播放控件必须明确说明字幕是否可用，并提供简单的方式启用字幕。

#### 设备如何设置 ####

[google 官方说明](https://support.google.com/accessibility/android/answer/6006554)

 **字幕** 

您可以为设备开启字幕功能以及为字幕选择所需选项（语言、文字和样式）。

开启字幕功能<br>
打开设备的“设置”应用<br>
点按`无障碍`，然后点按`字幕`<br>
点按顶部的`开启/关闭`开关<br> 
<br> 
**更改字幕设置**<br>
开启字幕功能后，您可以在`设置` > `无障碍` > `字幕`中调整以下设置。<br>
 
- 语言：选择您想要以何种语言显示字幕 
- 文字大小：选择字幕文字大小 
- 字幕样式：为字幕文字和背景选择样式和颜色 
 <br>

您的字幕设置可能不适用于某些应用。<br>
 
#### 官方文档 ####

[简介](https://developer.android.google.cn/about/versions/android-4.4.html?hl=zh-cn)

官方文档称之为：隐藏式字幕。4.4版本释出。<br>

现在播放HTTP Live Steam(HLS)视频时，`VideoView`支持`WebVTT`字幕跟踪，根据用户在系统设置中定义的隐藏式字幕首选项显示字幕跟踪。<br>
您也可以使用`addSubtitleSource()`方法为`VideoView`提供`WebVTT`字幕跟踪。此方法接受一个`InputStream`,其携带字幕数据和指定该字幕数据格式的`MediaFormat`对象，您可以使用`createSubtitleFormat()`指定该对象。这些字幕也会按照用户的首选项显示在视频上。<br>
如果您不使用`VideoView`显示您的视频内容，则应尽可能使您的字幕叠加层与用户的隐藏式字幕首选项匹配。新的`CaptioningManager API`允许您查询用户的隐藏式字幕首选项，包括`CaptioningManager.CaptionStyle`定义的样式，如字体和颜色。如果用户在视频已开始后调整部分首选项，您应侦听首选项的变化，方法是注册一个`CaptioningManager.CaptioningChangeListener`实例以在任意首选项发生变化时接收回调，然后根据需要更新您的字幕。<br>

[WebVTT 及 HTML5 <track> 元素简介](https://dev.opera.com/articles/zh-cn/an-introduction-to-webvtt-and-track/)<br>
[WebVTT与srt格式转换](https://www.zhihu.com/question/29789259)


#### 题外话：视频弹幕 ####

[基础原理](http://blog.csdn.net/zhangphil/article/details/52021055)<br>

[成熟的B站](https://github.com/Bilibili/DanmakuFlameMaster)<br>