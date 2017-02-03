package o.mapred

import scala.collection.JavaConversions._

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

class FileReducer extends Reducer[Text, IntWritable, Text, IntWritable] {

    override def reduce(key: Text, values: java.lang.Iterable[IntWritable],
                        context: Reducer[Text, IntWritable, Text, IntWritable]#Context):Unit = {

        var maxValue = Int.MinValue

        for (value <- values) {
            maxValue = math.max(maxValue, value.get())
        }

        context.write(key, new IntWritable(maxValue))
    }

}
