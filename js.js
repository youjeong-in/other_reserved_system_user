
function callType(object){
	// Click된 Div의 선택(색상 바꾸기):: default: #F6F6F6 << bg >> #FFBB00
	// general  restaurant
	let accessType = document.getElementsByName("accessType")[0];
	let objectId;
	if(object.id == "general"){
		objectId = "restaurant";
		accessType.value = "G";
	}else{
		objectId = "general";
		accessType.value = "R";
	}
	
	object.className = "choiceOn";
	document.getElementById(objectId).className = "choiceOff";

}

/* 회원가입 유형에 따른 CSS */
function joinType(object){
	let accessType = document.getElementsByName("accessType")[0];
	
	let objectId;
	if(object.id == "general"){
		objectId = "restaurant";
		accessType.value = "G";
		
		uCode.innerText = "User Code";
		//uCode.placeholder = "Your ID";
		uName.innerText = "User Name";
		//uName.placeholder = "Your Name";	
		
		
		sel.style.display = "none";
	}else{
		objectId = "general";
		accessType.value = "R";
		// innerHtml | innerText
		uCode.innerText = "Restaurant Code";
		//uCode.placeholder = "레스토랑 코드";
		uName.innerText = "Restaurant Name";
		//uName.placeholder = "상호명";	
	
		
		sel.style.display = "block";			
	}
	
	object.className = "choiceOn";
	document.getElementById(objectId).className = "choiceOff";
}

function isIdCheck(word, type){
	const cuComp = /^[A-Z]{1}[A-Z0-9]{4,9}$/;
	const reComp = /^[0-9]{10}$/;
	let result;

	if(type){
		result = cuComp.test(word);
	}else{
		result = reComp.test(word);
	}
	
	return result;
}

