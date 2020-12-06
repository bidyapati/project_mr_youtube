package com.edureka.project.youtube;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


// Find out the most viewed videos.
public class MostViewed {

	public static class MyMapper extends Mapper<LongWritable,Text, LongWritable, Text> {
		
		@Override
        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {	    	  
            try {
               String[] str = value.toString().split("\t");	 
               String vdoId = str[Utils.YOUTUBE_ID];
               Long viewCount = Long.parseLong(str[Utils.YOUTUBE_VIEWS_COUNT]);
               context.write(new LongWritable(viewCount), new Text(vdoId));
            }
            catch(Exception e)
            {
               System.out.println(e.getMessage());
            }
         }
    }
	
	//reducer to sort and write
	public static class MyReducer  extends Reducer<LongWritable, Text, Text, LongWritable>
	{
		// storage to reverse the order
		private Map<Long, String> viewedVdoMap;
		
		@Override
		public void setup(Context context) {
			// use treemap for sorting with Long key
			viewedVdoMap = new TreeMap<Long, String>(Collections.reverseOrder());
		}
		
		@Override
	    public void reduce(LongWritable viewCount, Iterable<Text> vdoIDs,Context context) throws IOException, InterruptedException {
	        // store in reverse order
			for (Text vdo : vdoIDs) {   
	    	   viewedVdoMap.put(viewCount.get(), vdo.toString());
	       }
	       
		}
		
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException {
			for(Map.Entry<Long,String> ent:viewedVdoMap.entrySet()) {
				context.write(new Text(ent.getValue()), new LongWritable(ent.getKey()));
			}
		}
	}
	
	//hadoop fs -mkdir youtube
	//hadoop fs -put youtubedata.txt youtube
	//hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.MostViewed youtube/youtubedata.txt youtube/youtube_most_viewed_out
    public static void main(String[] args) 
            throws IOException, ClassNotFoundException, InterruptedException {

			Configuration conf = new Configuration();
			conf.set("mapreduce.output.textoutputformat.separator", " ");
			Job job = Job.getInstance(conf);
		    job.setJarByClass(TopCategories.class);
		    job.setJobName("Most viewed vdos");
		    job.setMapperClass(MyMapper.class);
		    job.setNumReduceTasks(1);
		    job.setReducerClass(MyReducer.class);
		    job.setMapOutputKeyClass(LongWritable.class);
		    job.setMapOutputValueClass(Text.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(LongWritable.class);
		  
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		  
		    job.waitForCompletion(true);
		  
		}

}
