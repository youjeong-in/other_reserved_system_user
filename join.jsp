<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Page</title>
<script src="js/js.js"></script>
<link href="css/style.css" type="text/css" rel="stylesheet" />
</head>
<body onLoad="isDupCheck('${message}', '${uCode }')">
	<div id="accesszone">
		<div class="title">Join Page</div>
		<div class="inputzone">
			<div id="general" class="choiceOn" onClick="joinType(this)">General</div>
			<div id="restaurant" class="choiceOff" onClick="joinType(this)">Restaurant</div>
			<input type="hidden" name="accessType" value="G" />
		</div>
		<div class="inputzone">
			<div id="uCode" class="item">User Code</div>
			<div class="content">
				<input type="text" name="uCode" class="box" onKeyUp="korCheck(this, event)"/>
			</div>
		</div>
		<div class="inputzone">
			<div class="content"> </div>
			<div class="content">
				<input type="button" id="dupBtn" class="box" value="중복검사" onMouseOver="btnCss(this, true)" onMouseOut="btnCss(this, false)" onClick="dupCheck(this)"/>
			</div>
		</div>
		<div class="inputzone">
			<div class="item">Access Code</div>
			<div class="content"><input type="password" name="aCode" class="box" /></div>			
		</div>
		<div class="inputzone">
			<div class="item">Code Confirm</div>
			<div class="content"><input type="password" name="aCode" class="box" /></div>
		</div>
		<div class="inputzone">
			<div id="uName" class="item">User Name</div>
			<div class="content"><input type="text" name="uName" class="box" /></div>
		</div>
		<div class="content">
				<select name="location" class="box">
					<option>지역선택</option>
					<option value="I1">미추홀구</option>
					<option value="I2">남동구</option>
					<option value="I3">중구</option>
					<option value="I4">동구</option>
					<option value="I5">연수구</option>
					<option value="I6">계양구</option>
					<option value="I7">서구</option>
				</select>
			</div>
		<!-- Restaurant 가입 시 :  -->
		<div id="sel" class="inputzone" style="display:none;">
			<div class="content">
				<select name="rType" class="box">
					<option>분류선택</option>
					<option value="H">한식</option>
					<option value="I">분식</option>
					<option value="C">중식</option>
					<option value="A">양식</option>
				</select>
			</div>
		</div>	
		<div class="title" onClick="sendJoinInfo()">Submit</div>
	</div>
	<div style="text-align:center"><a href="http://192.168.219.127/LogInForm">로그인</a></div>
</body>
</html>