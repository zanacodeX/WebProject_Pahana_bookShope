package servlet;

import controller.CustomerController;
import controller.CustomerController.DashboardResult;
import dto.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CustomerController dashboardController = new CustomerController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        DashboardResult result = dashboardController.getCustomerData(user.getUserId());

        if (result.getCustomer() != null) {
            request.setAttribute("customer", result.getCustomer());
            request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", result.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
