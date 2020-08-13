package jp.libern.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.UserDAO;
import jp.libern.model.UserdataBean;

public class InsertUser {
	public boolean execute(HttpServletRequest request) throws Exception{
		UserDAO dao = null;
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		String userProfile = request.getParameter("userProfile");
		try{
			UserdataBean uBean = new UserdataBean();
			uBean.setUserID(userID);
			uBean.setPassword(password);
			uBean.setUserName(userName);
			uBean.setUserProfile(userProfile);
			dao = new UserDAO();
			int numRow = dao.insertUserdata(userID, password);
			if(numRow > 0){
				return true;
			}else{
				return false;
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}

	public boolean executeFollow(HttpServletRequest request) throws Exception{
		UserDAO dao = null;
		HttpSession session = request.getSession(false);
		UserdataBean uBean = (UserdataBean)session.getAttribute("uBean");
		int myUserNo = uBean.getUserNo();
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		try{
			dao = new UserDAO();
			int numRow = dao.insertMyFollowing(myUserNo, userNo);
			if(numRow > 0){
				return true;
			}else{
				return false;
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}
}
