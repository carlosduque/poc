package o.mapred

import java.io.IOException

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapred.{MapReduceBase, Mapper, OutputCollector, Reporter}

class FileMapper extends MapReduceBase
    with Mapper[LongWritable, Text, Text, IntWritable] {

    override def map(key: LongWritable,
      value:Text,
      output: OutputCollector[Text, IntWritable],
      reporter:Reporter):Unit = {
        val line = value.toString()
        val year = line.substring(15, 19)
        val airTemperature = line.substring(87, 92).toInt
        output.collect(new Text(year), new IntWritable(airTemperature))
    }

}
