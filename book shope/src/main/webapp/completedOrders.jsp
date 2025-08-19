<%@ page import="java.util.List" %>
<%@ page import="dto.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("completedOrders");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Completed Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        table {
            width: 90%;
            margin: 0 auto 20px auto;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .btn-print {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn-print:hover {
            background-color: #1976D2;
        }

        .back-button {
            text-align: center;
            margin-top: 20px;
        }

        .back-button button {
            background-color: #ff5722;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.3s;
        }

        .back-button button:hover {
            background-color: #e64a19;
        }
    </style>
</head>
<body>
    <h2>Completed Orders</h2>

    <% if (orders != null && !orders.isEmpty()) { %>
    <table>
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Book Name</th>
                <th>Author</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Order Status</th>
                <th>Payment Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <% for (Order order : orders) { %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getBookName() %></td>
                <td><%= order.getAuthor() %></td>
                <td><%= order.getUnitPrice() %></td>
                <td><%= order.getQuantity() %></td>
                <td><%= order.getTotal() %></td>
                <td><%= order.getOrderStatus() %></td>
                <td><%= order.getPaymentStatus() %></td>
                <td>
                    <form action="GenerateBillServlet" method="post" style="margin:0;">
                        <input type="hidden" name="orderId" value="<%= order.getId() %>" />
                        <button type="submit" class="btn-print">Print Bill</button>
                    </form>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <div class="back-button">
        <a href="customerDashboard.jsp">
            <button type="button">Back to Dashboard</button>
        </a>
    </div>

    <% } else { %>
        <p style="text-align:center; color:red;">No completed orders found.</p>
    <% } %>
</body>
</html>
