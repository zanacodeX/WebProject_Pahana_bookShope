package servlet;

import dao.OrderDAO;
import dto.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
// JavaMail imports
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class UpdateOrderStatusServlet extends HttpServlet {
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

            // Fetch the updated order to get customer details including email
            Order order = orderDAO.getOrderById(orderId);
            if (order != null) {
                String toEmail = order.getCustomerEmail(); // Make sure Order has getCustomerEmail()
                String customerName = order.getCustomerName();

                String subject = "Your order #" + orderId + " has been " + newStatus ;
                					
               
                
                String body;
                if ("accepted".equalsIgnoreCase(newStatus)) {
                    body = "Dear " + customerName + ",\n\n" +
                           "Your order with ID " + orderId + " has been accepted.\n" +
                           "Please pay the bill for your confirmed order.\n\n" +
                           "Thank you for shopping with us.\n\nBest regards,\nPahana Edu";
                } else {
                    body = "Dear " + customerName + ",\n\n" +
                           "Your order with ID " + orderId + " has been " + newStatus + ".\n" +
                           "Thank you for shopping with us.\n\nBest regards,\nPahana Edu";
                }

                sendEmail(toEmail, subject, body);
            }

            res.sendRedirect("manageBookings.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error updating order.");
        }
    }

    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        final String fromEmail = "pahanabookshope@gmail.com"; // Replace with your email
        final String password = "ilzf vcua zrys tbor"; // Replace with your email password or app password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host for Gmail
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail, "Pahana Edu"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
    }
}
