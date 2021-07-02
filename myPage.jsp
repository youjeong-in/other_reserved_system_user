<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="js/js.js"></script>
<script> let jsonMenuInfo= JSON.parse(JSON.stringify(${MenuInfo})); 
</script>
<link href="css/style.css" type="text/css" rel="stylesheet" />
<title>mypage</title>
</head>
<body onLoad = "message('${msg}')">

	<div>${bookingList }</div>
	<input  type = button value="처음으로" onClick="backmove()"/>
	
  	<div id = "popupzone" class="bg">
	<div id=menu class = "contents"></div>

</body>
</html>