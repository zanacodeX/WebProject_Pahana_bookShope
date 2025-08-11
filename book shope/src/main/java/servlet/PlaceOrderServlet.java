package servlet;

import dao.OrderDAO;
import dto.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/placeOrder")  // ✅ Very Important
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("customer") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // ✅ Get Customer ID from Session
            dto.Customer customer = (dto.Customer) session.getAttribute("customer");
            int customerId = customer.getCustomerId();

            // ✅ Get Book Details from Form
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            String bookName = request.getParameter("book_name");
            String author = request.getParameter("author");
            double unitPrice = Double.parseDouble(request.getParameter("unit_price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double total = unitPrice * quantity;

            // ✅ Set Order Details
            Order order = new Order();
            order.setCustomerId(customerId);
            order.setBookId(bookId);
            order.setBookName(bookName);
            order.setAuthor(author);
            order.setUnitPrice(unitPrice);
            order.setQuantity(quantity);
            order.setTotal(total);

            // ✅ Save to DB
            OrderDAO dao = new OrderDAO();
            dao.placeOrder(order);

            request.setAttribute("message", "Order placed successfully!");
            request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Order failed: " + e.getMessage());
            request.getRequestDispatcher("placeOrder.jsp").forward(request, response);
        }
    }
}
