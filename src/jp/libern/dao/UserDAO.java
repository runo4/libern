package jp.libern.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import jp.libern.model.UserdataBean;

public class UserDAO {
	private Connection connection;

	//libern_dbへの接続を行うコンストラクタ
	public UserDAO() throws SQLException{
		String url = "jdbc:mysql://localhost:3306/libern_db";
		String user = "root";
		String password = "root";
		connection = DriverManager.getConnection(url, user, password);
	}

	//libern_dbとの接続を切断するメソッド
	public void close() {
		try {
			if(connection != null) {
				connection.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//IDからユーザーを検索するメソッド
	public UserdataBean getUserData(String userID) throws SQLException{
		UserdataBean uBean = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM userdata WHERE user_id = ?";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setString(1, userID);

			rs = pstatement.executeQuery();
			if(rs.next()) {
				uBean = new UserdataBean();
				uBean.setUserNo(rs.getInt("user_no"));
				uBean.setUserID(rs.getString("user_id"));
				uBean.setPassword(rs.getString("password"));
				uBean.setUserName(rs.getString("user_name"));
				uBean.setUserImage(rs.getString("user_image"));
				uBean.setUserProfile(rs.getString("user_profile"));
				uBean.setRegisterDate(rs.getTimestamp("register_date"));
				uBean.setValue(rs.getBoolean("value"));
			}
			rs.close();
		}finally {
			pstatement.close();
		}
		return uBean;
	}

	//該当するユーザーNoのフォローしているユーザーリストを取得するメソッド
	public ArrayList<UserdataBean> getFollowingList(int userNo) throws SQLException{
		UserdataBean uBean = null;
		ArrayList<UserdataBean> followingList = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT u.user_no, u.user_id, u.user_name, u.user_image, u.user_profile "
					+ "FROM userdata u INNER JOIN following f ON u.user_no = f.following_no "
					+ "WHERE f.user_no = ?";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, userNo);
			rs = pstatement.executeQuery();
			followingList = new ArrayList<UserdataBean>();
			while(rs.next()){
				uBean = new UserdataBean();
				uBean.setUserNo(rs.getInt("user_no"));
				uBean.setUserID(rs.getString("user_id"));
				uBean.setUserName(rs.getString("user_name"));
				uBean.setUserImage(rs.getString("user_image"));
				uBean.setUserProfile(rs.getString("user_profile"));
				followingList.add(uBean);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			pstatement.close();
		}
		return followingList;
	}

	//該当するユーザーNoのフォローしているユーザーリストを取得するメソッド
		public ArrayList<UserdataBean> getFollowerList(int userNo) throws SQLException{
			UserdataBean uBean = null;
			ArrayList<UserdataBean> followerList = null;
			PreparedStatement pstatement = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT u.user_no, u.user_id, u.user_name, u.user_image, u.user_profile "
						+ "FROM userdata u INNER JOIN following f ON u.user_no = f.user_no "
						+ "WHERE f.following_no = ?";
				pstatement = connection.prepareStatement(sql);
				//INパラメータの設定
				pstatement.setInt(1, userNo);
				rs = pstatement.executeQuery();
				followerList = new ArrayList<UserdataBean>();
				while(rs.next()){
					uBean = new UserdataBean();
					uBean.setUserNo(rs.getInt("user_no"));
					uBean.setUserID(rs.getString("user_id"));
					uBean.setUserName(rs.getString("user_name"));
					uBean.setUserImage(rs.getString("user_image"));
					uBean.setUserProfile(rs.getString("user_profile"));
					followerList.add(uBean);
				}
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				pstatement.close();
			}
			return followerList;
		}

	//ユーザー情報を登録するメソッド
	public int insertUserdata(String userID, String password) throws SQLException{
		int numRow = 0;
		PreparedStatement pstatement = null;
		try{
			connection.setAutoCommit(false);
			String sql = "INSERT INTO userdata (user_id, password, register_date, value) VALUES (?, ?, ?, ?)";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setString(1, userID);
			pstatement.setString(2,  password);
			pstatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstatement.setBoolean(4, false);
			//SQLの実行
			numRow = pstatement.executeUpdate();
		}finally{
			if(numRow > 0){
				connection.commit();
			}else{
				connection.rollback();
			}
			pstatement.close();
		}
		return numRow;
	}

	public int insertMyFollowing(int myUserNo, int userNo) throws SQLException{
		int numRow = 0;
		PreparedStatement pstatement = null;
		try{
			connection.setAutoCommit(false);
			String sql = "INSERT INTO following (user_no, following_no) VALUES (?, ?)";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, myUserNo);
			pstatement.setInt(2,  userNo);
			//SQLの実行
			numRow = pstatement.executeUpdate();
		}finally{
			if(numRow > 0){
				connection.commit();
			}else{
				connection.rollback();
			}
			pstatement.close();
		}
		return numRow;
	}
}
