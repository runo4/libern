package jp.libern.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostdataBean implements Serializable {
	private int id;
	private int userNo;
	private String message;
	private Timestamp postedDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Timestamp postedDate) {
		this.postedDate = postedDate;
	}
}
