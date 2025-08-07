package servlet;

import dao.OrderDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateOrderStatusServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String action = req.getParameter("action");

        String newStatus = "pending";
        if ("confirm".equalsIgnoreCase(action)) {
            newStatus = "accepted";
        } else if ("decline".equalsIgnoreCase(action)) {
            newStatus = "declined";
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.updateOrderStatus(orderId, newStatus);
            res.sendRedirect("manageBookings.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error updating order.");
        }
    }
}
