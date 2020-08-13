package jp.libern.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.PostDAO;
import jp.libern.model.PostdataBean;
import jp.libern.model.UserdataBean;

public class PostSearch {
	public void execute(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		UserdataBean uBean = null;

		HttpSession session = request.getSession(false);
		try{
			if(session != null){
				uBean = (UserdataBean) session.getAttribute("uBean");
				if(uBean != null){
					dao = new PostDAO();
					ArrayList<PostdataBean> postList = dao.getPostData(uBean);
					if(!postList.isEmpty()){
						request.setAttribute("postList", postList);
						request.setAttribute("userID", uBean.getUserID());
						request.setAttribute("userName", uBean.getUserName());
						request.setAttribute("userImage", uBean.getUserImage());
					}else{
						request.setAttribute("message", "No posted");
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
