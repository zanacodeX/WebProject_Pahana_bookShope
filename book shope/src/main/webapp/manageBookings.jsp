<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dto.Order" %>
<%@ page import="dao.OrderDAO" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Orders</title>
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
        form {
            margin-bottom: 20px;
        }
        input[type="text"] {
            padding: 10px;
            width: 250px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 14px;
        }
        input[type="submit"] {
            padding: 10px 15px;
            border-radius: 5px;
            border: none;
            background: #27ae60;
            color: white;
            cursor: pointer;
            font-size: 14px;
            transition: 0.3s;
        }
        input[type="submit"]:hover {
            background: #219150;
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
        .action-btn {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            font-size: 13px;
        }
        .confirm { background-color: #27ae60; color: white; }
        .decline { background-color: #e74c3c; color: white; }
        .confirm:hover { background-color: #219150; }
        .decline:hover { background-color: #c0392b; }
    </style>
</head>
<body>

<header>
    <form action="adminDashboard.jsp" method="get" style="display:inline;">
        <button type="submit" class="back-btn">← Back</button>
    </form>
    <h2>Manage Orders</h2>
</header>

<form method="get" action="manageBookings.jsp">
    <input type="text" name="search" placeholder="Search by customer name" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>"/>
    <input type="submit" value="Search"/>
</form>

<%
    String searchQuery = request.getParameter("search");
    OrderDAO orderDAO = new OrderDAO();
    List<Order> pendingOrders;

    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        pendingOrders = orderDAO.searchPendingOrdersByCustomerName(searchQuery.trim());
    } else {
        pendingOrders = orderDAO.getPendingOrders();
    }

    if (pendingOrders.isEmpty()) {
%>
        <p>No pending orders found.</p>
<%
    } else {
%>
<table>
    <tr>
        <th>Order ID</th>
        <th>Book Name</th>
        <th>Quantity</th>
        <th>Customer Name</th>
        <th>Unit Price</th>
        <th>Total</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
<%
    for (Order order : pendingOrders) {
%>
    <tr>
        <td><%= order.getOrderId() %></td>
        <td><%= order.getBookName() %></td>
        <td><%= order.getQuantity() %></td>
        <td><%= order.getCustomerName() %></td>
        <td><%= order.getUnitPrice() %></td>
        <td><%= order.getTotal() %></td>
        
        <td><%= order.getOrderStatus() %></td>
        <td>
            <form method="post" action="UpdateOrderStatusServlet">
                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>"/>
                <button type="submit" name="action" value="confirm" class="action-btn confirm">Confirm</button>
                <button type="submit" name="action" value="decline" class="action-btn decline">Decline</button>
            </form>
        </td>
    </tr>
<%
    }
%>
</table>
<% } %>

</body>
</html>
