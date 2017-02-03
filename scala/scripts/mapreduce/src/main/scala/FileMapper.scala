package o.mapred

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class FileMapper extends Mapper[LongWritable, Text, Text, IntWritable] {

    override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
        val line = value.toString()
        //val year = line.substring(0, 4)
        //val airTemperature = line.substring(5, 7).toInt
        val year = line.take(4)
        val airTemperature = line.drop(5).toInt
        context.write(new Text(year), new IntWritable(airTemperature))
    }

}
