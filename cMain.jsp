<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반사용자 페이지</title>
<script src="js/js.js"></script>
<link href="css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div>${user}님의 페이지</div>
	<div id="searchzone">
		<input type="text" class="searchbox" name="word" placeholder="어떤메뉴 또는 가게를 찾으시나요?"/>
		<input type="button" class="btn" value="검색" onClick="search()" />
		
	</div>
	<div id="searchresult">
		${list }
		${nullmessage }
	</div>
	<input type = "button" class ="mypagebtn" value="마이페이지" onClick="myPage()"/>
</body>
</html>