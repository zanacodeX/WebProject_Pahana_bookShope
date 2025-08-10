package servlet;

import dao.OrderDAO;
import dto.Order;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class GenerateBillServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing order ID");
            return;
        }

        int orderId = Integer.parseInt(orderIdParam);
        OrderDAO orderDAO = new OrderDAO();
        Order order = null;
        try {
            order = orderDAO.getOrderById(orderId);
            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        // Set response to PDF content type
        response.setContentType("application/pdf");
        // Suggest download filename
        response.setHeader("Content-Disposition", "inline; filename=Bill_Order_" + orderId + ".pdf");

        // Create PDF document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 20);
                content.newLineAtOffset(50, 750);
                content.showText("Order Bill");
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, 700);
                content.showText("Order ID: " + order.getId());
                content.newLineAtOffset(0, -20);
                content.showText("Book Name: " + order.getBookName());
                content.newLineAtOffset(0, -20);
                content.showText("Author: " + order.getAuthor());
                content.newLineAtOffset(0, -20);
                content.showText("Unit Price: rs." + order.getUnitPrice());
                content.newLineAtOffset(0, -20);
                content.showText("Quantity: " + order.getQuantity());
                content.newLineAtOffset(0, -20);
                content.showText("Total: rs." + order.getTotal());
                content.newLineAtOffset(0, -20);
                content.showText("Order Status: " + order.getOrderStatus());
                content.newLineAtOffset(0, -20);
                content.showText("Payment Status: " + order.getPaymentStatus());
                content.endText();
            }

            // Write PDF to servlet output stream
            document.save(response.getOutputStream());
        }
    }
}
