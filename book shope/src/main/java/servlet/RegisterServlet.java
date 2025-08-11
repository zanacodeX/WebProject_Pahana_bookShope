package servlet;

import java.io.IOException;

import dao.CustomerDAO;
import dao.UserDAO;
import dto.Customer;
import dto.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String accNum = req.getParameter("accountNumber");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("customer");

            UserDAO userDAO = new UserDAO();
            int userId = userDAO.insertUser(user);

            if (userId != -1) {
                Customer customer = new Customer();
                customer.setUserId(userId);
                customer.setAccountNumber(accNum);
                customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);

                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.insertCustomer(customer);
                res.sendRedirect("login.jsp");
            } else {
                res.sendRedirect("error.jsp");
                res.getWriter().println("Registration failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Console log
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
