package com.sparkbyexamples.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDFromParallelizeRange {
  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()
    /* getOrCreate() = https://spark.apache.org/docs/latest/api/scala/org/apache/spark/sql/SparkSession$$Builder.html#getOrCreate():org.apache.spark.sql.SparkSession

    Gets an existing SparkSession or, if there is no existing one, creates a new one based on the options set in this builder.

This method first checks whether there is a valid thread-local SparkSession, and if yes, return that one. It then checks whether there is a valid global default SparkSession, and if yes, return that one. If no valid global default SparkSession exists, the method creates a new SparkSession and assigns the newly created SparkSession as the global default.

In case an existing SparkSession is returned, the non-static config options specified in this builder will be applied to the existing SparkSession.
     */

    val sc = spark.sparkContext
    // sparkcontext = https://spark.apache.org/docs/latest/api/scala/org/apache/spark/SparkContext.html

    val rdd4:RDD[Range] = sc.parallelize(List(1 to 1000))
    println("Number of Partitions : "+rdd4.getNumPartitions)
    // getNumPartitions(): https://spark.apache.org/docs/latest/api/scala/org/apache/spark/rdd/RDD.html#getNumPartitions:Int

    val rdd5 = rdd4.repartition(5)
    println("Number of Partitions : "+rdd5.getNumPartitions)

    // collect(): returns an array that contains all the data in the RDD:
    // source: https://spark.apache.org/docs/latest/api/scala/org/apache/spark/rdd/RDD.html#collect():Array[T]
    val rdd6:Array[Range] = rdd5.collect()
    println(rdd6.mkString(","))

    val rdd7:Array[Array[Range]] = rdd5.glom().collect()
    println("After glom");
    // glom(): return an RDD by coalescing all elements within each partition into an array
    // https://spark.apache.org/docs/latest/api/scala/org/apache/spark/rdd/RDD.html#glom():org.apache.spark.rdd.RDD[Array[T]]

    rdd7.foreach(f=>{
      println("For each partition")
      f.foreach(f1=>println(f1))
    })


  }

}
