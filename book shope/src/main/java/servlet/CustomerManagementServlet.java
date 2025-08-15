package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.CustomerController;
import controller.CustomerController.ManagementResult;
import dto.Customer;

@WebServlet("/CustomerManagementServlet")
public class CustomerManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CustomerController controller = new CustomerController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        try {
            if ("delete".equalsIgnoreCase(action) && idParam != null) {
                int id = Integer.parseInt(idParam);
                ManagementResult result = controller.deleteCustomer(id);
                request.setAttribute("message", result.getMessage());
                response.sendRedirect("manageCustomers.jsp");
                return;

            } else if ("edit".equalsIgnoreCase(action) && idParam != null) {
                // Redirect to edit page (implement editCustomer.jsp separately)
                response.sendRedirect("editCustomer.jsp?id=" + idParam);
                return;
            }

            // Default: list all customers
            ManagementResult result = controller.getAllCustomers();
            List<Customer> customers = result.getCustomers();
            request.setAttribute("customers", customers);
            if (!result.isSuccess()) {
                request.setAttribute("error", result.getMessage());
            }

            request.getRequestDispatcher("manageCustomers.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
