package com.gourd.erwa.spark.ml

import breeze.linalg.DenseMatrix

/**
  * @author wei.Li by 2018/5/11
  */
object Func {

  def main(args: Array[String]): Unit = {
    val unit = DenseMatrix.zeros[Double](2, 3)
    print(unit)
  }

}
