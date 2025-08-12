
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.Book;
import model.DBConnection;

public class BookDAO {
	
	public List<Book> searchBooks(String keyword) throws Exception {
        List<Book> books = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM books WHERE name LIKE ? OR author LIKE ?"
        );
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Book book = new Book(
            		rs.getInt("id"),
            	    rs.getString("name"),
            	    rs.getString("author"),
            	    rs.getString("category"),
            	    rs.getDouble("price"),
            	              // ✅ add this
            	    rs.getString("image_path")
            );
            books.add(book);
        }

        return books;
    }

    public void addBook(Book book) throws Exception {
        String sql = "INSERT INTO books (name, author, price,category, image_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3,book.getCategory());
           
            ps.setDouble(4, book.getPrice());
            ps.setString(5, book.getImagePath());
          
            
            ps.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(
                		rs.getInt("id"),
                	    rs.getString("name"),
                	    rs.getString("author"),
                	    rs.getString("category"),
                	    rs.getDouble("price"),
                	                // ✅ add this
                	    rs.getString("image_path")
                );
                books.add(book);
            }
        }
        return books;
    }

    public Book getBookByID(int id) throws Exception {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                    		rs.getInt("id"),
                    	    rs.getString("name"),
                    	    rs.getString("author"),
                    	    rs.getString("category"),
                    	    rs.getDouble("price"),
                    	                // ✅ add this
                    	    rs.getString("image_path")
                    );
                }
            }
        }
        return null;
    }

    public void updateBooks(Book book) throws Exception {
        String sql = "UPDATE books SET name=?, author=?, price=?, image_path=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getImagePath());
            ps.setInt(5, book.getId());
            ps.executeUpdate();
        }
        
        
    }
    
    //update part
    
    public Book getBookById(int id) throws Exception {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getDouble("price"));
                book.setImagePath(rs.getString("image_path"));
                return book;
            }
        }
        return null;
    }

    public boolean updateBook(Book book) throws Exception {
        String sql = "UPDATE books SET name=?, author=?, category=?, price=?, image_path=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDouble(4, book.getPrice());
            ps.setString(5, book.getImagePath());
            ps.setInt(6, book.getId());
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<String> getAllCategories() throws Exception {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM books";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }
    
    public List<Book> getBooksByCategory(String category) throws Exception {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(rs.getString("category"));
                book.setImagePath(rs.getString("image_path"));
                list.add(book);
            }
        }
        return list;
    }

    public void insertBook(Book book) {
        String sql = "INSERT INTO books (name, author, category, price, image_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDouble(4, book.getPrice());
            ps.setString(5, book.getImagePath());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    public void deleteBook(int id) throws Exception {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
