<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dto.Order" %>
<%@ page import="dao.OrderDAO" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Bookings</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }
        input[type="text"] {
            padding: 8px;
            width: 250px;
        }
        .action-btn {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            cursor: pointer;
        }
        .confirm { background-color: #4CAF50; color: white; }
        .decline { background-color: #f44336; color: white; }
    </style>
</head>
<body>

<h2>Pending Bookings</h2>

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
