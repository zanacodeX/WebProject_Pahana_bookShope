package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import dto.Customer;
import dto.User;

public class LoginController {

    public LoginResult login(String email, String password) throws Exception {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(email, password);

        if (user != null) {
            if ("admin".equalsIgnoreCase(user.getRole())) {
                return new LoginResult(user, null, "adminDashboard.jsp");
            } else {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
                return new LoginResult(user, customer, "customerDashboard");
            }
        }

        return null; // Invalid login
    }

    // Inner DTO to hold login result
    public static class LoginResult {
        private final User user;
        private final Customer customer;
        private final String redirectPage;

        public LoginResult(User user, Customer customer, String redirectPage) {
            this.user = user;
            this.customer = customer;
            this.redirectPage = redirectPage;
        }

        public User getUser() { return user; }
        public Customer getCustomer() { return customer; }
        public String getRedirectPage() { return redirectPage; }
    }
}
