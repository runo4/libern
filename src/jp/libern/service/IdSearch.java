package jp.libern.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.libern.dao.UserDAO;
import jp.libern.model.UserdataBean;

public class IdSearch {
	public UserdataBean execute(HttpServletRequest request) throws Exception{
		UserDAO dao = null;
		UserdataBean uBean = null;

		String userID = request.getParameter("userID");

		try {
			if(userID != null && !userID.isEmpty()) {
				dao = new UserDAO();
				uBean = dao.getUserData(userID);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(dao != null) {
				dao.close();
			}
		}
		return uBean;
	}
}
