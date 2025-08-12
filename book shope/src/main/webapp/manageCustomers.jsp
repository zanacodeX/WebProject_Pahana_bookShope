<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dto.Customer, dao.CustomerDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Customers</title>
<style>
    table {
        width: 90%;
        margin: 20px auto;
        border-collapse: collapse;
    }
    th, td {
        padding: 10px;
        border: 1px solid #ccc;
        text-align: left;
    }
    th {
        background-color: #0074D9;
        color: white;
    }
    a.button {
        background-color: #0074D9;
        color: white;
        padding: 5px 10px;
        text-decoration: none;
        border-radius: 4px;
        font-size: 14px;
    }
    a.button:hover {
        background-color: #005fa3;
    }
</style>
</head>
<body>

<h2 style="text-align:center;">Manage Customers</h2>

<%
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> customers = null;
    try {
        customers = customerDAO.getAllCustomers();
    } catch (Exception e) {
        out.println("<p style='color:red; text-align:center;'>Error loading customers: " + e.getMessage() + "</p>");
    }
%>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Account Number</th>
            <th>Name</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Units Consumed</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <%
        if (customers != null && !customers.isEmpty()) {
            for (Customer c : customers) {
    %>
        <tr>
            <td><%= c.getCustomerId() %></td>
            <td><%= c.getAccountNumber() %></td>
            <td><%= c.getName() %></td>
            <td><%= c.getAddress() %></td>
            <td><%= c.getPhone() %></td>
            <td><%= c.getUnitsConsumed() %></td>
            <td>
                
    <a class="button" href="EditCustomerServlet?id=<%= c.getCustomerId() %>">Edit</a>
    <a class="button" href="CustomerManagementServlet?action=delete&id=<%= c.getCustomerId() %>" onclick="return confirm('Are you sure to delete this customer?');">Delete</a>

            </td>
        </tr>
    <%
            }
        } else {
    %>
        <tr><td colspan="7" style="text-align:center;">No customers found.</td></tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
