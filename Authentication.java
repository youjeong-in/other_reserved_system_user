package auth.services.pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.pattern.AccessBean;
import beans.pattern.Action;


public class Authentication {
	private HttpServletRequest req;
	public Authentication(HttpServletRequest req) {
		this.req = req;
	}

	public Action logInCtl() {
		HttpSession session;
		Action action = new Action();
		action.setPage("LogInForm");
		action.setRedirect(false);
		String message = "아이디나 패스워드가 맞지않습니다.";
		
		AccessBean ab = new  AccessBean();
		//데이터가져오기

		ab.setUserType(req.getParameter("accessType"));
		ab.setUserId(req.getParameter("uCode"));
		ab.setUserPassword(req.getParameter("aCode"));

		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(6);

		//id존재여부
		if(this.isIdCheck(dao, ab)) {
			//비밀번호 일치 여부
			if(this.isAccessCheck(dao, ab)) {
				session = req.getSession();
				session.setAttribute("userId", ab.getUserId());
				ab.setUserPassword(null);
				
				this.getUserInfo(dao, ab);
				req.setAttribute("user", ab.getUserId());
				action.setPage((ab.getUserType().equals("G"))? "cMain.jsp" :"Rmain");

			}else {
				req.setAttribute("message", message);
			}
		}else {
			req.setAttribute("message", message);
		}
		dao.dbClose();



		return action;
	}


	private boolean isIdCheck(DataAccessObject dao, AccessBean ab) {
		return this.covertToBoolean(ab.getUserType().equals("G")? dao.isUserIdCheck(ab) : dao.isReCodeCheck(ab));
	}

	private boolean isAccessCheck(DataAccessObject dao, AccessBean ab) {
		return this.covertToBoolean(ab.getUserType().equals("G")? dao.isUserAccessCheck(ab): dao.isReAccessCheck(ab));
	}
	
	private void getUserInfo(DataAccessObject dao, AccessBean ab) {
		dao.getUserInfo(ab);
	}

	private boolean covertToBoolean(int value) {
		return (value>0)? true : false;
	}
	//중복체크 
	public Action dupCheckCtl() {
		Action action = new Action();
		action.setPage("join.jsp");
		action.setRedirect(false);
		String message = "사용 불가능한 아이디입니다.";
		
		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(0);

		// Member Bean에 id injection
		AccessBean ab= new AccessBean();
		ab.setUserType(req.getParameter("accessType"));
		ab.setUserId(req.getParameter("uCode"));

		// id중복체크  : true >> 중복   false >> 사용가능 아이디
		if(!this.isIdCheck(dao, ab)) {
			// BackEnd 응답
			message = "사용 가능한 아이디입니다.";
			req.setAttribute("uCode", ab.getUserId());	
		}

		dao.dbClose();
		req.setAttribute("message", message);
		return action;
	}


	public Action JoinCtl() {
		Action action = new Action();
		action.setPage("JoinForm");
		action.setRedirect(true);
		//데이터 가져와서 빈에 담기
		AccessBean ab = new AccessBean();
		ab.setUserType(req.getParameter("accessType"));
		ab.setUserId(req.getParameter("uCode"));
		ab.setUserPassword(req.getParameter("aCode"));
		ab.setUserName(req.getParameter("uName"));
		ab.setLocation(req.getParameter("location"));

		if(ab.getUserType().equals("R")) {
			ab.setrType(req.getParameter("rType").charAt(0));

		}

		DataAccessObject dao = new DataAccessObject();
		dao.dbOpen(0); 

		if(this.insJoin(dao, ab)) {
			action.setPage("LogInForm");
			action.setRedirect(true);
		}

		dao.dbClose();

		return action;
	}


	private boolean insJoin(DataAccessObject dao,AccessBean ab) {
		int result;
		if(ab.getUserType().equals("G")) {
			result= dao.insUserJoin(ab);
		}else {
			result =dao.insReJoin(ab);
		}

		return this.covertToBoolean(result);
	}


}
