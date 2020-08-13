package jp.libern.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.PostDAO;
import jp.libern.model.PostdataBean;
import jp.libern.model.UserdataBean;

public class InsertPost {
	public boolean execute(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		String post = request.getParameter("post");
		post = post.replaceAll("\r\n",  "<br />");
		//投稿ユーザー情報の取得
		HttpSession session = request.getSession(false);
		UserdataBean uBean = (UserdataBean)session.getAttribute("uBean");
		int userNo = uBean.getUserNo();
		try{
			PostdataBean pBean = new PostdataBean();
			pBean.setMessage(post);
			dao = new PostDAO();
			int numRow = dao.insertPostdata(userNo, post);
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
