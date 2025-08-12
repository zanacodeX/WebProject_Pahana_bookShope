package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    
 // Get all customers
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setAccountNumber(rs.getString("account_number"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                c.setUnitsConsumed(rs.getInt("units_consumed"));
                customers.add(c);
            }
        }
        return customers;
    }
    
    //update custom new
    
    public boolean updateCustomer(Customer customer) throws Exception {
        String sql = "UPDATE customers SET account_number=?, name=?, address=?, phone=?, units_consumed=? WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setInt(5, customer.getUnitsConsumed());
            stmt.setInt(6, customer.getCustomerId());
            return stmt.executeUpdate() > 0;
        }
    }
    
    
    //update part one
    
    public Customer getCustomerById(int id) throws Exception {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customer.setUnitsConsumed(rs.getInt("units_consumed"));
            }
        }
        return customer;
    }
    

    // Update a customer
    public boolean updateCustomers(Customer customer) throws Exception {
        String sql = "UPDATE customers SET account_number = ?, name = ?, address = ?, phone = ?, units_consumed = ? WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getAccountNumber());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getUnitsConsumed());
            ps.setInt(6, customer.getCustomerId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
    }

   

    //new delete function
    
    public boolean deleteCustomerAndUser(int customerId) throws Exception {
        String getUserIdSQL = "SELECT user_id FROM customers WHERE customer_id = ?";
        String deleteCustomerSQL = "DELETE FROM customers WHERE customer_id = ?";
        String deleteUserSQL = "DELETE FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);  // start transaction

            int userId;

            // 1) Get user_id related to customer
            try (PreparedStatement ps = con.prepareStatement(getUserIdSQL)) {
                ps.setInt(1, customerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    } else {
                        con.rollback();
                        throw new Exception("No customer found with ID " + customerId);
                    }
                }
            }

            // 2) Delete customer
            try (PreparedStatement ps = con.prepareStatement(deleteCustomerSQL)) {
                ps.setInt(1, customerId);
                ps.executeUpdate();
            }

            // 3) Delete user
            try (PreparedStatement ps = con.prepareStatement(deleteUserSQL)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
