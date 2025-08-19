<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("pendingOrders");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Status</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
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

        button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1976D2;
        }

        .back-btn {
            display: block;
            margin: 0 auto;
            background-color: #ff5722;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 6px;
        }

        .back-btn:hover {
            background-color: #e64a19;
        }
    </style>
</head>
<body>
    <h1>Order Status</h1>

    <table>
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

        <%
            if (orders != null) {
                for (Order order : orders) {
        %>
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
                <%
                 if (!"declined".equalsIgnoreCase(order.getOrderStatus()) && !"pending".equalsIgnoreCase(order.getOrderStatus())) {
                %>
                    <form action="payOrder" method="post">
                        <input type="hidden" name="orderId" value="<%= order.getId() %>" />
                        <button type="submit">Pay</button>
                    </form>
                <%
                    } else {
                        out.print("Not Available");
                    }
                %>
            </td>
        </tr>
        <%
                }
            } else {
                out.println("<tr><td colspan='9'>No orders found.</td></tr>");
            }
        %>
    </table>

    <div style="text-align: center;">
        <a href="customerDashboard.jsp">
            <button class="back-btn">Back to Dashboard</button>
        </a>
    </div>
</body>
</html>
