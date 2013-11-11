<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<script src="http://jwpsrv.com/library/T_sUtkmnEeOliSIACqoGtw.js"></script>
	<title>MyYouTube</title>
</head>
	<script type='text/javascript'>
		function playVideo(filename)
		{
		    jwplayer('playervciGxnpEWQEL').setup({
		        file: filename,
		        width: '50%',
		        aspectratio: '16:9',
		        fallback: 'false'
		    });
		    jwplayer().play();
		}
	</script>
<body>
	
	<div align="center"><h1>My YouTube</h1></div>
	<div id='upload_div'>
		<h2>Upload</h2>
		<form action="UploadServlet" method="post" enctype="multipart/form-data">
			<p>
				Please specify a video file to upload<br>
				<input type="file" name="movie_file" size="40">
			</p>
			<input type="submit" value="Upload">
		</form>
		<form action="index.jsp">
		    <input type="submit" value="List">
		</form>
	</div>
	<div><h2>Player</h2></div>
	<div id='playervciGxnpEWQEL'>
		<!-- <script>
		jwplayer('playervciGxnpEWQEL').setup({
		        file: 'http://dkwhu4ttiu7iu.cloudfront.net/23',
		        width: '50%',
		        aspectratio: '16:9',
		        fallback: 'false'
		    });
		</script> -->
		<video controls="" autoplay="" name="media">
			<source src="http://dkwhu4ttiu7iu.cloudfront.net/23" type="video/mp4">
		</video>
	</div>
	<p>
	<div id='videolist_div'>
		<div><h2>Video List</h2></div>
		<table border="1">
		<tr>
			<th>Title</th>
			<th>Play</th>
			<th>Date</th>
			<th>Rating</th>
			<th>Rate</th>
			<th>Delete</th>
		</tr>
		<% ArrayList<Movie> movieList = Helper.getSortedMovieList();
			Movie mov = new Movie();
			mov.setMovieId(3208);
			mov.setRatingCount(10);
			mov.setTotalRating(120);
			mov.setTitle("Star War");
			mov.setUploadDate(new Date());
			mov.setUrl("www.java.com");
			movieList.add(mov);
			for(Movie movie: movieList){
		%>
		<tr>
		<%
				out.println("<td>"+ movie.getTitle() +"</td>");
		%>
			<td>
				<button name="playButton" value="OK" type="button" onclick="playVideo('<% out.print(movie.getUrl()); %>')">Play</button>
			</td>
		<%
				out.println("<td>"+ movie.getUploadDate() +"</td>");			
				out.println("<td>"+ String.format("%.1f", movie.getRating())  +"</td>");
		%>
			<td>
				<form action="RateServlet" method="post">
					<input type="hidden" name="movie_id" value="<% out.print(movie.getMovieId()); %>">
					<select name="rating">
						<option selected value="0">0
						<option value="1">1
						<option value="2">2
						<option value="3">3
						<option value="4">4
						<option value="5">5
					</select>
					<input type="submit" value="Rate">
				</form>
			</td>
			<td>
				<form action="DeleteServlet" method="post">
					<input type="hidden" name="movie_id" value="<% out.print(movie.getMovieId()); %>">
					<input type="submit" value="Delete">
				</form>
			</td>
		</tr>
		<%
			}
		%>
		<tr>
			<td>Star Trek</td>
			<td>
				<button name="playButton" value="OK" type="button" onclick="playVideo('https://www.youtube.com/watch?v=IHQr0HCIN2w')">Play</button>
			</td>
			<td>2013-10-23</td>
			<td>4.2</td>
			<td>
				<form action="RateServlet" method="post">
					<input type="hidden" name="movie_id" value="23">
					<select name="rating">
						<option selected value="0">0
						<option value="1">1
						<option value="2">2
						<option value="3">3
						<option value="4">4
						<option value="5">5
					</select>
					<input type="submit" value="Rate">
				</form>
			</td>
			<td>
				<form action="DeleteServlet" method="post">
					<input type="hidden" name="movie_id" value="23">
					<input type="submit" value="Delete">
				</form>
			</td>
		</tr>
		</table>
	</div>
</body>
</html>