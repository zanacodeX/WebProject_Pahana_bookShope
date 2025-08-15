package controller;

import dao.CustomerDAO;
import dto.Customer;

import java.util.List;

public class CustomerController {

    private final CustomerDAO customerDAO;

    public CustomerController() {
        this.customerDAO = new CustomerDAO();
    }

    // ---------------- Dashboard ----------------
    public static class DashboardResult {
        private final Customer customer;
        private final String message;

        public DashboardResult(Customer customer, String message) {
            this.customer = customer;
            this.message = message;
        }

        public Customer getCustomer() {
            return customer;
        }

        public String getMessage() {
            return message;
        }
    }

    public DashboardResult getCustomerData(int userId) {
        try {
            Customer customer = customerDAO.getCustomerByUserId(userId);
            if (customer != null) {
                return new DashboardResult(customer, "Customer data loaded successfully.");
            } else {
                return new DashboardResult(null, "No customer data found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DashboardResult(null, "Error loading customer data: " + e.getMessage());
        }
    }

    // ---------------- Edit / Update ----------------
    public static class EditResult {
        private final Customer customer;
        private final String message;
        private final boolean success;

        public EditResult(Customer customer, String message, boolean success) {
            this.customer = customer;
            this.message = message;
            this.success = success;
        }

        public Customer getCustomer() {
            return customer;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public EditResult getCustomerById(int customerId) {
        try {
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer != null) {
                return new EditResult(customer, "Customer loaded successfully.", true);
            } else {
                return new EditResult(null, "Customer not found.", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new EditResult(null, "Error loading customer: " + e.getMessage(), false);
        }
    }

    public EditResult updateCustomer(Customer customer) {
        try {
            boolean updated = customerDAO.updateCustomer(customer);
            String message = updated ? "Customer updated successfully." : "Failed to update customer.";
            return new EditResult(customer, message, updated);
        } catch (Exception e) {
            e.printStackTrace();
            return new EditResult(customer, "Error updating customer: " + e.getMessage(), false);
        }
    }

    // ---------------- Management (List/Delete) ----------------
    public static class ManagementResult {
        private final List<Customer> customers;
        private final String message;
        private final boolean success;

        public ManagementResult(List<Customer> customers, String message, boolean success) {
            this.customers = customers;
            this.message = message;
            this.success = success;
        }

        public List<Customer> getCustomers() {
            return customers;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public ManagementResult getAllCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            return new ManagementResult(customers, "Customers loaded successfully.", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ManagementResult(null, "Error loading customers: " + e.getMessage(), false);
        }
    }

    public ManagementResult deleteCustomer(int customerId) {
        try {
            boolean deleted = customerDAO.deleteCustomerAndUser(customerId);
            String message = deleted ? "Customer and user deleted successfully." : "Failed to delete customer and user.";
            return new ManagementResult(null, message, deleted);
        } catch (Exception e) {
            e.printStackTrace();
            return new ManagementResult(null, "Error deleting customer: " + e.getMessage(), false);
        }
    }
}
