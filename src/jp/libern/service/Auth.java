package jp.libern.service;

import jp.libern.model.UserdataBean;

public class Auth {
	public boolean authUser(String password, UserdataBean uBean)  throws Exception{
		if(password.equals(uBean.getPassword())){
			return true;
		}else{
			return false;
		}
	}
}
