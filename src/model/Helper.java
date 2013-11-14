package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Helper {
	public static final String connectionUrl = "jdbc:mysql://myyoutubedbinstance.cvcefbvtamqy.us-east-1.rds.amazonaws.com:3306/MyYouTubeDB";
	public static final String connectionUser = "Yotta";
	public static final String connectionPassword = "jiatianli";
	public static final String s3BucketName = "myyoutubebucket.team9";
	public static final String cloudFrontDomainName = "dkwhu4ttiu7iu.cloudfront.net";
	public static ArrayList<Movie> getSortedMovieList() {
		ResultSet rs = DBManager.query("SELECT * FROM Movie");
		ArrayList<Movie> movies = new ArrayList<Movie>();
		S3StorageManager S3sm = new S3StorageManager();
		try {
			while (rs.next()) {
				long movie_id = rs.getLong("movie_id");
				String title = rs.getString("title");
				String ext = rs.getString("ext");
				String upload_date = rs.getString("upload_date");
				int total_rating = rs.getInt("total_rating");
				int rating_count = rs.getInt("rating_count");
				String url = S3sm.getResourceUrl(s3BucketName, String.valueOf(movie_id));
				String mimeType = StorageObject.extToMIMETypeHashmap.get(ext);
				Movie movie = new Movie(movie_id, title, ext, upload_date, total_rating, rating_count, url, mimeType);
				movies.add(movie);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(movies, Collections.reverseOrder());
		return movies;
	}
}
