package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Customer;
import model.DBConnection;

public class CustomerDAO {
    public void insertCustomer(Customer customer) throws Exception {
        String sql = "INSERT INTO customers (user_id, account_number, name, address, phone, units_consumed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getAccountNumber());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, customer.getUnitsConsumed());
            ps.executeUpdate();
        }
    }
    
    //get custom id by u id
    
    //public int getCustomerIdByUserId(int userId) throws Exception {
      //  int customerId = -1;
        //String sql = "SELECT customer_id FROM customers WHERE user_id = ?";

        //try (Connection con = DBConnection.getConnection();
          //   PreparedStatement ps = con.prepareStatement(sql)) {
            //ps.setInt(1, userId);
            //ResultSet rs = ps.executeQuery();

            //if (rs.next()) {
            //    customerId = rs.getInt("customer_id");
            //}
      //  }
  //      return customerId;
   // }
    
    public int getCustomerIdByUserId(int userId) throws Exception {
        String sql = "SELECT customer_id FROM customers WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id");
            }
        }
        throw new Exception("Customer not found for user_id: " + userId);
    }

    public Customer getCustomerByUserId(int userId) throws Exception {
        String sql = "SELECT * FROM customers WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setAccountNumber(rs.getString("account_number"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                //c.setUnitsConsumed(rs.getInt("units_consumed"));
                System.out.println("Customer found for user ID " + userId + ": " + c.getName ()+ "cutom ID:"+c.getCustomerId() + "acc nu :"+ c.getAccountNumber()
                		+ "address ; " +c.getAddress ()+ "ogone ;"+ c.getPhone ());
                return c;
                
            }
            return null;
            

        }
    }
}
