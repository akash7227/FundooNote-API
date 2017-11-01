<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form:form method="post" action="saveUser" modelAttribute="signup">
		<table>
			<tr>
				<td>Name :</td>
				<td><form:input path="user_name" /></td>
			</tr>
			<tr>
				<td><form:errors path="user_name" /></td>
			</tr>
			
			<tr>
				<td>Email:</td>
				<td><form:input path="user_email" /></td>
			</tr>
			<tr>
				<td><form:errors path="user_email" /></td>
			</tr>
			<tr>
				<td>Address :</td>
				<td><form:input path="user_address" /></td>
			</tr>
				<tr>
				<td><form:errors path="user_address" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><form:input type="password" path="user_password" /></td>
			</tr>
				<tr>
				<td><form:errors path="user_password" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>