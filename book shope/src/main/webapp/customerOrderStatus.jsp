<%@ page import="java.util.List" %>
<%@ page import="dto.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("pendingOrders");
%>

<table border="1">
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
