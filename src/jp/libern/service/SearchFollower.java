package jp.libern.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.UserDAO;
import jp.libern.model.UserdataBean;

public class SearchFollower {
	public void executeMyFollower(HttpServletRequest request) throws Exception{
		UserDAO dao = null;
		UserdataBean uBean = null;

		HttpSession session = request.getSession(false);
		try{
			if(session != null){
				uBean = (UserdataBean) session.getAttribute("uBean");
				if(uBean != null){
					dao = new UserDAO();
					ArrayList<UserdataBean> followerList = dao.getFollowerList(uBean.getUserNo());
					if(!followerList.isEmpty()){
						request.setAttribute("followerList", followerList);
						request.setAttribute("followerNum", followerList.size());
					}else{
						request.setAttribute("followerNum", 0);
						request.setAttribute("message", "No Follower");
					}
				}
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}
}
