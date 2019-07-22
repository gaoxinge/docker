package gxg;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class HelloWorld {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("HelloWorld")
                .setMaster("local")
                .set("spark.driver.memory", "1g")
                .set("spark.testing.memory", "2147480000");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        ctx.hadoopConfiguration().set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        JavaRDD<String> data = ctx.textFile("README.md");
        data.foreach(s -> System.out.println(s));
    }
}
