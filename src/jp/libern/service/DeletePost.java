package jp.libern.service;

import javax.servlet.http.HttpServletRequest;

import jp.libern.dao.PostDAO;

public class DeletePost {
	public boolean execute(HttpServletRequest request) throws Exception{
		PostDAO dao = null;
		String param = request.getParameter("postId");
		try{
			if(param != null){
				dao = new PostDAO();
				int postId = Integer.parseInt(param);
				int numRow = dao.deletePostData(postId);
				if(numRow > 0){
					return true;
				}else{
					return false;
				}
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
