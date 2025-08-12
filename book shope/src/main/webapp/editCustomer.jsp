<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Customer" %>
<%
    Customer customer = (Customer) request.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("manageCustomers.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Customer</title>
<style>
    form {
        width: 400px;
        margin: auto;
        padding: 20px;
        border: 1px solid #ccc;
        background-color: #f9f9f9;
        margin-top: 50px;
    }
    label {
        display: block;
        margin-top: 10px;
    }
    input[type="text"], input[type="number"] {
        width: 100%;
        padding: 8px;
        margin-top: 5px;
    }
    input[type="submit"] {
        margin-top: 15px;
        background-color: #0074D9;
        color: white;
        border: none;
        padding: 10px;
        cursor: pointer;
    }
    input[type="submit"]:hover {
        background-color: #005fa3;
    }
</style>
</head>
<body>

<h2 style="text-align:center;">Edit Customer</h2>

<form action="EditCustomerServlet" method="post">
    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">

    <label>Account Number:</label>
    <input type="text" name="accountNumber" value="<%= customer.getAccountNumber() %>" required>

    <label>Name:</label>
    <input type="text" name="name" value="<%= customer.getName() %>" required>

    <label>Address:</label>
    <input type="text" name="address" value="<%= customer.getAddress() %>" required>

    <label>Phone:</label>
    <input type="text" name="phone" value="<%= customer.getPhone() %>" required>

    <label>Units Consumed:</label>
    <input type="number" name="unitsConsumed" value="<%= customer.getUnitsConsumed() %>" required>

    <input type="submit" value="Update">
    <a href="manageCustomers.jsp" class="button">Cancel</a>
</form>

</body>
</html>