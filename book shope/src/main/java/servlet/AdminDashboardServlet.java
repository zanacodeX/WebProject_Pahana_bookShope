package servlet;

import dto.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"admin".equalsIgnoreCase(user.getRole())) {
            res.sendRedirect("unauthorized.jsp");
            return;
        }

        req.getRequestDispatcher("adminDashboard.jsp").forward(req, res);
    }
}
