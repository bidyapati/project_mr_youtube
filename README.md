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

B. Find out the top 10 rated videos(rate value = rating * ratng count).

	hadoop fs -mkdir youtube
	hadoop fs -put youtubedata.txt youtube
	hadoop jar /mnt/home/edureka_1270998/jars/edurekaProjects.jar com.edureka.project.youtube.TopRated youtube/youtubedata.txt youtube/youtube_top_rated_out
    Result: 
        362269.32 rZBA0SKmQy8
        354563.88 4DC4Rb9quKk
        353264.22000000003 EwTZ2xpQwpA
        295552.18 kHmvkRoEowc
        287188.0 LU8DDYz68kM
        270210.92 Qit3ALTelOo
        214492.6 irp8CNj9qBI
        170396.64 LTxO_pgMqys
        168093.66 Md6rURKhZmA
        156571.84 EBM854BTGL0

	
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
