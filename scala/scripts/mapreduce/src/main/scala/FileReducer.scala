package o.mapred

//import scala.collection.JavaConversions._

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapred.{MapReduceBase, OutputCollector, Reducer, Reporter}

class FileReducer extends MapReduceBase 
    with Reducer[Text, IntWritable, Text, IntWritable] {

    override def reduce(key:Text,
      values:java.util.Iterator[IntWritable],
      output:OutputCollector[Text, IntWritable],
      reporter:Reporter):Unit = {

        var maxValue = Int.MinValue

        while (values.hasNext) {
            maxValue = Math.max(maxValue, values.next().get())
        }

        output.collect(key, new IntWritable(maxValue))
    }

}