function isPasswordCheck(word){
	const sEng = /[a-z]/;
	const bEng = /[A-Z]/;
	const num = /[0-9]/;
	const special = /[!@#$%^&*]/; 
	
	// password가 영문소문자, 영문대문자, 숫자, 특수문자 중 3가지 이상의 문자군을 사용했는지 여부
	let count = 0;
	if(sEng.test(word)){	count++; }
	if(bEng.test(word)){	count++; }
	if(num.test(word)){	count++; }	
	if(special.test(word)){	count++; }
	
	return count;
}

function charCount(word, min, max){
	return word.length >= min && word.length <= max;
}

function sendAccessInfo(){
	// html 객체 연결 : id, password
	let id = document.getElementsByName("uCode")[0];
	let password = document.getElementsByName("aCode")[0];
	let accessType = document.getElementsByName("accessType")[0];
	// id가 영어소문자로 시작하고 숫자를 포함할 수 있으면서 전체 길이는 5~10
	
	if(accessType.value == "G"){
		if(!isIdCheck(id.value, true)) {
			id.value="";
			id.focus();
			return;
		}
	}else{
		if(!isIdCheck(id.value, false)) {
			id.value="";
			id.focus();
			return;
		}
	}
	
	if(isPasswordCheck(password.value) < 3){
		password.value="";
		password.focus();
		return;
	}
	
	// password 길이 파악 : 7~12
	if(!charCount(password.value, 7, 12)){
		password.value = "";
		password.focus();
		return;
	}
	
	// form 객체 생성
	let f = document.createElement("form");
	f.method = "post"; //f.setAttribute("method", "post");
	f.action = "LogIn";
	// id와 password를 form자식으로 입양
	f.appendChild(id);
	f.appendChild(password);
	f.appendChild(accessType);
	// form을 body자식으로 입양
	document.body.appendChild(f);
	
	f.submit();
}

function korCheck(obj, event){
	const pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	
	if(pattern.test(event.target.value.trim())) {
		obj.value = obj.value.replace(pattern,'').trim();
	}
}

function btnCss(object, state){
	object.style.backgroundColor = state? "#FFBB00":"#F6F6F6";
	object.style.color = state? "#FFFFFF":"#2478FF";
}

function dupCheck(obj){
	let uCode = document.getElementsByName("uCode")[0];
	let accessType = document.getElementsByName("accessType")[0];
	
	if(obj.value != "재입력"){
		// 아이디 유효성 검사
		if(accessType.value == "G"){
			if(!isIdCheck(uCode.value, true)){
				uCode.value = "";
				uCode.focus();
				return;
			}
		}else{
			if(!isIdCheck(uCode.value, false)){
			uCode.value = "";
			uCode.focus();
			return;
			}
		}
		
		let f = document.createElement("form");
		f.method = "post"; 
		f.action = "DupCheck";
		
		f.appendChild(uCode); 
		f.appendChild(accessType); 
		document.body.appendChild(f);
		f.submit();
	}else{
		uCode.value = "";
		uCode.readOnly = false;
		uCode.focus();
		obj.value = "중복검사";	
	}
}

function sendJoinInfo(){
	// HTML Object 연결
	let accessType = document.getElementsByName("accessType")[0];
	let uCode = document.getElementsByName("uCode")[0];
	let aCode = document.getElementsByName("aCode");
	let uName = document.getElementsByName("uName")[0];
	let location = document.getElementsByName("location")[0];
	
	/* Restaurant 가입 시*/
	if(accessType.value == "R"){
		let rType = document.getElementsByName("rType")[0];
	}
	
	// 패스워드 유효성 체크 // 패스워드 일치여부 체크
	if(isPasswordCheck(aCode[0].value) < 3 || aCode[0].value != aCode[1].value){
		aCode[0].value=""; aCode[1].value="";
		aCode[0].focus();
		return;
	}
	
	// 사용자명 입력여부 체크
	if(uName.value == ""){
		uName.focus();
		return;
	}
	//지역선택여부
	if(location.value == "지역선택"){ // location.selectedIndex < 1
			location.focus();
			return;
		}
	
	// 분류선택 여부
	if(accessType.value == "R"){
		if(rType.value == "분류선택"){ // rType.selectedIndex < 1
			rType.focus();
			return;
		}
	}
	
	// form 생성
	let f = document.createElement("form");
	f.method = "post";
	f.action = "Join";
	// 아이디, 패스워드, 사용자명, 사용자전화번호 를 form의 자식으로 편입
	f.appendChild(accessType);
	f.appendChild(uCode); 
	f.appendChild(aCode[0]);
	f.appendChild(uName);
	f.appendChild(location); 
	
	if(accessType.value == "R"){
		f.appendChild(rType);
	}
	
	// form을 body의 자식으로 편입
	document.body.appendChild(f);
	// 전송
	f.submit();
}

function isDupCheck(message, userId){
	let uCode = document.getElementsByName("uCode")[0];
	let dupBtn = document.getElementById("dupBtn");
	if(message != ""){
		let result = confirm(message + "사용하시겠습니까?");
		if(result){
			uCode.value = userId;
			uCode.readOnly = true;
			dupBtn.value = "재입력";
		}
	}
}

function callMessage(message){
	if(message != ""){
		alert(message);
	}
}

function search(){
	// HTML Object 연결 getElementsByName("word")[0]
	let word = document.getElementsByName("word")[0];
	
	// 검색어 입력 여부 확인
	if(word.value == ""){ 
		alert("검색어를 입력해주세요");
		word.focus();
		return;
	}
	

	let f = document.createElement("form");
	f.method = "post";
	f.action = "Cmain";
	
	// Form 개체 : HTML Object를 자식으로 편성
	f.appendChild(word);
	// Form 개체 : body의 자식으로 편성
	document.body.appendChild(f);
	// Form 전송
	f.submit();
}

/* 날짜 출력 */
function dayList(rCode){
	let reCode = makeInput("text", "reCode", rCode);
	let f = document.createElement("form");
	f.action = "DayList";
	f.method = "post";
	f.appendChild(reCode);
	
	document.body.appendChild(f);
	f.submit();
}

function rMain_init(message){
	if(message != "") alert(message);
	let list = document.getElementsByName("list");
	list[1].style.display = "none";
	
}

function selectRestaurant(cate, reCode, cuCode, reDate, mCode){
	if(cate){
		let check = confirm("예약을 확정하시겠습니까?");
		if(!check) return;
	
		// input 개체 생성
		// form 생성
		let f = document.createElement("form");
		f.action = "ConfirmReserve";
		f.method = "post";
	
		f.appendChild(makeInput("hidden", "reCode", reCode));
		f.appendChild(makeInput("hidden", "cuCode", cuCode));
		f.appendChild(makeInput("hidden", "dbDate", reDate));
	
		document.body.appendChild(f);
	
		f.submit();
	}else{
		alert("금일예약현황");
	}
}

function makeInput(type, name, value){
	let input = document.createElement("input");
	input.type = type;
	input.name = name;
	input.value = value;
	
	return input;
}

function showDiv(index){
	let list = document.getElementsByName("list");
	
	if(index){
		list[0].style.display = "block";
		list[1].style.display = "none";
	}else{
		list[0].style.display = "none";
		list[1].style.display = "block";
	}
	
}

function trOver(comments, obj){
	obj.className = "mouseOver";
}
function trOut(obj){
	obj.className = "mouseOut";
}

function menuList(reCode, day){
	let orders = document.getElementsByName("orders");
	if(orders != null){
		for(index=orders.length-1; index >= 0; index--){
			orders[index].remove();
		}
	}
	
	/* Step 1*/
	let ajax = new XMLHttpRequest();
	
	/* Step2 */
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			/* Step 5 */
			const jsonData = JSON.parse(ajax.responseText);
			makeHtml(jsonData,day);
			
		}
	};
	
	/* Step 3 */
	ajax.open("post", "menuList", true); //비동기화 방식. page는 안넘김
	/* Step 4 */
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	reCode = encodeURIComponent(reCode);
	day = encodeURIComponent(day);
	let data = "reCode="+reCode+"&day="+day;
	ajax.send(data);

}

