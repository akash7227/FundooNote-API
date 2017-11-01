<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>Change your password</p>

	<p>${errorMessage}</p>
	<form action="reset" method="post">


		<input type="hidden" name="resetToken" value="${resetToken}">
		<input type="password" name="newPassword"
			placeholder="Enter Your new Password" /> <input type="submit"
			value="Submit" />
	</form>
</body>
</html>















