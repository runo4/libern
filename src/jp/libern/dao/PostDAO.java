package jp.libern.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import jp.libern.model.PostAndUserBean;
import jp.libern.model.PostdataBean;
import jp.libern.model.UserdataBean;

public class PostDAO {
	private Connection connection;

	//libern_dbへの接続を行うコンストラクタ
	public PostDAO() throws SQLException{
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

	//ユーザーNoからPostを検索するメソッド
	public ArrayList<PostdataBean> getPostData(UserdataBean uBean) throws SQLException{
		PostdataBean pBean = null;
		ArrayList<PostdataBean> postList = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM postdata WHERE user_no = ? ORDER BY id DESC";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, uBean.getUserNo());

			rs = pstatement.executeQuery();
			postList = new ArrayList<PostdataBean>();

			while(rs.next()){
				pBean = new PostdataBean();
				pBean.setId(rs.getInt("id"));
				pBean.setUserNo(rs.getInt("user_no"));
				pBean.setMessage(rs.getString("message"));
				pBean.setPostedDate(rs.getTimestamp("posted_date"));
				postList.add(pBean);
			}
			rs.close();
		}finally {
			pstatement.close();
		}
		return postList;
	}

	//ユーザーNoからフォローしているユーザーと自分のPostを検索するメソッド
	public ArrayList<PostAndUserBean> getPostDataList(UserdataBean uBean) throws SQLException{
		PostAndUserBean puBean = null;
		ArrayList<PostAndUserBean> postDataList = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		try {
			String sql =
					"SELECT p.id, p.user_no, p.message, p.posted_date, u.user_id, u.user_name, u.user_image  "
					+ "FROM postdata p INNER JOIN userdata u ON p.user_no = u.user_no "
					+ "WHERE u.user_no = ? "
					+ "UNION ALL "
					+ "SELECT p.id, p.user_no, p.message, p.posted_date, u.user_id, u.user_name, u.user_image "
					+ "FROM postdata p INNER JOIN userdata u ON p.user_no = u.user_no "
					+ "INNER JOIN following f ON p.user_no = f.following_no "
					+ "WHERE f.user_no = ? "
					+ "ORDER BY id DESC";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, uBean.getUserNo());
			pstatement.setInt(2, uBean.getUserNo());
			rs = pstatement.executeQuery();
			postDataList = new ArrayList<PostAndUserBean>();
			while(rs.next()){
				puBean = new PostAndUserBean();
				puBean.setId(rs.getInt("id"));
				puBean.setUserNo(rs.getInt("user_no"));
				puBean.setMessage(rs.getString("message"));
				puBean.setPostedDate(rs.getTimestamp("posted_date"));
				puBean.setUserID(rs.getString("user_id"));
				puBean.setUserName(rs.getString("user_name"));
				puBean.setUserImage(rs.getString("user_image"));
				postDataList.add(puBean);
			}
			rs.close();
		}finally {
			pstatement.close();
		}
		return postDataList;
	}

	//search欄に入力された文字列からパターンマッチングを行い投稿を抽出するメソッド
	public ArrayList<PostAndUserBean> getPostDataByKeyword(String keyword) throws SQLException{
		PostAndUserBean puBean = null;
		ArrayList<PostAndUserBean> postDataList = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		keyword = "%" + keyword + "%";
		System.out.println(keyword);
		try{
			String sql =
					"SELECT  p.id, p.message, p.posted_date, p.user_no, u.user_id, u.user_name, u.user_image"
					+ " FROM postdata p INNER JOIN userdata u ON p.user_no = u.user_no"
					+ " WHERE message LIKE ? ORDER BY p.id DESC";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setString(1, keyword);

			rs = pstatement.executeQuery();
			postDataList = new ArrayList<PostAndUserBean>();
			while(rs.next()){
				puBean = new PostAndUserBean();
				puBean.setId(rs.getInt("id"));
				puBean.setUserNo(rs.getInt("user_no"));
				puBean.setMessage(rs.getString("message"));
				puBean.setPostedDate(rs.getTimestamp("posted_date"));
				puBean.setUserID(rs.getString("user_id"));
				puBean.setUserName(rs.getString("user_name"));
				puBean.setUserImage(rs.getString("user_image"));
				postDataList.add(puBean);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			pstatement.close();
		}
		return postDataList;
	}

	//投稿を追加するメソッド
	public int insertPostdata(int userNo, String post) throws SQLException{
		int numRow = 0;
		PreparedStatement pstatement = null;
		try{
			connection.setAutoCommit(false);
			String sql = "INSERT INTO postdata (user_no, message, posted_date) VALUES (?, ?, ?)";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, userNo);
			pstatement.setString(2,  post);
			pstatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
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

	//投稿を削除するメソッド
	public int deletePostData(int postId) throws SQLException{
		int numRow = 0;
		PreparedStatement pstatement = null;
		try{
			connection.setAutoCommit(false);
			String sql = "DELETE FROM postdata WHERE id = ?";
			pstatement = connection.prepareStatement(sql);
			//INパラメータの設定
			pstatement.setInt(1, postId);
			//SQLの実行
			numRow = pstatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
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
