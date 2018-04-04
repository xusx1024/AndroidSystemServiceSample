
`把view绘制过程安排的明明白白`

---

比如说,Activity的获取焦点,就会触发view的展示请求.

view的展示过程,简单说来是测量,布局,绘制的过程.

以一个ViewGroup展示为例.

`测量`是自上而下遍历的,由父视图开始,循环测量子视图的宽高.

在viewGroup中的getChildMeasureSpec方法中实现父视图对子视图的宽高的约束信息.

在子视图中测量,根据父视图中传来的参数,经历了measure(),onMeasure(),setMeasureDimension()方法,把测量出来的视图的宽高,存放在子视图中.

---

`布局`也是自上而下的遍历.根据measure得到的子视图的宽高,调用layout方法.

不同的ViewGroup有各自不同onLayout的实现,ViewGroup中最终仍然是调用子视图的onLayout方法.

具体的onLayout在不同控件中各自实现.

---

`绘制`分7步:
1. 如果需要就绘制背景
2. 如果有需要,保存画布的图层以准备淡化
3. 绘制内容
4. 分发绘制子视图
5. 如果有需要绘制渐变边界并恢复图层
6. 绘制滚动条这类的装饰物
7. 绘制默认的焦点,高亮显示区域

---

关键方法:

`invalidate()`,请求重绘view树,即draw的过程,假如视图大小没有变化,就不会调用Layout过程,并且只绘制那些调用了`invalidate`方法的view

`requestLayout`, 当布局变化的时候,比如方向变化,尺寸变化,会调用该方法,在自定义视图中,如果某些情况下希望重新测量尺寸大小,应该手动调用该方法,他会触发`measure`和`layout`过程,但不会draw.

![diff](../img/view_draw_method_chain.png)

---

width和measureWidth的区别:

measureWidth是在measure阶段确定的,通过setMeasureDimension方法赋值;

width是在layout阶段确定的,是view的实际绘制的宽高;

view的宽高是由父视图容器和本身参数决定的,并不是xml中的layout_width,layout_height设置决定的,这两个参数只是决定了在layout是的一个参考量

---

view 默认是可见的

如果设置属性INVISIBLE,则不会调用layout方法

如果设置属性GONE,则不会调用measure,layout方法

不管view是什么状态,总是会回调onAttachToWindow和onDetachedFromWindow