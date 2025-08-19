<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paid Bills</title>
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
        p {
            margin-top: 15px;
            font-size: 16px;
        }
        p strong {
            color: #2c3e50;
        }
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>

<header>
    <form action="adminDashboard.jsp" method="get" style="display:inline;">
        <button type="submit" class="back-btn">← Back</button>
    </form>
    <h2>Paid Bills</h2>
</header>

<div class="container">
    <section id="bills">
        <p>Billing reports and payment history.</p>

        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Book Name</th>
                    <th>Author</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Order Status</th>
                    <th>Payment Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Order> paidOrders = (List<Order>) request.getAttribute("paidOrders");
                    if (paidOrders != null && !paidOrders.isEmpty()) {
                        for (Order o : paidOrders) {
                %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getBookName() %></td>
                    <td><%= o.getAuthor() %></td>
                    <td>Rs. <%= o.getUnitPrice() %></td>
                    <td><%= o.getQuantity() %></td>
                    <td>Rs. <%= o.getTotal() %></td>
                    <td><%= o.getOrderStatus() %></td>
                    <td><%= o.getPaymentStatus() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="8">No paid orders found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <%
            double grandTotal = 0;
            if (paidOrders != null && !paidOrders.isEmpty()) {
                for (Order o : paidOrders) {
                    grandTotal += o.getTotal();
                }
            }
        %>

        <p><strong>Total Summary:</strong> Rs. <%= String.format("%.2f", grandTotal) %></p>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <p class="error"><%= errorMessage %></p>
        <%
            }
        %>
    </section>
</div>

</body>
</html>
