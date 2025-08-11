package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Order;
import model.DBConnection;

public class OrderDAO {

    // Place an order
    public void placeOrder(Order order) throws Exception {
        String sql = "INSERT INTO orders (customer_id, book_id, book_name, author, unit_price, quantity, total, order_status, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getBookId());
            stmt.setString(3, order.getBookName());
            stmt.setString(4, order.getAuthor());
            stmt.setDouble(5, order.getUnitPrice());
            stmt.setInt(6, order.getQuantity());
            stmt.setDouble(7, order.getTotal());
            stmt.setString(8, "Pending");
            stmt.setString(9, "Pending");

            stmt.executeUpdate();
        }
    }
    
    //compketed
    
    public List<Order> getCompletedOrdersByCustomerId(int customerId) throws Exception {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ? AND payment_status = 'Paid'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBookId(rs.getInt("book_id"));
                order.setBookName(rs.getString("book_name"));   // you had it commented in earlier code
                order.setAuthor(rs.getString("author"));
                order.setUnitPrice(rs.getDouble("unit_price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getDouble("total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getString("payment_status"));
                orders.add(order);
            }
        }
        return orders;
    }
    
    //payment history
    
    public List<Order> getAllPaidOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE payment_status = 'Paid'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBookId(rs.getInt("book_id"));
                order.setBookName(rs.getString("book_name"));
                order.setAuthor(rs.getString("author"));
                order.setUnitPrice(rs.getDouble("unit_price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getDouble("total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getString("payment_status"));
                // No customer name/email because we are not joining other tables
                orders.add(order);
            }
        }
        return orders;
    }
    
    
    //aa order details
    
    public List<Order> getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBookId(rs.getInt("book_id"));
                order.setBookName(rs.getString("book_name"));
                order.setAuthor(rs.getString("author"));
                order.setUnitPrice(rs.getDouble("unit_price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getDouble("total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getString("payment_status"));
                // No customer name/email because we are not joining other tables
                orders.add(order);
            }
        }
        return orders;
    }
    
    // Update order status (Confirm or Decline)
    public void updateOrderStatus(int orderId, String newStatus) throws Exception {
        String sql = "UPDATE orders SET order_status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    // Get all pending orders with customer name
    public List<Order> getPendingOrders() throws Exception {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, " +
                "       c.name AS customer_name, " +
                "       u.email AS email " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN users u ON c.user_id = u.user_id " + // join users table
                "WHERE o.order_status = 'Pending'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("id"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setBookId(rs.getInt("book_id"));
                o.setBookName(rs.getString("book_name"));
                o.setAuthor(rs.getString("author"));
                o.setQuantity(rs.getInt("quantity"));
                o.setUnitPrice(rs.getDouble("unit_price"));
                o.setTotal(rs.getDouble("total"));
                o.setOrderStatus(rs.getString("order_status"));
                o.setPaymentStatus(rs.getString("payment_status"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setCustomerEmail(rs.getString("email"));
                list.add(o);
            }
        }
        return list;
    }
    
    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id=?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBookId(rs.getInt("book_id"));
                order.setBookName(rs.getString("book_name"));
                order.setAuthor(rs.getString("author"));
                order.setUnitPrice(rs.getDouble("unit_price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getDouble("total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getString("payment_status"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    //customer dash pyment pendinga status get
    public List<Order> getPendingOrdersByCustomerId(int customerId) throws Exception {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ?  AND payment_status = 'Pending'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBookId(rs.getInt("book_id"));
                order.setBookName(rs.getString("book_name"));
                order.setAuthor(rs.getString("author"));
                order.setUnitPrice(rs.getDouble("unit_price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getDouble("total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getString("payment_status"));
                //order.setCreatedAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }
        }

        return orders;
    }
    
    //get customer completed order
    
   

    //payed update
    
    public void updatePaymentStatusToPaid(int orderId) throws Exception {
        String sql = "UPDATE orders SET payment_status = 'Paid' WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }
    
    
    
    
    public void updatePaymentStatus(int orderId, String status) {
        String sql = "UPDATE orders SET payment_status=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
 // Get order details by ID (for bill printing)
    public Order getOrderById(int orderId) throws Exception {
        Order order = null;
        String sql = "SELECT o.*, c.name AS customer_name, u.email AS email " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN users u ON c.user_id = u.user_id " +
                "WHERE o.id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setBookId(rs.getInt("book_id"));
                    order.setBookName(rs.getString("book_name"));
                    order.setAuthor(rs.getString("author"));
                    order.setUnitPrice(rs.getDouble("unit_price"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotal(rs.getDouble("total"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setCustomerEmail(rs.getString("email"));
                   // order.setCustomerAddress(rs.getString("address"));
                }
            }
        }
        return order;
    }
    
    // Search pending orders by customer name (case-insensitive)
    public List<Order> searchPendingOrdersByCustomerName(String name) throws Exception {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, c.name AS customer_name " +
                     "FROM orders o " +
                     "JOIN customers c ON o.customer_id = c.customer_id " +
                     "WHERE o.order_status = 'Pending' AND LOWER(c.name) LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setOrderId(rs.getInt("id"));
                    o.setCustomerId(rs.getInt("customer_id"));
                    o.setBookId(rs.getInt("book_id"));
                    o.setBookName(rs.getString("book_name"));
                    o.setAuthor(rs.getString("author"));
                    o.setQuantity(rs.getInt("quantity"));
                    o.setUnitPrice(rs.getDouble("unit_price"));
                    o.setTotal(rs.getDouble("total"));
                    o.setOrderStatus(rs.getString("order_status"));
                   // o.setPaymentStatus(rs.getString("payment_status"));
                    o.setCustomerName(rs.getString("customer_name"));
                    list.add(o);
                }
            }
        }
        return list;
    }
}
