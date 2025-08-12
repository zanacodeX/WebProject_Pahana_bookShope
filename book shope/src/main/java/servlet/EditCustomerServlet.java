
package servlet;

import dao.CustomerDAO;
import dto.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Customer customer = customerDAO.getCustomerById(id);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            response.sendRedirect("manageCustomers.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("customerId"));
            String accountNumber = request.getParameter("accountNumber");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            int unitsConsumed = Integer.parseInt(request.getParameter("unitsConsumed"));

            Customer customer = new Customer();
            customer.setCustomerId(id);
            customer.setAccountNumber(accountNumber);
            customer.setName(name);
            customer.setAddress(address);
            customer.setPhone(phone);
            customer.setUnitsConsumed(unitsConsumed);

            boolean updated = customerDAO.updateCustomer(customer);
            if (updated) {
                response.sendRedirect("manageCustomers.jsp");
            } else {
                request.setAttribute("error", "Failed to update customer.");
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
