package com.gourd.erwa.spark.ml.linear.regression

import breeze.linalg.sum
import com.gourd.erwa.spark.ml.Constant
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 数据描述
  * 此数据是根据一系列的特征预测每小时自行车租车次数。数据中字段分别为：
  * instant：记录ID
  * dteday：时间
  * season：四季信息
  * yr：年份（2011或2012）
  * mnth：月份
  * hr：当天时刻
  * holiday：是否节假日
  * weekday：周几
  * workingday：当天是否工作日
  * weathersit：表示天气类型的参数
  * temp：气温
  * atemp：体感温度
  * hum：湿度
  * windspeed：风速
  * cnt：目标变量，每小时的自行车租车量
  * 一共15个字段，主要目的是预测最后一个字段的租车量。研究数据发现头两个字段对于预测结果没什么用处，所以这里不考虑。
  *
  * 特征提取
  * 观察数据发现从第3到第10个字段是类型变量（类型变量就是取值一直就只有那么几种，例如星期几、天气状况等），11到14字段是实数变量（实数变量就是一个无法确定的值）这里的值已经进行过归一化处理了，
  * 如果没归一化处理需先归一处理，可以提高准确度。
  *
  * 类别特征
  * 其实上一篇博客已经讲过类别型数据怎么提取特征，主要方法就是将类别数据转换为二元向量，就是用一个向量来表示类别。
  * 举个例子就是如果水果有三类分别为苹果、香蕉、梨。一般在类别数据中记录的形式为1：苹果，2：香蕉，3：梨。将其转换为二元向量之后就是001：苹果，010：香蕉，100：梨。
  * 如果不转换为二元特征算出来会有错误，
  * 本数据我试过如果不转换预测结果是负数
  *
  * @author wei.Li by 2018/5/11
  */
object BikeLinearRegression {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("regression").setMaster("local[1]")
    val sc = new SparkContext(conf)

    val records = sc.textFile(Constant.DATA_PATE + "linearRegression/bike/hour_no_head.csv").map(_.split(",")).cache()

    val mappings = for (i <- Range(2, 10)) yield getMapping(records, i)

    val catLen = sum(mappings.map(_.size))
    val numLen = records.first().slice(10, 14).length
    val totalLen = catLen + numLen

    //linear regression data 此部分代码最重要，主要用于产生训练数据集，按照前文所述处理类别特征和实数特征。
    val data = records.map { record =>
      val cat_vec = Array.ofDim[Double](catLen)
      var i = 0
      var step = 0
      for (filed <- record.slice(2, 10)) {
        val m = mappings(i)
        val idx = m(filed)
        cat_vec(idx.toInt + step) = 1.0
        i = i + 1
        step = step + m.size
      }

      val num_vec = record.slice(10, 14).map(x => x.toDouble)
      val features = cat_vec ++ num_vec
      val label = record(record.length - 1).toInt

      LabeledPoint(label, Vectors.dense(features))
    }

    // val categoricalFeaturesInfo = Map[Int, Int]()
    //val regressionModel=DecisionTree.trainRegressor(data,categoricalFeaturesInfo,"variance",5,32)
    val regressionModel = LinearRegressionWithSGD.train(data, 10, 0.5)
    val true_vs_predicted = data.map(p => (p.label, regressionModel.predict(p.features)))
    //输出前五个真实值与预测值
    println(true_vs_predicted.take(5).toVector.toString())
  }

  def getMapping(rdd: RDD[Array[String]], idx: Int): collection.Map[String, Long] = {
    rdd.map(filed => filed(idx)).distinct().zipWithIndex().collectAsMap()
  }

}
