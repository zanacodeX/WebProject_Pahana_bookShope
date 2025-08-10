package servlet;

import dao.OrderDAO;
import dto.Order;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.File;
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
     // --- Professional PDF generation replacing old code ---
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Load fonts (make sure arial.ttf is placed correctly)
            File fontFile = new File(getServletContext().getRealPath("/font/ARIAL.TTF"));
            PDType0Font fontRegular = PDType0Font.load(document, fontFile);
            PDType0Font fontBold = PDType0Font.load(document, fontFile);

            // Load images (put logo.png and paid.png in /WEB-INF/classes/images/)
            PDImageXObject logoImage = PDImageXObject.createFromFile(
                    getServletContext().getRealPath("/image/icon.jpeg"), document);
            PDImageXObject paidSeal = PDImageXObject.createFromFile(
                    getServletContext().getRealPath("/image/paid.png"), document);

            PDPageContentStream content = new PDPageContentStream(document, page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;

            // Draw logo top-left
            content.drawImage(logoImage, margin, yStart - 60, 80, 50);

            // Company name
            content.beginText();
            content.setFont(fontBold, 20);
            content.newLineAtOffset(margin + 90, yStart - 20);
            content.showText("PAHANA BOOK SHOPE");
            content.endText();

            // Company address
            content.beginText();
            content.setFont(fontRegular, 12);
            content.newLineAtOffset(margin + 90, yStart - 40);
            content.showText("Main street, Kuruwita, Rathnapura");
            content.endText();

            // Title - centered
            String title = "Book Invoice";
            float titleWidth = fontBold.getStringWidth(title) / 1000 * 18;
            float centerX = (page.getMediaBox().getWidth() - titleWidth) / 2;

            content.beginText();
            content.setFont(fontBold, 18);
            content.newLineAtOffset(centerX, yStart - 100);
            content.showText(title);
            content.endText();

            // Draw rectangle around order details
            float boxY = yStart - 140;
            content.setLineWidth(1f);
            content.addRect(margin, boxY - 150, page.getMediaBox().getWidth() - 2 * margin, 150);
            content.stroke();

            // Draw order details inside the rectangle
            float textY = boxY - 20;
            float labelX = margin + 10;
            float valueX = margin + 150;

            drawLabelValue(content, fontBold, fontRegular, "Order ID:", String.valueOf(order.getId()), labelX, valueX, textY);
            textY -= 20;
            drawLabelValue(content, fontBold, fontRegular, "Book Name:", order.getBookName(), labelX, valueX, textY);
            textY -= 20;
            drawLabelValue(content, fontBold, fontRegular, "Author:", order.getAuthor(), labelX, valueX, textY);
            textY -= 20;
            drawLabelValue(content, fontBold, fontRegular, "Unit Price:", "rs" + order.getUnitPrice(), labelX, valueX, textY);
            textY -= 20;
            drawLabelValue(content, fontBold, fontRegular, "Quantity:", String.valueOf(order.getQuantity()), labelX, valueX, textY);
            textY -= 20;
            drawLabelValue(content, fontBold, fontRegular, "Total:", "rs" + order.getTotal(), labelX, valueX, textY);

            // Footer message
            content.beginText();
            content.setFont(fontRegular, 10);
            content.newLineAtOffset(margin, 50);
            content.showText("Thank you for your purchase!");
            content.endText();

            // Show "PAID" seal only if payment status is "paid" (case-insensitive)
            if (order.getPaymentStatus() != null && order.getPaymentStatus().equalsIgnoreCase("paid")) {
                float sealX = page.getMediaBox().getWidth() - 200;
                float sealY = 200;
                content.drawImage(paidSeal, sealX, sealY, 120, 60);
            }

            content.close();
            document.save(response.getOutputStream());
        }
    }

    private void drawLabelValue(PDPageContentStream content, PDType0Font bold, PDType0Font regular,
                                String label, String value, float labelX, float valueX, float y) throws IOException {
        content.beginText();
        content.setFont(bold, 12);
        content.newLineAtOffset(labelX, y);
        content.showText(label);
        content.endText();

        content.beginText();
        content.setFont(regular, 12);
        content.newLineAtOffset(valueX, y);
        content.showText(value);
        content.endText();
    }
}