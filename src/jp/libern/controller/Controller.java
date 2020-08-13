package jp.libern.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.libern.model.UserdataBean;
import jp.libern.service.Auth;
import jp.libern.service.DeletePost;
import jp.libern.service.IdSearch;
import jp.libern.service.InsertPost;
import jp.libern.service.InsertUser;
import jp.libern.service.SearchFollower;
import jp.libern.service.SearchFollowing;
import jp.libern.service.SearchPost;
import jp.libern.service.Validation;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/form")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String url = "/top.jsp";

		HttpSession session = request.getSession(false);
		//セッションオブジェクトに登録されたstatusの値によってメッセージを切り替え
		try{
			if(session != null) {
				UserdataBean uBean = (UserdataBean)session.getAttribute("uBean");
				String status = (String) session.getAttribute("status");
				if(status != null) {
					if(status.contentEquals("login")){
						SearchPost pSearch = new SearchPost();
						pSearch.executeTimeLine(request);
						request.setAttribute("message", "Welcome to libern !! " + uBean.getUserName());
						request.setAttribute("loginUser", uBean.getUserID());
					}
				}else {
					request.setAttribute("message", "ログインしてください");
				}
			}else {
				request.setAttribute("message", "ログインしてください");
			}
		}catch(Exception e){

		}

		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		String url = "/top.jsp";
		Auth auth;
		UserdataBean uBean;

		HttpSession session = request.getSession(false);

		try {
			if(action != null){
				System.out.println("action value=" + action);
				if(action.equals("loginForm")){
					//top.jspのloginボタン押下時
					url = "/login.jsp";
				}else if(action.equals("registerForm")){
					//top.jspのregisterボタン押下時
					url = "/register.jsp";
				}else if(action.equals("logoutForm")){
					//top.jspのlogoutボタン押下時
					url = "/logout.jsp";
				}else if(action.equals("login")){
					//login.jspのloginボタン押下時
					IdSearch iSearch = new IdSearch();
					auth = new Auth();
					uBean = iSearch.execute(request);
					if(uBean != null){
						if(auth.authUser(request.getParameter("password"), uBean)) {
							//ログイン成功時
							url = "/home.jsp";
							session = request.getSession(true);
							session.setAttribute("status", "login");
							session.setAttribute("uBean", uBean);
							request.setAttribute("message", "welcome!");
						}else{
							url = "/login.jsp";
							request.setAttribute("message", "IDかパスワードが間違っています");
						}
					}else{
						//ログイン失敗時
						url = "/login.jsp";
						request.setAttribute("message", "IDかパスワードが間違っています");
					}
				}else if(action.equals("logout")){
					//logout.jspのlogoutボタン押下時
					url = "/home.jsp";
					session.invalidate();
					request.setAttribute("message", "ログアウトしました。");
				}else if(action.equals("register")){
					//register.jspのregisterボタン押下時
					Validation validation = new Validation();
					IdSearch iSearch = new IdSearch();
					InsertUser insertUser = new InsertUser();

					if(validation.isNotEmptyId(request) && validation.matchPassword(request)){
						if(iSearch.execute(request) == null){
							if(insertUser.execute(request)){
								//アカウント作成成功時
								url = "/home.jsp";
								request.setAttribute("message", "アカウントを作成しました。");
							}else{
								url = "/home.jsp";
								request.setAttribute("message", "<font color=\"red\">アカウントを作成できませんでした。</font>");
							}
						}else{
							url = "/register.jsp";
							request.setAttribute("message", "そのIDは既に使用されています。");
						}
					}else if(!validation.matchPassword(request)){
						url = "/register.jsp";
						request.setAttribute("message", "パスワードが一致しません。");
					}else if(!validation.isNotEmptyId(request)){
						url = "/register.jsp";
						request.setAttribute("message", "IDまたはパスワードを入力する必要があります。");
					}
				}else if(action.equals("post")){
					Validation validation = new Validation();
					InsertPost insertPost = new InsertPost();
					if(validation.isNotEmptyPost(request)){
						if(insertPost.execute(request)){
							url = "/home.jsp";
							request.setAttribute("message", "投稿が完了しました。");
						}else{
							url = "/home.jsp";
							request.setAttribute("message", "<font color=\"red\">投稿に失敗しました。</font>");
						}
					}else{
						request.setAttribute("errorMessage", "<font color=\"red\">未入力で投稿はできません。</font>");
						doGet(request, response);
					}
				}else if(action.equals("postDelete")){
					DeletePost deletePost = new DeletePost();
					if(deletePost.execute(request)){
						doGet(request, response);
					}else{
						request.setAttribute("errorMessage", "<font color=\"red\">投稿の削除に失敗しました。</font>");
						doGet(request, response);
					}
				}else if(action.equals("search")){
					SearchPost pSearch = new SearchPost();
					pSearch.executeSearchPost(request);
					url = "/search.jsp";
				}else if(action.equals("profile")){
					if(session != null){
						SearchPost pSearch = new SearchPost();
						SearchFollowing sfg = new SearchFollowing();
						SearchFollower sfr = new SearchFollower();
						pSearch.execute(request);
						sfg.executeMyFollowing(request);
						sfr.executeMyFollower(request);
						url = "/profile.jsp";
					}else{
						request.setAttribute("errorMessage", "セッションが無効です、再度ログインしてください。");
						url = "/error.jsp";
					}
				}else if(action.equals("setting")){
					if(session != null){
						url = "/setting.jsp";
					}else{
						request.setAttribute("errorMessage", "セッションが無効です、再度ログインしてください。");
						url = "/error.jsp";
					}
				}else if(action.equals("test")){
					url = "/test.jsp";
				}else if(action.equals("myFollowingList")){

				}else if(action.equals("myFollowerList")){

				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
