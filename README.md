# 把嵌套ViewModel当做子ViewModel处理
子ViewModel 的概念就是在ViewModel 里面嵌套其他的ViewModel，这种场景还是很常见的。
* 比如说你一个Activity里面有两个Fragment，ViewModel 是以业务划分的，两个Fragment做的业务不一样，自然是由两个ViewModel来处理。
Activity 本身可能就有个ViewModel 来做它自己的业务，这时候Activity的这个ViewModel里面可能包含了两个Fragment分别的ViewModel。
这就是嵌套的子ViewModel。

* 还有另外一种就是对于AdapterView 如ListView RecyclerView,ViewPager等。
