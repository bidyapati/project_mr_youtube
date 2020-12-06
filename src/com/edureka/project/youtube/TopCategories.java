package com.edureka.project.youtube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// Find out the top 5 categories with maximum number of videos uploaded.
public class TopCategories {
	// inner mapper class  -  to count videos per category
	public static class MyMapper extends Mapper<LongWritable,Text, Text, LongWritable> {
        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {	    	  
            try{
               String[] str = value.toString().split("\t");	 
               String category = str[Utils.YOUTUBE_CATEGORY];
               context.write(new Text(category),new LongWritable(1));
            }
            catch(Exception e)
            {
               System.out.println(e.getMessage());
            }
         }  
    }
	
	
	//reducer to find category with max videos
	public static class ReduceSum  extends Reducer<Text,LongWritable,Text,LongWritable>
	{
		//data structure to store category and count
		// map cant be used to sort values
		class CategoryCount {
			public String category;
			public Long count;
			public CategoryCount(String cat, Long ct) {
				category = new String(cat);
				count = ct;
			}
		}
		
		// comparator for sorting with count
		class CompareCategoryCount implements Comparator<CategoryCount>{

			@Override
			public int compare(CategoryCount o1, CategoryCount o2) {
				//reverse sort(DESC order)
				return (int)(o2.count - o1.count);
			}
			
		}
		
		// store the category and count, to find max later
		private List<CategoryCount> lstCatVdo;

		@Override
	    public void setup(Context context) throws IOException, 
	                                     InterruptedException { 
			lstCatVdo = new ArrayList<CategoryCount>();
	    } 
		
		@Override
	    public void reduce(Text category, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
		   long count = 0;
	       for (LongWritable val : values) {   
	    	   count = count + val.get();  
	       }
	       
	       // just collect the counters for each category
	       lstCatVdo.add(new CategoryCount(category.toString(), count));
		}
	    
	    @Override
	    public void cleanup(Context context) throws IOException, 
	                                       InterruptedException { 
	    	// sort the list in desc order
	    	lstCatVdo.sort(new CompareCategoryCount());
	    	
	    	// finally print the top 5
	    	int counter = 5;
	    	for (CategoryCount c:lstCatVdo) {
	    		if (counter <= 0) {
	    			break;
	    		}
	    		
	    	    context.write(new Text(c.category), new LongWritable(c.count));
	    	    --counter;
	    	}
	    } 
	}
	
	//hadoop fs -mkdir youtube
	//hadoop fs -put youtubedata.txt youtube
	//hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopCategories youtube/youtubedata.txt youtube/youtube_top_category_out
    public static void main(String[] args) 
            throws IOException, ClassNotFoundException, InterruptedException {

			Configuration conf = new Configuration();
			conf.set("mapreduce.output.textoutputformat.separator", " ");
			Job job = Job.getInstance(conf);
		    job.setJarByClass(TopCategories.class);
		    job.setJobName("Top 5 movie category");
		    job.setMapperClass(MyMapper.class);
		    job.setNumReduceTasks(1);
		    job.setReducerClass(ReduceSum.class);
		    job.setMapOutputKeyClass(Text.class);
		    job.setMapOutputValueClass(LongWritable.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(LongWritable.class);
		  
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		  
		    job.waitForCompletion(true);
		  
		}
	

}
