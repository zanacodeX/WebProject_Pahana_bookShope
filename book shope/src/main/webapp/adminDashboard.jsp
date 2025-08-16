<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.AdminSummary, dao.AdminDashboardDAO" %>
<%
    // Create DAO and fetch summary
    AdminDashboardDAO dashboardDAO = new AdminDashboardDAO();
    AdminSummary summary = null;
    try {
        summary = dashboardDAO.getDashboardSummary();
    } catch(Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<style>
    body { margin: 0; font-family: Arial, sans-serif; }
    header { background-color: #0074D9; color: white; padding: 10px; text-align: center; }
    nav { background-color: #f2f2f2; padding: 10px; display: flex; justify-content: center; }
    nav a { margin: 0 15px; text-decoration: none; font-weight: bold; color: #0074D9; }
    nav a:hover { text-decoration: underline; }
    section { padding: 20px; }
    .summary-container { display: flex; flex-wrap: wrap; gap: 20px; }
    .summary-card { border: 1px solid #ccc; padding: 15px; width: 200px; border-radius: 8px; box-shadow: 2px 2px 5px #aaa; }
</style>
</head>
<body>

<header>
    <h1>Welcome Admin</h1>
    <p><a href="log.jsp" style="color: white; text-decoration: underline;">Logout</a></p>
</header>

<nav>
    <a href="#dashboard">Dashboard</a>
    <a href="manageBooks.jsp">Manage Books</a>
    <a href="manageBookings.jsp">Manage Bookings</a>
    <a href="BillServlet">Bills</a>
    <a href="manageCustomers.jsp">Customer Manager</a>
    <a href="AllOrderData">All Orders</a>
</nav>

<section id="dashboard">
    <h2>Dashboard</h2>
    <p>Welcome to the admin panel. Choose a tab to manage the system.</p>

    <!-- Summary Section -->
    <div class="summary-container">
        <div class="summary-card">
            <h3>Total Earnings (Last 7 Days)</h3>
            <p>$<%= (summary != null ? summary.getTotalEarnings() : 0) %></p>
        </div>
        <div class="summary-card">
            <h3>Pending Orders</h3>
            <p><%= (summary != null ? summary.getPendingOrders() : 0) %></p>
        </div>
        <div class="summary-card">
            <h3>Completed Orders</h3>
            <p><%= (summary != null ? summary.getCompletedOrders() : 0) %></p>
        </div>
        <div class="summary-card">
            <h3>Declined Orders</h3>
            <p><%= (summary != null ? summary.getDeclinedOrders() : 0) %></p>
        </div>
        <div class="summary-card">
            <h3>Confirmed Orders</h3>
            <p><%= (summary != null ? summary.getConfirmedOrders() : 0) %></p>
        </div>
        <div class="summary-card">
            <h3>Total Customers</h3>
            <p><%= (summary != null ? summary.getTotalCustomers() : 0) %></p>
        </div>
    </div>
</section>

</body>
</html>
