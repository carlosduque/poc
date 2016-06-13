package o.mapred;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.w3c.dom.Text;

public class FileReducer extends MapReduceBase 
	implements Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text key, Iterator<IntWritable> values,
			OutputCollector<Text, IntWritable> output, Reporter reporter) 
					throws IOException {
		
		int maxValue = Integer.MIN_VALUE;
		
		while(values.hasNext()) {
			maxValue = Math.max(maxValue, values.next().get());
		}
		
		output.collect(key, new IntWritable(maxValue));
	}

}
