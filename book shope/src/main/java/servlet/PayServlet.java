package servlet;

import dao.OrderDAO;
import model.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/payOrder")
public class PayServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            orderDAO.updatePaymentStatusToPaid(orderId);
            response.sendRedirect("customerOrderStatus?success=true"); // redirect after update
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerOrderStatus.jsp?error=true");
        }
    }
}
