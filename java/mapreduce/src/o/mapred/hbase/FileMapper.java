package o.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class FileMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	public void map(LongWritable key, 
			Text value, 
			OutputCollector<Text, IntWritable> output, 
			Reporter reporter) throws IOException {
		String line = value.toString();
		String year = line.substring(15, 19);
		int airTemperature = Integer.parseInt(line.substring(87, 92));
		output.collect(new Text(year), new IntWritable(airTemperature));
	}

}
