package user.services.pattern;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import beans.pattern.AccessBean;
import beans.pattern.Action;
import beans.pattern.RestaurantInfo;


public class UserServices {
	private HttpServletRequest req;
	public UserServices(HttpServletRequest req) {
		this.req = req;
	}

	//검색창 
	public Action cmainCtl() {
		Action action = new Action();
		HttpSession session;
		String message ;
		AccessBean ab = new AccessBean();
		RestaurantInfo ri = new RestaurantInfo();
		ri.setWord(req.getParameter("word"));
		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(6);

		if(this.getSearchResult(dao, ri).isEmpty()) { //검색하신 결과가 NULL이 아니라면
			ab.setUserId((String)req.getSession().getAttribute("userId"));
			req.setAttribute("user", ab.getUserId());
			message = "ㅠㅠ검색하신 결과가 없습니다.ㅠㅠ";
			req.setAttribute("nullmessage", message);
			
		}else {
			ab.setUserId((String)req.getSession().getAttribute("userId"));
			req.setAttribute("list", this.makeHtml(this.getSearchResult(dao, ri)));
			req.setAttribute("user", ab.getUserId());
		}
		dao.dbClose();
		action.setPage("cMain.jsp");
		action.setRedirect(false);
		return action;
	}

	private ArrayList<RestaurantInfo> getSearchResult(DataAccessObject dao, RestaurantInfo ri) {
		return dao.getSearchResult(ri);
	}

	//비교검색 html
	private String makeHtml(ArrayList<RestaurantInfo> list) {
		StringBuffer sb = new StringBuffer();

		for(RestaurantInfo ri : list) {
			//System.out.println(ri.getMenu());
			sb.append("<div onClick=\'dayList(" + ri.getReCode()+")\'>-----------------------------------------");
			sb.append("<div class=\"storename\">" + ri.getRestaurant()+ "(" + ri.getCategory() + ")" + "</div>");
			sb.append("<span class= \"menu\">" + ri.getMenu() + "(" + ri.getPrice() + "원)" +"</span>");
			sb.append("<span>" + ri.getLocation() + "</span><br>");
			sb.append("-----------------------------------------</div>");
		}

		return sb.toString();

	}
	//예약가능한 날짜 조회
	public Action dayListCtl() {
		Action action = new Action();
		ArrayList<String> sevenDays = null;

		HttpSession session = req.getSession();

		if(!session.isNew() && session.getAttribute("userId")!=null) {
			RestaurantInfo ri = new RestaurantInfo();
			ri.setReCode(req.getParameter("reCode"));

			DataAccessObject dao = new DataAccessObject();
			dao.dbOpen(6);

			sevenDays = this.getDays();

			this.compareDate(sevenDays, this.getreservedDate(dao, ri));

			dao.dbClose();

			req.setAttribute("dayList", this.makeHtmlDayList(sevenDays,ri.getReCode()));
			action.setPage("cProcess1.jsp");
			action.setRedirect(false);

		}
		return action;
	}

	private String makeHtmlDayList(ArrayList<String> sevenDays, String reCode) {
		StringBuffer sb = new StringBuffer();

		sb.append("<div id=\"dayList\">");
		sb.append("<div class=\"step\">DayList</div>");
		for(String day : sevenDays) {
			sb.append("<div class=\"data\" onMouseOver=\"trOver('', this)\" onMouseOut=\"trOut(this)\"onClick = \"menuList(\'"+ reCode + "\' , \'" + day + "\')\">" + day + "</div>");
		}
		sb.append("</div>");

		return sb.toString();
	}

	//DB예약되어있는 날짜 불러온 메서드 (내일~7일이내)
	private ArrayList<String> getreservedDate(DataAccessObject dao, RestaurantInfo ri) {

		return dao.getreservedDate(ri);
	}

	//내일~7일까지의 날짜 
	private ArrayList<String> getDays() {
		int days = 8;
		ArrayList<String> sevenDays = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		for(int i=1; i <days; i++) {
			Calendar calendar = Calendar.getInstance(); //오늘 날짜를 불러옴
			calendar.add(Calendar.DAY_OF_MONTH, i);
			sevenDays.add(sdf.format(calendar.getTime()));
		}
		return sevenDays;
	}

	//7일간의 날짜와 예약되어있는 날짜들을 비교해서 그 날짜는 빼줌
	private void compareDate(ArrayList<String> sevenDays, ArrayList<String> reservedDate) {

		for(int days=(sevenDays.size()-1); days>=0; days--) { //days 7일동안의 날짜
			for(int reserved=0; reserved < reservedDate.size(); reserved++) { // reserved는 예약되어잇는 날짜
				if(sevenDays.get(days).equals(reservedDate.get(reserved))) {
					sevenDays.remove(days);
					break;
				}
			}
		}
	}


	//특정가게의 메뉴리스트 
	public String menuListCtl() {
		String menuList=null;
		RestaurantInfo ri = new RestaurantInfo();
		ri.setReCode(req.getParameter("reCode"));
		ri.setrDate(req.getParameter("day"));

		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(6);
		menuList =this.getMenu(dao, ri);

		dao.dbClose();

		return menuList;
	}

