package servlet;

import controller.BookController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookController bookController = new BookController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int bookId = Integer.parseInt(idParam);
                boolean deleted = bookController.deleteBook(bookId);
                if (deleted) {
                    response.sendRedirect("manageBooks.jsp?message=Book+deleted+successfully");
                } else {
                    response.sendRedirect("manageBooks.jsp?error=Failed+to+delete+book");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("manageBooks.jsp?error=Invalid+book+ID");
            }
        } else {
            response.sendRedirect("manageBooks.jsp?error=No+book+ID+provided");
        }
    }
}
