<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Profile Page</title>
<link rel="stylesheet" type="text/css" href="css/background.css" />
<link rel="stylesheet" type="text/css" href="css/textbox.css" />
<link rel="stylesheet" type="text/css" href="css/textarea.css" />
<link rel="stylesheet" type="text/css" href="css/hr.css" />
<link rel="stylesheet" type="text/css" href="css/font.css" />
<link rel="stylesheet" type="text/css" href="css/table.css" />
<link rel="stylesheet" type="text/css" href="css/pictogram.css" />
<link rel="stylesheet" type="text/css" href="css/menu.css" />
<link rel="stylesheet" type="text/css" href="css/image.css" />
<link rel="stylesheet" type="text/css" href="css/button.css" />
<link rel="stylesheet" type="text/css" href="css/modalwindow.css" />
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/textbox.js" ></script>
<script src="js/textarea.js" ></script>
<script src="js/button.js" ></script>
<script src="js/menu.js" ></script>
<script src="js/modalwindow.js" ></script>
</head>
<body>
	<div class="container">
		<c:if test="${not empty sessionScope.status}">
			<div class="tools">
				<div id="menu">
					<div class="hamberger">
						<span></span>
						<span></span>
						<span></span>
					</div>

					<ul class="menu-list">
						<!-- Content 01 -->
						<li class="menu-contents">
							<form action="form" method="post" name="profile">
								<a href="javascript:profile.submit()">Profile</a>
								<input type="hidden" name="action" value="profile" />
							</form>
						</li>
						<!-- Content 02 -->
						<li class="menu-contents">
							<form action="form" method="post" name="logoutForm">
								<a href="javascript:logoutForm.submit()">Logout</a>
								<input type="hidden" name="action" value="logoutForm" />
							</form>
						</li>
						<!-- Content 03 -->
						<li class="menu-contents">
							<form action="form" method="post" name="setting">
								<a href="javascript:setting.submit()">Setting</a>
								<input type="hidden" name="action" value="setting" />
							</form>
						</li>
						<!-- Content 04 -->
						<li class="menu-contents">
							<form action="form" method="post" name="test">
								<a href="javascript:test.submit()">Test Page</a>
								<input type="hidden" name="action" value="test" />
							</form>
						</li>
					</ul>
				</div>
				<div class="search">
					<form action="form" method="post">
						<input type="text" name="keyword" value=""/>
						<input type="submit" value="Search" class="button2"/>
						<input type="hidden" name="action" value="search" />
					</form>
				</div>
			</div>
		</c:if>
		<div class="title" >
			<div class="title-image">
				<a href="form"><img src="img/libern_title.png" alt="libern" /></a>
			</div>
			<div class="describe">
				My first SNS-like site.
			</div>
		</div>
		<div class="main">
			<div class="profile">
				<div class="userProfileIcon"><img src="${sessionScope.uBean.userImage }" alt="icon" /></div>
				<div class="userIdProfile">@<c:out value="${sessionScope.uBean.userID }" /></div>
				<div class="userNameProfile"><c:out value="${sessionScope.uBean.userName }" /></div>
				<div class="userProfileFont"><c:out value="${sessionScope.uBean.userProfile }" /></div>
				<div class="followfollower">
					<table border="0">
						<tr>
							<td><div class="ffFont">Post</div></td>
							<td><div class="ffFont">Following</div></td>
							<td><div class="ffFont">Follower</div></td>
						</tr>
						<tr>
							<td>
								<div class="ffNumberFont">
									<p>${requestScope.postNum }</p>
								</div>
							</td>
							<td>
								<div class="userFollowing ffNumberFont">
									<p><a class="modal-syncer button-link" data-target="modal-content-01">${requestScope.followingNum }</a></p>
								</div>
							</td>
							<td>
								<div class="userFollower ffNumberFont">
									<p><a class="modal-syncer button-link" data-target="modal-content-02">${requestScope.followerNum }</a></p>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="modal-content-01" class="modal-content">
				<div class="scrollable">
					<div class="closeButton"><a id="modal-close" class="button-link">×</a></div>
					<div class="followfollowerLabel">フォロー一覧</div>
					<div class="postList">
						<c:forEach var="following" items="${requestScope.followingList }">
						<div class="myPosts">
						<table border="0" summary="following">
							<tr>
								<td class="nameCell">
									<div class="userIdFont">@<c:out value="${following.userID }" /></div>
									<div class="userNameFont"><c:out value="${following.userName }" /></div>
									<div class="userIcon"><img src="${following.userImage }" width="48px"/></div>
								</td>
								<td class="postCell">
									<div class="postFont">${following.userProfile }</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="actionCell">
									<div id="scale">

									</div>
								</td>
							</tr>
						</table>
						</div>
						</c:forEach>
						<c:if test="${empty requestScope.followingList  }">
							<div class="resultMessage">フォローしているユーザーはいません</div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="modal-content-02" class="modal-content">
				<div class="scrollable">
					<div class="closeButton"><a id="modal-close" class="button-link">×</a></div>
					<div class="followfollowerLabel">フォロワー一覧</div>
					<div class="postList">
						<c:forEach var="follower" items="${requestScope.followerList }">
						<div class="myPosts">
						<table border="0" summary="follower">
							<tr>
								<td class="nameCell">
									<div class="userIdFont">@<c:out value="${follower.userID }" /></div>
									<div class="userNameFont"><c:out value="${follower.userName }" /></div>
									<div class="userIcon"><img src="${follower.userImage }" width="48px"/></div>
								</td>
								<td class="postCell">
									<div class="postFont">${follower.userProfile }</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="actionCell">
									<div id="scale">

									</div>
								</td>
							</tr>
						</table>
						</div>
						</c:forEach>
						<c:if test="${empty requestScope.followerList  }">
							<div class="resultMessage">フォローされているユーザーはいません</div>
						</c:if>
					</div>
				</div>
			</div>
			<hr />
			<c:if test="${not empty sessionScope.status}">
				<div class="postList">
					<c:forEach var="post" items="${requestScope.postList }">
					<div class="myPosts">
					<table border="0" summary="posts">
						<tr>
							<td class="nameCell">
								<div class="userIdFont">@<c:out value="${requestScope.userID }" /></div>
								<div class="userNameFont"><c:out value="${requestScope.userName }" /></div>
								<div class="userIcon"><img src="${requestScope.userImage }" width="48px"/></div>
							</td>
							<td class="postCell">
								<div class="postFont">${post.message }</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="actionCell">
								<div id="scale">
									<form action="form" method="post" onSubmit="return ConfirmDeletePost()">
										<a href="javascript:form.submit()">
											<input type="image" src="img/icon/trush.png" alt="delete"/>
											<input type="hidden" name="action" value="postDelete" />
											<input type="hidden" name="postId" value="${post.id }" />
										</a>
									</form>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="footerCell">
								<div class="postDateFont"><c:out value="${post.postedDate }" /></div>
							</td>
						</tr>
					</table>
					</div>
					</c:forEach>
					<c:if test="${empty requestScope.postList  }">
						<div class="resultMessage">${requestScope.messageOfPost }</div>
					</c:if>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>