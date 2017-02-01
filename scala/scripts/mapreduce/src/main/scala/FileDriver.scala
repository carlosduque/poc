package o.mapred

import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable,Text}
import org.apache.hadoop.mapred.{FileInputFormat, FileOutputFormat, JobClient, JobConf}
import org.apache.hadoop.util.{Tool, ToolRunner}

object FileDriver extends Configured  with Tool {

  override def run(args: Array[String]):Int = {
    if (args.length != 2) {
        println(s"Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName())
       ToolRunner.printGenericCommandUsage(Console.err)
       -1
    }

    val conf:JobConf = new JobConf(this.getConf(), this.getClass())
    conf.setJobName("Max temperature")

    FileInputFormat.addInputPath(conf, new Path(args(0)))
    FileOutputFormat.setOutputPath(conf, new Path(args(1)))

    conf.setOutputKeyClass(classOf[Text])
    conf.setOutputValueClass(classOf[IntWritable])

    conf.setMapperClass(classOf[FileMapper])
    conf.setCombinerClass(classOf[FileReducer])
    conf.setReducerClass(classOf[FileReducer])

    JobClient.runJob(conf)
    return 0
  }

  def main(args: Array[String]): Unit = {
    ToolRunner.run(FileDriver, args)
  }

}
