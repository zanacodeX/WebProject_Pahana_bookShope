package servlet;

import dao.CustomerDAO;
import dto.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerManagementServlet")
public class CustomerManagementServlet extends HttpServlet {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        try {
        	if ("delete".equalsIgnoreCase(action) && idParam != null) {
        	    int id = Integer.parseInt(idParam);
        	    boolean deleted = customerDAO.deleteCustomerAndUser(id);  // Use new method here
        	    if (deleted) {
        	        request.setAttribute("message", "Customer and user deleted successfully.");
        	    } else {
        	        request.setAttribute("error", "Failed to delete customer and user.");
        	    }
        	    response.sendRedirect("manageCustomers.jsp");
        	    return;
        	
            } else if ("edit".equalsIgnoreCase(action) && idParam != null) {
                // Redirect to edit page (implement editCustomer.jsp separately)
                response.sendRedirect("editCustomer.jsp?id=" + idParam);
                return;
            }

            // Default: list all customers
            List<Customer> customers = customerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("manageCustomers.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
