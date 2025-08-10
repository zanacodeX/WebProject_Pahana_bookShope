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
            

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            // Margin and spacing
            float margin = 50;
            float yPosition = pageHeight - margin;

            // Draw logo centered at top
            float logoWidth = 120;
            float logoHeight = 80;
            float logoX = (pageWidth - logoWidth) / 2;
            content.drawImage(logoImage, logoX, yPosition - logoHeight, logoWidth, logoHeight);

            // Shop name - centered below logo
            String shopName = "PAHANA BOOK SHOPE";
            float shopNameFontSize = 24;
            float shopNameWidth = fontBold.getStringWidth(shopName) / 1000 * shopNameFontSize;
            float shopNameX = (pageWidth - shopNameWidth) / 2;
            float shopNameY = yPosition - logoHeight - 30;

            content.beginText();
            content.setFont(fontBold, shopNameFontSize);
            content.newLineAtOffset(shopNameX, shopNameY);
            content.showText(shopName);
            content.endText();

            // Shop address - centered below shop name
            String shopAddress = "Main street, Kuruwita, Rathnapura";
            float shopAddressFontSize = 14;
            float shopAddressWidth = fontRegular.getStringWidth(shopAddress) / 1000 * shopAddressFontSize;
            float shopAddressX = (pageWidth - shopAddressWidth) / 2;
            float shopAddressY = shopNameY - 25;

            content.beginText();
            content.setFont(fontRegular, shopAddressFontSize);
            content.newLineAtOffset(shopAddressX, shopAddressY);
            content.showText(shopAddress);
            content.endText();

            // Title "Book Invoice" centered below shop address
            String title = "Book Invoice";
            float titleFontSize = 22;
            float titleWidth = fontBold.getStringWidth(title) / 1000 * titleFontSize;
            float titleX = (pageWidth - titleWidth) / 2;
            float titleY = shopAddressY - 50;

            content.beginText();
            content.setFont(fontBold, titleFontSize);
            content.newLineAtOffset(titleX, titleY);
            content.showText(title);
            content.endText();

            // Draw rectangle for order details filling more vertical space
            float boxMarginX = margin;
            float boxWidth = pageWidth - 2 * margin;
            float boxMarginTop = titleY - 20;
            float boxHeight = 250;  // taller box to use more space

            content.setLineWidth(1f);
            content.addRect(boxMarginX, boxMarginTop - boxHeight, boxWidth, boxHeight);
            content.stroke();

            // Write order details inside rectangle spaced out vertically
            float textStartXLabel = boxMarginX + 15;
            float textStartXValue = boxMarginX + 180;
            float textY = boxMarginTop - 30;
            float lineSpacing = 35;

            drawLabelValue(content, fontBold, fontRegular, "Order ID:", String.valueOf(order.getId()), textStartXLabel, textStartXValue, textY);
            textY -= lineSpacing;
            drawLabelValue(content, fontBold, fontRegular, "Book Name:", order.getBookName(), textStartXLabel, textStartXValue, textY);
            textY -= lineSpacing;
            drawLabelValue(content, fontBold, fontRegular, "Author:", order.getAuthor(), textStartXLabel, textStartXValue, textY);
            textY -= lineSpacing;
            drawLabelValue(content, fontBold, fontRegular, "Unit Price:", "Rs " + order.getUnitPrice(), textStartXLabel, textStartXValue, textY);
            textY -= lineSpacing;
            drawLabelValue(content, fontBold, fontRegular, "Quantity:", String.valueOf(order.getQuantity()), textStartXLabel, textStartXValue, textY);
            textY -= lineSpacing;
            drawLabelValue(content, fontBold, fontRegular, "Total:", "Rs " + order.getTotal(), textStartXLabel, textStartXValue, textY);

            // Footer message centered at bottom
            String footer = "Thank you for your purchase!";
            float footerFontSize = 12;
            float footerWidth = fontRegular.getStringWidth(footer) / 1000 * footerFontSize;
            float footerX = (pageWidth - footerWidth) / 2;
            float footerY = margin;

            content.beginText();
            content.setFont(fontRegular, footerFontSize);
            content.newLineAtOffset(footerX, footerY);
            content.showText(footer);
            content.endText();

            // Draw "PAID" seal as light watermark if payment status = "paid"
            if (order.getPaymentStatus() != null && order.getPaymentStatus().equalsIgnoreCase("paid")) {
                // Save graphics state
                content.saveGraphicsState();

                // Set transparency to low (e.g., 20%)
                content.setNonStrokingColor(0.2f);

                // Position watermark roughly center of page, big size
                float sealWidth = 200;
                float sealHeight = 100;
                float sealX = (pageWidth - sealWidth) / 2;
             // Place seal just below the order details box (some padding 20)
                float sealY = boxMarginTop - boxHeight - sealHeight - 20;

                content.drawImage(paidSeal, sealX, sealY, sealWidth, sealHeight);


                // Restore graphics state
                content.restoreGraphicsState();
            }

            content.close();
            document.save(response.getOutputStream());
            
        }}
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