package com.edureka.project.youtube;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//Find out the top 10 rated videos.
public class TopRated {
	public static class MyMapper extends Mapper<LongWritable,Text, DoubleWritable, Text> {		//data structure to store category and count
		@Override
        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {	    	  
            try{
               String[] str = value.toString().split("\t");	 
               String vdoId = str[Utils.YOUTUBE_ID];
               Double rating = Double.parseDouble(str[Utils.YOUTUBE_RATING]);
               context.write(new DoubleWritable(rating), new Text(vdoId));
            }
            catch(Exception e)
            {
               System.out.println(e.getMessage());
            }
         }
    }
	
	//reducer to sort and write
	public static class MyReducer extends Reducer<DoubleWritable, Text, DoubleWritable, Text>
	{
		private Map<Double, String> rateVdoMap;
		
		@Override
		public void setup(Context context) {
			// use treemap for sorting with double key
			rateVdoMap = new TreeMap<Double, String>(Collections.reverseOrder());
		}
		
		@Override
	    public void reduce(DoubleWritable rating, Iterable<Text> vdoIDs,Context context) throws IOException, InterruptedException {
	       for (Text vdo : vdoIDs) {   
	    	   rateVdoMap.put(rating.get(), vdo.toString());
	       }
	       
		}
		
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException {
			int count = 10;
			for(Map.Entry<Double,String> ent:rateVdoMap.entrySet()) {
				if (count <= 0) {
					break;
				}
				context.write(new DoubleWritable(ent.getKey()), new Text(ent.getValue()));
				--count;
			}
		}
	}
	
	//hadoop fs -mkdir youtube
	//hadoop fs -put youtubedata.txt youtube
	//hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopRated youtube/youtubedata.txt youtube/youtube_top_rated_out
    public static void main(String[] args) 
            throws IOException, ClassNotFoundException, InterruptedException {

			Configuration conf = new Configuration();
			conf.set("mapreduce.output.textoutputformat.separator", " ");
			Job job = Job.getInstance(conf);
		    job.setJarByClass(TopCategories.class);
		    job.setJobName("Top 10 rated vdo");
		    job.setMapperClass(MyMapper.class);
		    job.setNumReduceTasks(1);
		    job.setReducerClass(MyReducer.class);
			job.setCombinerClass(MyReducer.class);
		    job.setMapOutputKeyClass(DoubleWritable.class);
		    job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(DoubleWritable.class);
			job.setOutputValueClass(Text.class);
		  
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		  
		    job.waitForCompletion(true);
		  
		}
}
