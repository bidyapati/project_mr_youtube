# Map Reduce Project for Youtube data

A. Find out the top 5 categories with maximum number of videos uploaded.

	hadoop fs -mkdir youtube
	hadoop fs -put youtubedata.txt youtube
	hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopCategories youtube/youtubedata.txt youtube/youtube_top_category_out
	
    Result:
        Entertainment 908
        Music 862
        Comedy 414
        People & Blogs 398
        News & Politics 333
