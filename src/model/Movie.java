package model;

import java.util.Date;

public class Movie implements Comparable<Movie> {
	private long movieId;
	private String title;
	private String ext;
	private String MIMEType;
	private Date uploadDate;
	private int totalRating;
	private int ratingCount;
	private String url;
	private byte [] data;
	
	public String getCloudFrontUrl() {
		return "https://" + Helper.cloudFrontDomainName + "/" + movieId;
	}
	
	public long getMovieId() {
		return movieId;
	}
	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}
	public String getMIMEType() {
		return MIMEType;
	}
	public void setMIMEType(String MIMEType) {
		this.MIMEType = MIMEType;
	}
	public Movie() {
	}
	
	public Movie(long movieId, String title, String ext, Date uploadDate,
			int totalRating, int ratingCount, String url, String mimeType) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.ext = ext;
		this.uploadDate = uploadDate;
		this.totalRating = totalRating;
		this.ratingCount = ratingCount;
		this.url = url;
		this.MIMEType = mimeType;
	}
	
	public Movie(long movieId, String title, String ext, Date uploadDate,
			int totalRating, int ratingCount, String url, byte[] data) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.ext = ext;
		this.uploadDate = uploadDate;
		this.totalRating = totalRating;
		this.ratingCount = ratingCount;
		this.url = url;
		this.data = data;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public int getTotalRating() {
		return totalRating;
	}
	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public double getRating() {
		if (ratingCount == 0)
			return 0;
		else
			return (double)totalRating / ratingCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/*
    **  Implement the natural order for this class
    */
    public int compareTo(Movie rhs)
    {
    	if (getRating() < rhs.getRating())
    		return -1;
    	if (getRating() > rhs.getRating())
    		return 1;
    	return 0;
    }
    public String toString() {
    	String info = "ID: " + movieId 
    				+ ", title: " + title
					+ ", Upload_date: " + uploadDate 
					+ ", Total_rating: " + totalRating
					+ ", Rating_count: " + ratingCount
					+ ", Rating: " + getRating()
					+ ", url: " + url
					+ ", ext: " + ext
    				+ ", mimeType: " + MIMEType;
    	return info;
    }
}
