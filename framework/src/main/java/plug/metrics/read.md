#Metrics是一个给JAVA服务的各项指标提供度量工具的包
----------------------------------------------------------------------
在JAVA代码中嵌入Metrics代码，
可以方便的对业务代码的各个指标进行监控，
同时，Metrics能够很好的跟Ganlia、Graphite结合，方便的提供图形化接口。

##1.Gauges
  Gauge是最简单的度量类型，只有一个简单的返回值，
  例如，你的应用中有一个由第三方类库中保持的一个度量值，你可以很容易的通过Gauge来度量他
##2.JMX Gauges
  Metrics提供一个JmxGauge类，可以供很多第三方的类库通过JMX来展示度量值，通过Metric的newGauge方法可以初始化他，
  参数为JMX MBean的Object名和属性名，还有一个继承了Gauge的类，返回值为那个属性的值。
##3.Ratio Gauges
  Ratio（比率） Gauge是一种计算两个数字之间比例的度量方法
##4.Histograms-直方图
  Histrogram是用来度量流数据中Value的分布情况，例如，每一个搜索中返回的结果集的大小
##5.Meters
  Meter度量一系列事件发生的比率
  Meter需要除了Name之外的两个额外的信息，事件类型（enent type）跟比率单位（rate unit）。
  事件类型简单的描述Meter需要度量的事件类型，在上面的例子中，Meter是度量代理请求数，所以他的事件类型也叫做“requests”。
  比率单位是命名这个比率的单位时间，在上面的例子中，这个Meter是度量每秒钟的请求次数，所以他的单位就是秒。
  这两个参数加起来就是表述这个Meter，描述每秒钟的请求数。
  Meter从几个角度上度量事件的比率，平均值是时间的平均比率，
  它描述的是整个应用完整的生命周期的情况（例如，所有的处理的请求数除以运行的秒数），它并不描述最新的数据。
  幸好，Meters中还有其他3个不同的指数方式表现的平均值，1分钟，5分钟，15分钟内的滑动平均值
  Hint:这个平均值跟Unix中的uptime跟top中秒数的Load的含义是一致的
##6.Timers
  Timer是Histogram跟Meter的一个组合
  Timer需要的参数处理Name之外还需要，持续时间单位跟比率时间单位，持续时间单位是要度量的时间的期间的一个单位，
  在上面的例子中，就是MILLISECONDS，表示这段周期内的数据会按照毫秒来进行度量。比率时间单位跟Meters的一致。
  注：度量消耗的时间是通过java中高进度的System.nanoTime()方法，他提供的是一种纳秒级别的度量
##7.Health Checks(健康检查)
  Meters提供一种一致的、统一的方法来对应用进行健康检查，健康检查是一个基础的对应用是否正常运行的自我检查。
    
#Reporters报告
    Reporters是将你的应用中所有的度量指标展现出来的一种方式，metrics-core中用了三种方法来导出你的度量指标，JMX，Console跟CSV文件
    
##JMX
    默认的，Metrics一直将你的所有指标注册成JMX的MBeans，你可以通过安装了VisualVM-MBeans的VisualVM（大部分JDK自带的jvisualvm）或者Jconsole（大部分JDK自带的jconsole）
    
    提示：你可以双击meteric属性，VisualVM会将数据以图形的方式展现出来。
    
    这种Report必须是JMX一直都是打开的，由于JMX的RPC API是不可靠的，我们不建议你在生产环境中通过JMX来手机度量指标。对于开发者来说，最好是通过网页来浏览，这会非常好用。
    
##Console
    对于一些简单的基准，Metrics提供了ConsoleReporter，这个周期性的打印出注册的metric到控制台上。命令如下：
    
    `ConsoleReporter.enable(1,TimeUnit.SECONDS); ` 
##CSV
    对于比较复杂的基准，Metrics提供了CsvReporter，他周期性的提供了一连串的给定目录下.csv文件。
    
    `CsvReporter.enable(newFile("work/measurements"), 1, TimeUnit.SECONDS); ` 
    上面语句的意思是，每一个Metric指标，都会对应有一个.csv文件创建，每秒钟都会有一行记录被写入到.csv文件中。
    
##OtherReporters
    Metrics还有其他的Reporters：
    
    l  MetricsServlet  将你的度量指标以JSon的格式展现的Servlet，他还可以进行检查检查，Dump线程，暴露出有价值的JVM层面跟OS层面的信息
    
    l  GanliaReporter  将度量指标以流式的方式返回给Ganglia服务器
    
    l  GraphiteReporter  将度量指标以流式的方式返回给Graphite服务器

参考资料
[http://www.dropwizard.io]
[http://metrics.dropwizard.io]
https://github.com/dropwizard/metrics
http://blog.csdn.net/scutshuxue/article/details/8350135
http://blog.synyx.de/2013/09/yammer-metrics-made-easy-part-i/
http://blog.synyx.de/2013/09/yammer-metrics-made-easy-part-ii/
http://wiki.apache.org/hadoop/HADOOP-6728-MetricsV2
