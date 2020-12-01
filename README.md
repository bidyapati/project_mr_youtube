# Map Reduce Project for Youtube data
A. Find out the top 5 categories with maximum number of videos uploaded.

	hadoop fs -mkdir youtube
	hadoop fs -put youtubedata.txt youtube
	hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopCategories youtube/youtubedata.txt youtube/youtube_top_category_out
