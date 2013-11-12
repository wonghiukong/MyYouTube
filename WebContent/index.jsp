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
		function playAll(url, mimeType)
		{
			
			playHTML5Video(url, mimeType);
		}
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
		function playHTML5Video(url, mimeType)
		{
			var source = document.getElementById('video_src');
			source.setAttribute('src', url);
			source.setAttribute('type', mimeType);
			var video = document.getElementById('html5_video');
			if (!video.paused)
				video.pause();
			video.src = url;
			video.play();
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
		<script>
		jwplayer('playervciGxnpEWQEL').setup({
		        file: 'https://dkwhu4ttiu7iu.cloudfront.net/43',
		        width: '50%',
		        provider: 'video',
		        aspectratio: '16:9',
		        fallback: 'false'
		    });
		</script>
	</div>
	<div>
		<video id='html5_video' width="640" height="480" controls name="media">
			<source id='video_src' src="https://dkwhu4ttiu7iu.cloudfront.net/43" type="video/mp4">
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
			for(Movie movie: movieList){
				out.println(movie.toString());
		%>
		<tr>
		<%
				out.println("<td>"+ movie.getTitle() +"</td>");
		%>
			<td>
				<button name="playButton" value="OK" type="button" onclick="playAll('<%out.print(movie.getCloudFrontUrl());%>','<%out.print(movie.getMIMEType());%>')">Play</button>
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
		</table>
	</div>
</body>
</html>