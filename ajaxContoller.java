package controller.pattern;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.pattern.Action;
import user.services.pattern.UserServices;

@WebServlet({"/menuList" })
public class ajaxContoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       UserServices us ;
    
    public ajaxContoller() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String jsonData = null;
		us = new UserServices(request);
		
		String jobCode = request.getRequestURI().substring(request.getContextPath().length()+1);
		
		if(jobCode.equals("menuList")) {
			jsonData =us.menuListCtl();
			//System.out.println(jsonData);
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter output = response.getWriter();
		output.print(jsonData);
		output.close();
		
	}

}
