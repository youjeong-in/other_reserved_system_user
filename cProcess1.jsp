<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반회원 예약진행서비스</title>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<script src="js/js.js"></script>
<script> let menuCheck;
</script>
</head>
<body>
<div >
 ${dayList }

 <div id ="menuList"></div></div>
 <div id ="orderList"></div>
<input type = "button" value = "예약하기" onClick = "callData()"/>
</body>
</html>