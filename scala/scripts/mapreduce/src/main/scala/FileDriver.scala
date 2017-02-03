package o.mapred

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

object FileDriver {

  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println(s"Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName())
      System.exit(-1)
    }

    val job: Job = new Job()
    job.setJobName("Max temperature")

    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])

    job.setMapperClass(classOf[FileMapper])
    job.setReducerClass(classOf[FileReducer])

    System.exit(if (job.waitForCompletion(true)) 0 else 1)
  }

}
