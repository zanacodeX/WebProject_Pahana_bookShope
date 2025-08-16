package dao;

import dto.AdminSummary;
import model.DBConnection;

import java.sql.*;

public class AdminDashboardDAO {

    public AdminSummary getDashboardSummary() throws Exception {
        AdminSummary summary = new AdminSummary();
        String sql = "SELECT " +
                     "(SELECT IFNULL(SUM(total),0) FROM orders WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)AND payment_status = 'Paid') AS totalEarnings, " +
                     "(SELECT COUNT(*) FROM orders WHERE order_status='Pending') AS pendingOrders, " +
                     "(SELECT COUNT(*) FROM orders WHERE order_status='accepted') AS confirmedOrders, " +
                     "(SELECT COUNT(*) FROM orders WHERE payment_status='Paid') AS completedOrders, " +
                     "(SELECT COUNT(*) FROM orders WHERE order_status='Declined') AS declinedOrders, " +
                     "(SELECT COUNT(*) FROM customers) AS totalCustomers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                summary.setTotalEarnings(rs.getDouble("totalEarnings"));
                summary.setPendingOrders(rs.getInt("pendingOrders"));
                summary.setConfirmedOrders(rs.getInt("confirmedOrders"));
                summary.setCompletedOrders(rs.getInt("completedOrders"));
                summary.setDeclinedOrders(rs.getInt("declinedOrders"));
                summary.setTotalCustomers(rs.getInt("totalCustomers"));
            }
        }

        return summary;
    }
}
