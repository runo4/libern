package jp.libern.service;

import javax.servlet.http.HttpServletRequest;

public class Validation {
	private String userID;
	private String password;
	private String rePassword;
	private String post;

	public boolean isNotEmptyId(HttpServletRequest request){
		userID = request.getParameter("userID");
		password = request.getParameter("password");
		rePassword = request.getParameter("rePassword");

		if(userID.isEmpty() || userID == null){
			return false;
		}
		if(password.isEmpty() || password == null){
			return false;
		}
		if(rePassword.isEmpty() || rePassword == null){
			return false;
		}
		return true;
	}

	public boolean matchPassword(HttpServletRequest request){
		password = request.getParameter("password");
		rePassword = request.getParameter("rePassword");

		if(password.equals(rePassword)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isNotEmptyPost(HttpServletRequest request){
		post = request.getParameter("post");

		if(!post.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