function makeHtml(jsonData,day){
	menuCheck = new Array(jsonData.length);
	for(index=0; index<menuCheck.length; index++){
		menuCheck[index] = false;
	}
	
	let menuList = document.getElementById("menuList");
	let menu = "<div class=\"step\">MENU LIST</div>";
	
	for(index=0;index<jsonData.length;index++){
		let info = jsonData[index].reCode + ":" + jsonData[index].mlCode + ":" + jsonData[index].menu  + ":" + jsonData[index].price + ":" + index + ":" + day ;
		menu += "<div class=\"data\" onMouseOver=\"trOver(\'\', this)\" onMouseOut=\"trOut(this)\" onClick=\"orders(\'"+ info +"\')\">" + jsonData[index].menu + "</div>";
	}
	menuList.innerHTML = menu;
}

function orders(textinfo){
	let orderList = document.getElementById("orderList");
	const info = textinfo.split(":");
	
	if(document.getElementById("title") == null){
		let div = document.createElement("div");
		div.id = "title";
		div.className ="step";
		div.innerText = "ORDER LIST";
	
		orderList.appendChild(div);
	}
	
	
	if(menuCheck[info[4]] == false){
		
		let menu = document.createElement("div");
		menu.setAttribute("name", "orders");
		menu.setAttribute("data-day", info[5]);
		menu.setAttribute("data-recode", info[0]);
		menu.setAttribute("data-menucode", info[1]);
		menu.setAttribute("data-qty", 0);
		menu.className 	= "data";
		
		menu.addEventListener("mouseover", function(event){
			trOver('', this);
			});
		menu.addEventListener("mouseout", function(event){
			trOut(this);
			});
		
		let menuName = document.createElement("span");
		menuName.innerText = info[2];
		
		menuName.addEventListener("click", function(event){
				objRemove(this.parentNode, info[4]);
			});
		
	
		//let input = document.createElement("input");
		//input.id = "quantity";
		//input.type = "number";
		//input.value = "1";
		let input = makeInput("number", "quantity",1);
		input.min = "1";
		input.max = "10";
		
		let count = parseInt(input.valueAsNumber);
		let menu1 = parseInt(info[3]);
		console.log(typeof count);
		console.log(typeof menu1);
		console.log(count);
		console.log(menu1);
		console.log(input.value);
		let sum = Number(menu1*count);
		
		console.log(typeof sum);
		
		//eval("info[3]*count");
		let menuPrice = document.createElement("span");
		menuPrice.innerText = sum; //가격
		
		
		
		menu.appendChild(menuName);
		menu.appendChild(menuPrice);
		menu.appendChild(input);
		
	
		
		orderList.appendChild(menu);
		menuCheck[info[4]] = true;
	}
}


function objRemove(obj, index){
	obj.remove();
	menuCheck[index] = false;
}

function callData(){
	let orders = document.getElementsByName("orders"); //orders위에 있음.
	if(orders == null) return;
	
	const json = [];
	for(index=0; index < orders.length; index++){
		json.push({reCode: orders[index].dataset.recode, mlCode: orders[index].dataset.menucode, rDate: orders[index].dataset.day, quantity:orders[index].childNodes[2].value});
	}
	let jsonData = JSON.stringify(json);
	
	alert(jsonData);
	let form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", "Orders");
	
	let input = makeInput("hidden", "data", jsonData);
	form.appendChild(input);
	
	document.body.appendChild(form);
	form.submit();
}

