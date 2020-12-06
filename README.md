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

B. Find out the top 10 rated videos.

	hadoop fs -mkdir youtube
	hadoop fs -put youtubedata.txt youtube
	hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopRated youtube/youtubedata.txt youtube/youtube_top_rated_out
    Result: [INCORRECT]
        5.0 niaeOlGRkFE
        4.99 cYbVkXai6Ec
        4.98 xe-f-zg_KIU
        4.97 gce0EWI0tEE
        4.96 JYrp6D9akQA
        4.95 X8LMuZUEzIs
        4.94 DwDy-mEJ3mU
        4.93 3cpQgrJrygo
        4.92 srHT9H7cb0I
        4.91 cT2Bb8r134E
	
C. Find out the most viewed videos.

	hadoop fs -mkdir youtube
	hadoop fs -put youtubedata.txt youtube
	hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.MostViewed youtube/youtubedata.txt youtube/youtube_most_viewed_out
    Result:
        12Z3J1uzd0Q 65341925
        4DC4Rb9quKk 33754615
        LU8DDYz68kM 27721690
        kHmvkRoEowc 18235463
        Md6rURKhZmA 18141492
        EwTZ2xpQwpA 16841569
	....
	....
	

Folder Clean up command:

    hadoop fs -rm -r youtube/youtube_top_rated_out
    hadoop fs -rm -r youtube/youtube_most_viewed_out
    hadoop fs -rm -r youtube/youtube_top_category_out
