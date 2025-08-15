package servlet;

import controller.BookController;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class AddBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookController bookController = new BookController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Read form fields
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            String imageUrl = request.getParameter("imageUrl");

            // Call controller to add book
            BookController.BookResult result = bookController.addBook(name, author, category, price, imageUrl);

            if (result.isSuccess()) {
                response.sendRedirect("manageBooks.jsp");
            } else {
                request.setAttribute("error", result.getMessage());
                request.getRequestDispatcher("addBook.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing book addition: " + e.getMessage());
            request.getRequestDispatcher("addBook.jsp").forward(request, response);
        }
    }
}
