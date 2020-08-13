package jp.libern.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.UserDAO;
import jp.libern.model.UserdataBean;

public class SearchFollowing {
	public void executeMyFollowing(HttpServletRequest request) throws Exception{
		UserDAO dao = null;
		UserdataBean uBean = null;

		HttpSession session = request.getSession(false);
		try{
			if(session != null){
				uBean = (UserdataBean) session.getAttribute("uBean");
				if(uBean != null){
					dao = new UserDAO();
					ArrayList<UserdataBean> followingList = dao.getFollowingList(uBean.getUserNo());
					if(!followingList.isEmpty()){
						request.setAttribute("followingList", followingList);
						request.setAttribute("followingNum", followingList.size());
					}else{
						request.setAttribute("followingNum", 0);
						request.setAttribute("message", "Not following");
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
