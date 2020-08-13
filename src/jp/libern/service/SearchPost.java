package jp.libern.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.libern.dao.PostDAO;
import jp.libern.model.PostAndUserBean;
import jp.libern.model.PostdataBean;
import jp.libern.model.UserdataBean;

public class SearchPost {
	public void execute(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		UserdataBean uBean = null;
		ArrayList<PostdataBean> postList = null;

		HttpSession session = request.getSession(false);
		try{
			if(session != null){
				uBean = (UserdataBean) session.getAttribute("uBean");
				if(uBean != null){
					dao = new PostDAO();
					postList = dao.getPostData(uBean);
					if(!postList.isEmpty()){
						request.setAttribute("postList", postList);
						request.setAttribute("userID", uBean.getUserID());
						request.setAttribute("userName", uBean.getUserName());
						request.setAttribute("userImage", uBean.getUserImage());
						request.setAttribute("postNum", postList.size());
					}else{
						request.setAttribute("postNum", 0);
						request.setAttribute("messageOfPost", "Not posted");
					}
				}
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}

	public void executeTimeLine(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		UserdataBean uBean = null;
		HttpSession session = request.getSession(false);
		try{
			if(session != null){
				uBean = (UserdataBean) session.getAttribute("uBean");
				if(uBean != null){
					dao = new PostDAO();
					ArrayList<PostAndUserBean> postDataList = dao.getPostDataList(uBean);
					if(!postDataList.isEmpty()){
						request.setAttribute("postList", postDataList);
						request.setAttribute("userID", uBean.getUserID());
						request.setAttribute("userName", uBean.getUserName());
						request.setAttribute("userImage", uBean.getUserImage());
					}else{
						request.setAttribute("message", "Not posted");
					}
				}
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}

	public void executeSearchPost(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		UserdataBean uBean = null;
		String keyword = request.getParameter("keyword");
		HttpSession session = request.getSession(false);
		try{
			dao = new PostDAO();
			uBean = (UserdataBean) session.getAttribute("uBean");
			ArrayList<PostAndUserBean> postDataList = dao.getPostDataByKeyword(keyword);
			if(!postDataList.isEmpty()){
				request.setAttribute("keyword", keyword);
				request.setAttribute("postDataList", postDataList);
				request.setAttribute("userID", uBean.getUserID());
				request.setAttribute("userName", uBean.getUserName());
				request.setAttribute("userImage", uBean.getUserImage());
				request.setAttribute("loginUser", uBean.getUserID());
				if(!keyword.isEmpty()){
					request.setAttribute("message", "キーワード\""+ keyword + "\"を含む投稿の検索結果");
				}else{
					request.setAttribute("message", "全件検索");
				}
			}else{
				request.setAttribute("message", "No result");
			}
		}finally{
			if(dao != null){
				dao.close();
			}
		}
	}
}
