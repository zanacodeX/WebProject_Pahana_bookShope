package servlet;

import dao.BookDAO;
import dto.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

//@WebServlet("/landing")
public class LandingServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    BookDAO bookDAO = new BookDAO();

	    try {
            String category = request.getParameter("category");
            List<Book> books;

            if (category != null && !category.trim().isEmpty()) {
                books = bookDAO.getBooksByCategory(category); // You must implement this method in BookDAO
            } else {
                books = bookDAO.getAllBooks();
            }

            request.setAttribute("books", books);
            request.setAttribute("selectedCategory", category);
            request.getRequestDispatcher("landing.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}