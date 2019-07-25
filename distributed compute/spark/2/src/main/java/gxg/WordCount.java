package gxg;

import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;


public class WordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("WordCount")
                .setMaster("local")
                .set("spark.driver.memory", "1g")
                .set("spark.testing.memory", "2147480000");

        JavaSparkContext ctx = new JavaSparkContext(conf);
        ctx.hadoopConfiguration().set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        JavaRDD<String> lines = ctx.textFile("WordCountByJava8.java", 1);
        lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((e, acc) -> e + acc, 1)
                .map(e -> new Tuple2<>(e._1, e._2))
                .sortBy(e -> e._2, false, 1)
                .foreach(e -> System.out.println("[" + e._1 + "] " + e._2));
        ctx.close();
    }
}
