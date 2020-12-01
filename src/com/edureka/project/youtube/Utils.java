package com.edureka.project.youtube;

public class Utils {
	// file names
	// youtubedata file
	public final static String FILE_YOUTUBE = "youtubedata.txt";
	
	//data separator
	public final static String COMMA_SEP = ",";
	public final static String SPACE_SEP = " ";
	
	/*
	Youtube data set i.e youtubedata.txt
	0 Video ID - Text
	1 Uploader - Text
	2 Upload Days - Long
	3 Category of the video - Text
	4 Length of the video - Long
	5 Number of views - Long
	6 Rating - Double
	7 Number of Rating - Long
	8 Number of comments - Long
	9 Related video ID list - Text Array
	 */
    public final static Integer YOUTUBE_ID = 0;//TEXT
    public final static Integer YOUTUBE_UPLOADER = 1;//TEXT
    public final static Integer YOUTUBE_DAYS = 2;//LONG
    public final static Integer YOUTUBE_CATEGORY = 3;//TEXT
    public final static Integer YOUTUBE_LENGTH  = 4;//LONG
    public final static Integer YOUTUBE_VIEWS_COUNT = 5;//LONG
    public final static Integer YOUTUBE_RATING = 6;//DOUBLE
    public final static Integer YOUTUBE_RATING_COUNT = 7;//LONG
    public final static Integer YOUTUBE_COMMENT_COUNT = 8;//LONG
    public final static Integer YOUTUBE_RELATED_VDOS = 9;//TEXT Array
}
