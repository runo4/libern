<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Your Page</title>
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
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/textbox.js" ></script>
<script src="js/textarea.js" ></script>
<script src="js/button.js" ></script>
<script src="js/menu.js" ></script>
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
			<c:choose>
				<c:when test="${not empty requestScope.postDataList}">
				<div class="resultMessage">
					<p>${requestScope.message }</p>
				</div>
				<div class="postList">
					<c:forEach var="post" items="${requestScope.postDataList }">
					<c:choose>
						<c:when test="${post.userID == requestScope.loginUser }">
							<div class="myPosts">
								<table border="0" summary="posts">
									<tr>
										<td class="nameCell">
											<div class="userIdFont">@<c:out value="${post.userID }" /></div>
											<div class="userNameFont"><c:out value="${post.userName }" /></div>
											<div class="userIcon"><img src="${post.userImage }" width="48px"/></div>
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
						</c:when>
						<c:otherwise>
								<div class="otherPosts">
									<table border="0" summary="posts">
										<tr>
											<td class="nameCell">
												<div class="userIdFont">@<c:out value="${post.userID }" /></div>
												<div class="userNameFont"><c:out value="${post.userName }" /></div>
												<div class="userIcon"><img src="${post.userImage }" width="48px"/></div>
											</td>
											<td class="postCell">
												<div class="postFont">${post.message }</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="actionCell">
												<div id="scale">
													<form action="form" method="post" onSubmit="return ConfirmFavoritePost()">
														<a href="javascript:form.submit()">
															<input type="image" src="img/icon/heart1.png" alt="favorite"/>
															<input type="hidden" name="action" value="postFavorite" />
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
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
				</c:when>
				<c:otherwise>
					<div class="resultMessage">
					<p>${requestScope.message }</p>
				</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>