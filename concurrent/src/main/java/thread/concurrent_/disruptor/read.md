51CTO 译文框架介绍 http://developer.51cto.com/art/201306/399370.htm


#Disruptor
Disruptor 的源码非常精简，没有任何配置文件，所有源文件类加起来也就 58 个（不同版本可能不一样），
用代码行统计工具算了下，一共 6306 行。
对于一个能做到如此成功的开源工具来说，能有这么精短的代码量，确实很不错。

##Disruptor 代码共分为四个包：

1). com.lmax.disruptor: 大部分文件存放于这个目录下，包括 Disruptor 中重要的类文件，
    包括：EventProcessor、RingBuffer、Sequence、Sequencer、WaitStrategy 等
2). com.lmax.disruptor.collections: 该目录下只有一个类：Histogram，
    它不是 Disruptor 运行的必须类，其实我也没用过它，从源码注释来看，
    该类的作用是，在一个对性能要求很高的、有多个消费者的系统中，Histogram 可以用来记录系统耗各个组件的耗时情况，
    并以直方图的形式展示出来。初学 Disruptor 可以不用管关心它。
3). com.lmax.disruptor.dsl: 该包中保存了消费者和生产者的一些信息，核心类文件 Disruptor 也存放在该目录下。
4). com.lmax.disruptor.util: 该包中存放了几个辅助操作类，
    如 Util 类，DaemonThreadFactory 类，PaddedLong 类，该类用来做缓冲行填充的。