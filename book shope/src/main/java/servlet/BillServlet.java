package servlet;

import dao.OrderDAO;
import dto.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

//@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Order> paidOrders = orderDAO.getAllPaidOrders();
            request.setAttribute("paidOrders", paidOrders);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving paid orders: " + e.getMessage());
        }
        request.getRequestDispatcher("billPayed.jsp").forward(request, response);
    }
}
