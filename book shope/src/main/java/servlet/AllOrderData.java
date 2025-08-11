
package servlet;

import dao.OrderDAO;
import dto.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class AllOrderData extends HttpServlet {
    
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Order> allOrders = orderDAO.getAllOrders();
            request.setAttribute("allOrders", allOrders);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving paid orders: " + e.getMessage());
        }
        request.getRequestDispatcher("allOrders.jsp").forward(request, response);
    }
}