	private String getMenu(DataAccessObject dao,RestaurantInfo ri) {
		String menuList=null;
		Gson gson = new Gson();
		menuList = gson.toJson(dao.getMenu(ri));

		return menuList;
	}

	public Action reserveCtl() {
		Action action = new Action();
		ArrayList<RestaurantInfo> reserve = new ArrayList<RestaurantInfo>();
		String message =null;
		HttpSession session =req.getSession() ;

		//System.out.println(req.getParameter("data"));
		//데이터 넣기 (recode, mlcode, quantity, userId,date)
		Gson gson = new Gson();
		reserve = gson.fromJson(req.getParameter("data"), new TypeToken<ArrayList<RestaurantInfo>>(){}.getType());

		//메뉴가 여러개일수있기때문에 메뉴가 담긴 것들을 반복문으로 돌려서 넣어야한다.
		for(RestaurantInfo ri : reserve) {
			ri.setUserId((String)session.getAttribute("userId"));
			//			System.out.println(ri.getReCode());
			//			System.out.println(ri.getMlCode());
			//			System.out.println(ri.getQuantity());
			//			System.out.println(ri.getrDate());
			//			System.out.println(ri.getUserId());
		}
		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(6); dao.setTranConf(false);//자동커밋 풀었음
		if(this.insBookingList(reserve.get(0), dao)) {
			if(this.insBookingDetail(reserve, dao)) {
				dao.setTran(true);
				message = "정상적으로 예약이 처리되었습니다.가게의 확인을 기다려주세요.";
				req.setAttribute("msg", message);
			}else {
				dao.setTran(false);
			}
		}else {
			dao.setTran(false);
		}
		action.setPage("myPage");
		action.setRedirect(false);

		dao.setTranConf(true); dao.dbClose();
		return action;
	}
	//필요한 데이터 : reCode, userId, rDate, state
	private boolean insBookingList(RestaurantInfo ri, DataAccessObject dao) {

		return this.convertData(dao.insBookingList(ri));
	}
	//필요한 데이터 : reCode, userId, rDate, mlCode, quantity
	private boolean insBookingDetail(ArrayList<RestaurantInfo> list, DataAccessObject dao) {
		boolean result = false;

		for(RestaurantInfo ri : list) { //레코드 갯수만큼 반복 메뉴갯수만큼 
			result = this.convertData(dao.insBookingDetail(ri));//
			if(!result) {break;}
		}
		return result;
	}

	private boolean convertData (int data) {
		return (data>0)? true :false;
	}

	public Action myPageCtl() {
		Action action = new Action();

		HttpSession session = req.getSession();
		RestaurantInfo ri = new RestaurantInfo();

		ri.setUserId((String)session.getAttribute("userId"));

		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(6);


		req.setAttribute("bookingList", this.makeHtmlBookList(this.getBookList(dao, ri)));

		Gson gson = new Gson();
		req.setAttribute("MenuInfo", 
				gson.toJson(this.getBookDetail(dao, ri)));

		//req.setAttribute("bookingDetail", this.makeHtmlBookDetail(this.getBookDetail(dao, ri)));
		dao.dbClose();
		action.setPage("myPage.jsp");
		action.setRedirect(false);

		return action;
	}

	private ArrayList<RestaurantInfo> getBookList(DataAccessObject dao,RestaurantInfo ri) {

		return dao.getBookList(ri);
	}

	private String makeHtmlBookList(ArrayList<RestaurantInfo> list) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table>");
		sb.append("<tr><th>레스토랑</th><th>날짜</th><th>상태</th><th>위치</th></tr>");
		for(RestaurantInfo ri : list) {
			sb.append("<tr onClick=\"bookingDetail(\'" + ri.getReCode() + "\',\'" + ri.getrDate() +"\')\">");
			sb.append("<td>"+ ri.getRestaurant() +"</td>");
			sb.append("<td>"+ ri.getrDate() +"</td>");
			sb.append("<td>"+ ri.getState() +"</td>");
			sb.append("<td>"+ ri.getLocation() +"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	private ArrayList<RestaurantInfo> getBookDetail(DataAccessObject dao, RestaurantInfo ri) {

		return dao.getBookDetail(ri);
	}

	private String makeHtmlBookDetail(ArrayList<RestaurantInfo> list) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table>");
		sb.append("<tr><th>레스토랑</th><th>날짜</th><th>메뉴</th><th>가격</th><th>갯수</th><th>총금액</th></tr>");
		for(RestaurantInfo ri : list) {
			sb.append("<tr onClick=\"bookingDetail(\'" + ri.getReCode() + "\',\'" + ri.getrDate() +"\')\">");
			sb.append("<td>"+ ri.getReCode() +"</td>");
			sb.append("<td>"+ ri.getrDate() +"</td>");
			sb.append("<td>"+ ri.getMenu() +"</td>");
			sb.append("<td>"+ ri.getPrice() +"</td>");
			sb.append("<td>"+ ri.getQuantity() +"</td>");
			sb.append("<td>"+ ri.getAmount() +"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

}
