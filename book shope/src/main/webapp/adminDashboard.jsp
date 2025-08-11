<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }
        header {
            background-color: #0074D9;
            color: white;
            padding: 10px;
            text-align: center;
        }
        nav {
            background-color: #f2f2f2;
            padding: 10px;
            display: flex;
            justify-content: center;
        }
        nav a {
            margin: 0 15px;
            text-decoration: none;
            font-weight: bold;
            color: #0074D9;
        }
        nav a:hover {
            text-decoration: underline;
        }
        section {
            padding: 20px;
        }
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
    <a href="AllOrderData">all orders</a>
</nav>

<section id="dashboard">
    <h2>Dashboard</h2>
    <p>Welcome to the admin panel. Choose a tab to manage the system.</p>
</section>

<section id="manageBooks">
    <h2>Manage Books</h2>
    <p>Here you can add, edit or delete books.</p>
    <!-- Book management form/table will go here -->
</section>

<section id="manageBookings">
    <h2>Manage Bookings</h2>
    <p>View and manage customer bookings.</p>
</section>

<section id="bills">
    <h2>Bills</h2>
    <p>Billing reports and payment history.</p>
</section>

<section id="customerManager">
    <h2>Customer Manager</h2>
    <p>View and manage registered customers.</p>
</section>

<section id="allorders">
    <h2>order data</h2>
    <p>View all orders an history.</p>
</section>


</body>
</html>