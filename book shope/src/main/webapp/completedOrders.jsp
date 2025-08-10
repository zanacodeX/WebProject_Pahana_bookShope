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
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #999;
            padding: 8px 12px;
            text-align: center;
        }
        th {
            background-color: #eee;
        }
        .btn-print {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 6px 12px;
            cursor: pointer;
            border-radius: 4px;
        }
        .btn-print:hover {
            background-color: #45a049;
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
    <% } else { %>
        <p>No completed orders found.</p>
    <% } %>
</body>
</html>
