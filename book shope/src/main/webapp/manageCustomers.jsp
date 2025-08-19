<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.Customer, dao.CustomerDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Customers</title>
<style>
    body {
        font-family: "Segoe UI", Arial, sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
    }
    header {
        background: #2c3e50;
        color: white;
        padding: 15px;
        text-align: center;
        position: relative;
    }
    header h2 {
        margin: 0;
        font-size: 28px;
    }
    .back-btn {
        position: absolute;
        left: 20px;
        top: 15px;
        background: #3498db;
        color: white;
        border: none;
        padding: 8px 15px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
        transition: 0.3s;
    }
    .back-btn:hover {
        background: #2980b9;
    }
    .container {
        width: 90%;
        max-width: 1100px;
        margin: 30px auto;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
    }
    table, th, td {
        border: 1px solid #ddd;
    }
    th {
        background: #34495e;
        color: white;
    }
    th, td {
        padding: 12px;
        text-align: center;
    }
    a.button {
        background-color: #3498db;
        color: white;
        padding: 6px 12px;
        text-decoration: none;
        border-radius: 5px;
        font-size: 13px;
        transition: 0.3s;
    }
    a.button:hover {
        background-color: #2980b9;
    }
    .error {
        color: red;
        text-align: center;
        margin-top: 15px;
    }
</style>
</head>
<body>

<header>
    <form action="adminDashboard.jsp" method="get" style="display:inline;">
        <button type="submit" class="back-btn">← Back</button>
    </form>
    <h2>Manage Customers</h2>
</header>
<div class="container">

<%
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> customers = null;
    try {
        customers = customerDAO.getAllCustomers();
    } catch (Exception e) {
        out.println("<p style='color:red; text-align:center;'>Error loading customers: " + e.getMessage() + "</p>");
    }
%>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Account Number</th>
            <th>Name</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Units Consumed</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <%
        if (customers != null && !customers.isEmpty()) {
            for (Customer c : customers) {
    %>
        <tr>
            <td><%= c.getCustomerId() %></td>
            <td><%= c.getAccountNumber() %></td>
            <td><%= c.getName() %></td>
            <td><%= c.getAddress() %></td>
            <td><%= c.getPhone() %></td>
            <td><%= c.getUnitsConsumed() %></td>
            <td>
                
    <a class="button" href="EditCustomerServlet?id=<%= c.getCustomerId() %>">Edit</a>
    <a class="button" href="CustomerManagementServlet?action=delete&id=<%= c.getCustomerId() %>" onclick="return confirm('Are you sure to delete this customer?');">Delete</a>

            </td>
        </tr>
    <%
            }
        } else {
    %>
        <tr><td colspan="7" style="text-align:center;">No customers found.</td></tr>
    <%
        }
    %>
    </tbody>
</table>
</div>

</body>
</html>
