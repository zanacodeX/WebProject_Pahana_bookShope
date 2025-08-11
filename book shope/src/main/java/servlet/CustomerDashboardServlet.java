package servlet;

import java.io.IOException;

import dao.CustomerDAO;
import dto.Customer;
import dto.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // doGet(request, response); // Forward POST calls to doGet
    

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            CustomerDAO dao = new CustomerDAO();
            Customer customer = dao.getCustomerByUserId(user.getUserId());

            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
            } else {
                response.getWriter().write("No customer data found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error loading customer data.");
        }
    }
}
