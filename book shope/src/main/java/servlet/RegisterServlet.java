package servlet;

import controller.RegisterController;
import controller.RegisterController.RegisterResult;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterController registerController = new RegisterController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // If user tries GET, just show registration page
        req.getRequestDispatcher("register.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String accNum = req.getParameter("accountNumber");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");

        RegisterResult result = registerController.registerCustomer(email, password, accNum, name, address, phone);

        if (result.isSuccess()) {
            res.sendRedirect("login.jsp");
        } else {
            req.setAttribute("errorMessage", result.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
