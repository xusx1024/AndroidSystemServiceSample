#### HashMap快问快答

##### HashMap的特点
- 允许null键和null值,hashtable不可以
- 存储键值对
- 无序
- 非同步的,速度快
##### HashMap的工作原理
HashMap基于hashing原理,我们使用put(k,v)存储对象,使用get(k)从HashMap中获取对象.<br/>
当调用put方法传递k,v时,会先对k调用hashCode方法,返回的hashCode用于找到bucket位置来存储Entry对象.<br/>
如果hashcode没有碰撞,直接放进bucket中,如果碰撞了,以链表的形式存在buckets之后<br/>
如果碰撞导致链表过长,就把链表转换成红黑树<br/>
如果节点已经存在就替换old value,保证key的一致性<br/>
如果bucket满了,就要resize

##### HashMap的的get()方法的工作原理
##### 两个对象的hashcode相同会发生什么?
hashcode相同,所以他们的bucket位置相同,'碰撞'发生.这个Entry会存储在链表中.
##### 如果两个对象的hashcode相同,你如何获取值对象?
##### 如果HashMap大小超过了负载因子定义的容量怎么办?
##### 你了解重新调整HashMap大小存在什么问题吗?
##### 为什么String,Integer这样的wrapper类适合作为键?
##### 可以使用自定义的对象作为键吗?
##### 可以使用CocurrentHashMap来代替HashTable吗?
