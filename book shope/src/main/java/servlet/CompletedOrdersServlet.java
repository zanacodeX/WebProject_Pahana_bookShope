package servlet;

import dao.CustomerDAO;
import dao.OrderDAO;
import dto.Customer;
import dto.Order;
import dto.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CompletedOrdersServlet extends HttpServlet {

   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            User user = (User) session.getAttribute("user");
            CustomerDAO customerDAO = new CustomerDAO();
            int customerId = customerDAO.getCustomerIdByUserId(user.getUserId());

            OrderDAO orderDAO = new OrderDAO();
            List<Order> completedOrders = orderDAO.getCompletedOrdersByCustomerId(customerId);

            request.setAttribute("completedOrders", completedOrders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/completedOrders.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error fetching completed orders.");
        }
    }
}
