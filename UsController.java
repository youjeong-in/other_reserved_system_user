package controller.pattern;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.services.pattern.Authentication;
import beans.pattern.Action;
import restaurant.services.pattern.RestaurantServices;
import user.services.pattern.UserServices;

@WebServlet({"/LogInForm", "/JoinForm", "/LogIn", "/Join", "/DupCheck" , "/Cmain" , "/DayList", "/Orders","/myPage"})
public class UsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// LogInForm, JoinForm
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		String page;
		
		if(jobCode.equals("LogInForm")) {
			page = "access.jsp";
		}else if(jobCode.equals("JoinForm")){
			page = "join.jsp";
		}else {
			page = "warnning.jsp";
		}
		
		response.sendRedirect(page);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// LogIn, Join
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		Authentication auth =  new Authentication(request);
		UserServices us = new UserServices(request);
		RestaurantServices rs = null;
		Action action = null;

		if(jobCode.equals("LogIn")) {
			action = auth.logInCtl();
		}else if(jobCode.equals("Join")) {
			action = auth.JoinCtl();
		}else if(jobCode.equals("Cmain")) {
			action = us.cmainCtl();
		}else if(jobCode.equals("LogInForm")) {
			action = new Action();
			action.setPage("access.jsp");
			action.setRedirect(true);
		}else if(jobCode.equals("JoinForm")) {
			action = new Action();
			action.setPage("join.jsp");
			action.setRedirect(true);
		}else if(jobCode.equals("DupCheck")) {
			action =auth.dupCheckCtl();
		}else if(jobCode.equals("DayList")) {
			action = us.dayListCtl();
		}else if(jobCode.equals("Orders")) {
			action = us.reserveCtl();
		}else if(jobCode.equals("myPage")) {
			action=us.myPageCtl();
		}
		
		else {

		}

		if(action.isRedirect()) {
			response.sendRedirect(action.getPage());
		}else {
			RequestDispatcher rd = request.getRequestDispatcher(action.getPage());
			rd.forward(request, response);
		}

	}

}
