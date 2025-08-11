<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
<h2>An error occurred!</h2>
    <p style="color:red;">
        <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "Please try again later or contact the system administrator." %>
    </p>
    <a href="register.jsp">Back to Register</a> |
    <a href="login.jsp">Back to Login</a>
</body>
</html>