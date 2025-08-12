package servlet;

import dao.BookDAO;
import dto.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {

  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Book book = bookDAO.getBookById(id);
                request.setAttribute("book", book);
                request.getRequestDispatcher("editBook.jsp").forward(request, response);
            } catch (Exception e) {
                throw new ServletException("Error loading book", e);
            }
        } else {
            response.sendRedirect("manageBooks.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            String imagePath = request.getParameter("imagePath");

            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setAuthor(author);
            book.setCategory(category);
            book.setPrice(price);
            book.setImagePath(imagePath);

            boolean updated = bookDAO.updateBook(book);

            if (updated) {
                response.sendRedirect("manageBooks.jsp");
            } else {
                request.setAttribute("error", "Failed to update book.");
                request.setAttribute("book", book);
                request.getRequestDispatcher("editBook.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error updating book", e);
        }
    }
}
