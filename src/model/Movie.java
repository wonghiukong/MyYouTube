package model;

import java.sql.Date;

public class Movie {
	private String movieId;
	private String title;
	private String ext;
	private Date uploadDate;
	private int totalRating;
	private int ratingCount;
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public Movie(String movieId, String title, Date uploadDate,
			int totalRating, int ratingCount) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.uploadDate = uploadDate;
		this.totalRating = totalRating;
		this.ratingCount = ratingCount;
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
}
