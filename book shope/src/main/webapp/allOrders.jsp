<%@ page import="java.util.List" %>
<%@ page import="dto.Order" %>


<section id="allorders">
    <h2>ALL ORDERS</h2>
    <p>All Orders AND Order history.</p>

    <table border="1" cellpadding="5" cellspacing="0">
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
                List<Order> allOrders = (List<Order>) request.getAttribute("allOrders");
                if (allOrders != null && !allOrders.isEmpty()) {
                    for (Order o : allOrders) {
            %>
            <tr>
                <td><%= o.getId() %></td>
                
                <td><%= o.getBookName() %></td>
                <td><%= o.getAuthor() %></td>
                <td><%= o.getUnitPrice() %></td>
                <td><%= o.getQuantity() %></td>
                <td><%= o.getTotal() %></td>
                <td><%= o.getOrderStatus() %></td>
                <td><%= o.getPaymentStatus() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="10">No paid orders found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>
</section>
