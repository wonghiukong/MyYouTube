package model;

import java.util.ArrayList;

public class Helper {
	public static final String connectionUrl = "jdbc:mysql://myyoutubedbinstance.cvcefbvtamqy.us-east-1.rds.amazonaws.com:3306/MyYouTubeDB";
	public static final String connectionUser = "Yotta";
	public static final String connectionPassword = "jiatianli";
	public static final String s3BucketName = "MyYouTubeBucket.team9";
	public static ArrayList<Movie> getSortedMovieList() {
		return new ArrayList<Movie>();
	}
}
