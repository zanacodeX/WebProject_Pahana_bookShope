package servlet;

import controller.LoginController;
import controller.LoginController.LoginResult;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginController loginController = new LoginController();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            LoginResult result = loginController.login(email, password);

            if (result != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", result.getUser());

                if (result.getCustomer() != null) {
                    session.setAttribute("customer", result.getCustomer());
                }

                res.sendRedirect(result.getRedirectPage());
            } else {
                req.setAttribute("error", "Invalid credentials!");
                req.getRequestDispatcher("login.jsp").forward(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.jsp");
        }
    }
}
