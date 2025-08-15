package servlet;

import controller.CustomerController;
import controller.CustomerController.EditResult;
import dto.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CustomerController controller = new CustomerController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                EditResult result = controller.getCustomerById(id);

                if (result.isSuccess()) {
                    request.setAttribute("customer", result.getCustomer());
                    request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", result.getMessage());
                    response.sendRedirect("manageCustomers.jsp");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("manageCustomers.jsp");
            }
        } else {
            response.sendRedirect("manageCustomers.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Customer customer = new Customer();
            customer.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
            customer.setAccountNumber(request.getParameter("accountNumber"));
            customer.setName(request.getParameter("name"));
            customer.setAddress(request.getParameter("address"));
            customer.setPhone(request.getParameter("phone"));
            customer.setUnitsConsumed(Integer.parseInt(request.getParameter("unitsConsumed")));

            EditResult result = controller.updateCustomer(customer);

            if (result.isSuccess()) {
                response.sendRedirect("manageCustomers.jsp");
            } else {
                request.setAttribute("error", result.getMessage());
                request.setAttribute("customer", result.getCustomer());
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
