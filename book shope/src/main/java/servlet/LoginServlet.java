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
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.validateUser(email, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
               
                
                System.out.println("User ID from session: " + user.getUserId());
                System.out.println("Login role: " + user.getRole());

                if ("admin".equalsIgnoreCase(user.getRole())) {
                    System.out.println("Redirecting to adminDashboard.jsp");
                    res.sendRedirect("adminDashboard.jsp");
                } else {
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
                    session.setAttribute("customer", customer);
                    res.sendRedirect("customerDashboard");
                }
            } else {
                req.setAttribute("error", "Invalid credentials!");
                req.getRequestDispatcher("login.jsp").forward(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.jsp");
            res.getWriter().println("login failed. Please try again.");
        }
    }
}
