package gxg;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCount {
    public static class WordWithCount {
        public String word;
        public long count;
        public WordWithCount() {}
        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }
        @Override
        public String toString() {
            return "WordWithCount{word=" + word + ", count=" + count + "}";
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStreamSource<String> text = env.fromElements("hello", "hello", "world", "world", "world");
//        DataStream<WordWithCount> windowCount = text.flatMap(
//                new FlatMapFunction<String, WordWithCount>() {
//                    @Override
//                    public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
//                        String[] splits = value.split("\\s");
//                        for (String word: splits) {
//                            out.collect(new WordWithCount(word, 1L));
//                        }
//                    }
//                }
//        ).keyBy("word").sum("count");
        DataStreamSource<String> text = env.socketTextStream("localhost", 9000, "\n");
        DataStream<WordWithCount> windowCount = text.flatMap(
                new FlatMapFunction<String, WordWithCount>() {
                    @Override
                    public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                        String[] splits = value.split("\\s");
                        for (String word: splits) {
                            out.collect(new WordWithCount(word, 1L));
                        }
                    }
                }
        ).keyBy("word").timeWindow(Time.seconds(2), Time.seconds(1)).sum("count");
        windowCount.print().setParallelism(1);
        env.execute("streaming word count");
    }
}
