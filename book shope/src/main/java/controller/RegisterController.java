package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import dto.Customer;
import dto.User;

public class RegisterController {

    public static class RegisterResult {
        private final boolean success;
        private final String message;

        public RegisterResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    public RegisterResult registerCustomer(String email, String password, String accNum,
                                           String name, String address, String phone) {
        try {
            // Create User
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("customer");

            UserDAO userDAO = new UserDAO();
            int userId = userDAO.insertUser(user);

            if (userId != -1) {
                // Create Customer
                Customer customer = new Customer();
                customer.setUserId(userId);
                customer.setAccountNumber(accNum);
                customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);

                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.insertCustomer(customer);

                return new RegisterResult(true, "Registration successful.");
            } else {
                return new RegisterResult(false, "Failed to create user account.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RegisterResult(false, "Error: " + e.getMessage());
        }
    }
}