function callmsg(message){
	if(message!=""){
		alert(message);
	}
}
function message(msg){
	
	if(msg!=""){
		alert(msg);
	}
}

function myPage(){
	
	let form = document.createElement("form");
	
	form.method = "post";
	form.action = "myPage";
	
	document.body.append(form);
	form.submit();
}

function backmove(){
	let form = document.createElement("form");
	
	form.method = "post";
	form.action = "Cmain";
	
	document.body.append(form);
	form.submit();
}

function bookingDetail(reCode,rDate){
	//alert(reCode + ":" + rDate);
	//alert(jsonMenuInfo[0].primarykey);//menuInfo에서 있는 primarykey
	//alert(pk);//server에서 보낸내용
	let popupzone = document.getElementById("popupzone");
	let menu = document.getElementById("menu");
	let html;
	if(jsonMenuInfo.length>0){
	
	html = "<table>";
	html += "<tr><th>메뉴</th><th>가격</th><th>갯수</th><th>합계</th></tr>";
	for(i=0; i<jsonMenuInfo.length; i++){
		if(reCode == jsonMenuInfo[i].reCode && rDate == jsonMenuInfo[i].rDate){
			//alert(jsonMenuInfo[i].menu + " : " + jsonMenuInfo[i].price + " : " + jsonMenuInfo[i].quantity + " : " + jsonMenuInfo[i].amount);
			html += "<tr>";
			html+= "<td>" +jsonMenuInfo[i].menu + "</td>";
			html+= "<td>" +jsonMenuInfo[i].price + "</td>";
			html+= "<td>" +jsonMenuInfo[i].quantity + "</td>";
			html+= "<td>" +jsonMenuInfo[i].amount + "</td>";
			html += "</tr>";
		}
		
	}html+= "</table>";
	 html+= "<div><span class = \"close\" onClick=\"popupClose()\"><img  src=images/delete.jpeg></span></div>";
	//console.log(html);	
	}
	menu.innerHTML = html;
	popupzone.style.display = "block";
}

function popupClose(){
	let popupzone = document.getElementById("popupzone");
	
	popupzone.style.display = "none";
	
}




/* AJAX(Asynchronous Javascript And XML)
		: 비동기식 서버통신
		* reference : https://developer.mozilla.org/ko/docs/Web/API/XMLHttpRequest
		
		XMLHttpRequest 개체 주로 사용   :: jQuery >> .ajax 
		 : Page(client page)전송 없이 Page의 일부분을 전송 << 페이지 새로고침 X
	   : 주로 Mobile 환경에서 많이 사용 >> 웹 환경에서도 사용 범위는 확대~~~~
		 
	  Step 1 : javascriopt :: XMLHttpRequest 개체 생성
		Step 2 : XMLHttpRequest개체의 환경 설정 :: 
							응답에 대한 처리 + 서버와의 통신 처리 방식 + 송수신 데이터 처리 방식
					readyState : 0  >> open()호출되지 않음. request가 초기화 되지 않음.
										 : 1  >> loading 서버와의 연결 성공 open()에 의해 실행 send()는 호출되지 않음
										 : 2  >> 서버에서 클라이언트가 전송한 request를 받음. send()호출
										 : 3  >> 서버에서 request를 처리하는 중
										 : 4  >> request에 대한 응답 처리가 준비되어 있음
					status		 : 200  204  >> 응답이 클라이언트에 도착
										 : 400  >> Bad Request
										 : 500	>> Interval Server Error  >> backend 에러
									   : 503  >> Service Unavailable
		Step 3 : open()호출 >> server와의 연결
		Step 4 : 서버로 데이터 전송
		Step 5 : 요청에 대한 응답을 Client에서 처리 단계
 */

/* JSON(javaScript Object Notation) : server ==> client
	 [{"reCode":"0123456789", "":""}, {}] JSON.parse ==>  let reCode = "0123456789";

	FrontEnd에서 사용되어지는 JSON 관련 함수
	- JSON.parse()  --> JSON형식의 문자열을 JSON 객체로 변환
	- JSON.stringify() --> JSON형식의 문자열로 변환
*/

