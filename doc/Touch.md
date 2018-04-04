`把Touch事件安排的明明白白`

---

- 所有的Touch事件都封装在MotionEvent里面
- 事件处理包括三种情况,事件分发,事件截获,事件消费
- 事件类型都是以ACTION_DOWN开始,以ACTION_UP结束

---

- 事件都是从dispatchOnTouchEvent开始传递
- 事件是由父视图传递给子视图,ViewGroup可以通过onInterceptTouchEvent方法对事件拦截,停止向子view传递
- 如果事件从上往下传递过程中一直没有被停止,且最底层的view没有消费事件,事件会反向往上传递,如果父视图也没有消费的话,最后会到Activity的onTouchEvent函数
- 如果view没有对ACTION_DOWN进行消费,之后的事件不会传递进来,也就是说ACTION_DOWN必须返回true,之后的事件才会传递进来
- onTouchListener优先于onTouchEvent响应