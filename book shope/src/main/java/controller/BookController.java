package controller;

import java.util.List;

import dao.BookDAO;
import dto.Book;

public class BookController {

    private final BookDAO bookDAO;

    public BookController() {
        this.bookDAO = new BookDAO();
    }

    // Result class for unified response
    public static class BookResult {
        private final Book book;
        private final String message;
        private final boolean success;

        public BookResult(Book book, String message, boolean success) {
            this.book = book;
            this.message = message;
            this.success = success;
        }

        public Book getBook() {
            return book;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }
    
    public boolean deleteBook(int bookId) {
        try {
            bookDAO.deleteBook(bookId); // call DAO method
            return true;                 // deleted successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;                // failed to delete
        }
    }
    
    // Fetch single book
    public Book getBookById(int bookId) {
        try {
            return bookDAO.getBookById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update Book
    public boolean updateBook(Book book) {
        try {
            return bookDAO.updateBook(book);  // updateBook in DAO should return boolean
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all books or search
    public List<Book> getAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> searchBooks(String searchTerm) {
        try {
            return bookDAO.searchBooks(searchTerm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Add a new book
    public BookResult addBook(String name, String author, String category, double price, String imagePath) {
        try {
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setCategory(category);
            book.setPrice(price);
            book.setImagePath(imagePath);

            bookDAO.insertBook(book);
            return new BookResult(book, "Book added successfully.", true);

        } catch (Exception e) {
            e.printStackTrace();
            return new BookResult(null, "Failed to add book: " + e.getMessage(), false);
        }
    }

    // Optionally, you can add more methods like updateBook, deleteBook, getAllBooks here
}
