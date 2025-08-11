<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Add New Book</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        form { max-width: 400px; margin: auto; }
        label, input, select { display: block; margin-bottom: 10px; width: 100%; }
        input[type="submit"] { background: green; color: white; padding: 10px; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Add New Book</h2>
    
    <form action="addBook" method="post" enctype="multipart/form-data">
        <label>Book Name:</label>
        <input type="text" name="name" required>

        <label>Author:</label>
        <input type="text" name="author" required>

        <label>Category:</label><br>
<input type="text" name="category" required>

        <label>Price:</label>
        <input type="number" name="price" step="0.01" required>

        <label>Book Image:</label>
       <input type="text" name="imageUrl" placeholder="Enter image URL" required />

        <input type="submit" value="Add Book">
    </form>

</body>
</html>