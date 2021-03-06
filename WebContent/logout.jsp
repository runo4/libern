<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Logout Page</title>
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
		<div class="header" >
			<div class="title-image">
				<a href="form"><img src="img/libern_title.png" alt="libern" /></a>
			</div>
			<div class="describe">
				My first SNS-like site.
			</div>
		</div>
		<div class="main">
			<p>ログアウトしますか？</p>
			<form action="form" method="post">
				<p><input type="submit" value="Logout" class="button" /></p>
				<input type="hidden" name="action" value="logout" />
			</form>
		</div>
	</div>
</body>
</html>