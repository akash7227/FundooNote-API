<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
		session.getAttribute("found");
		System.out.println("session is " + session.getAttribute("found"));
		if (session.getAttribute("found") == null) {
			System.out.println("inside null");
	%>
	<jsp:include page="login.jsp" />
	<%
		} else {
			System.out.println("not null");
	%>
	<p>Welcome ${found.user_name}</p>
	<a href="logout">Logout</a>
	<%
		}
	%>
</body>
</html>