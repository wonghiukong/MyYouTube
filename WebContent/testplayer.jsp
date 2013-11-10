<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<script src="http://jwpsrv.com/library/T_sUtkmnEeOliSIACqoGtw.js"></script>
	<title>Test jwPlayer</title>
</head>
<body>
	<br>Test jwplayer</br>
	<div id='playervciGxnpEWQEL'>This is player</div>
	<form>
	<button name="button" value="OK" type="button" onclick="playVideo('https://www.youtube.com/watch?v=IHQr0HCIN2w', 'Mexican Standoff (ft. Key & Peele)')">Click Me</button>
	</form>
	<script type='text/javascript'>
	function playVideo(filename, videoTitle)
	{
	    jwplayer('playervciGxnpEWQEL').setup({
	        file: filename,
	        title: videoTitle,
	        width: '50%',
	        aspectratio: '16:9',
	        fallback: 'false'
	    });
	}
	</script>
</body>
</html>