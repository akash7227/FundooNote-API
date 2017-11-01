<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="springexample"
	content="95803056311-ut199o1k9vjamru4gdvd3agtlk49uor2.apps.googleusercontent.com">
<title>Insert title here</title>
</head>
<body>
	<p>${error}</p>
	<form:form method="post" action="SaveLogin" commandName="login">
		<table>
			<tr>
				<td>Email:</td>
				<td><form:input path="user_email" /></td>

			</tr>
			<tr>
				<td><form:errors path="user_email" /></td>
			</tr>
			<tr>
				<td>password :</td>
				<td><form:input path="user_password" /></td>
			</tr>
			<tr>
				<td><form:errors path="user_password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="login" /></td>
			</tr>
		</table>
	</form:form>
	<a href="signup">New User</a>
	<a href="forgot">forgot password</a>
	<a href="GmailLogin">Gmail</a>  

</body>
</html>