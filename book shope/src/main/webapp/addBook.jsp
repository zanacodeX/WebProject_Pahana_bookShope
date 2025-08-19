<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Add New Book</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
    }

    h2 {
        text-align: center;
        color: #2c3e50;
        margin-top: 30px;
        font-size: 32px;
    }

    form {
        max-width: 450px;
        margin: 30px auto;
        background: #ffffff;
        padding: 25px 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
       background-color: #e3f2fd;
    }

    label {
        font-weight: bold;
        color: #0d47a1;
        margin-bottom: 5px;
        display: block;
    }

    input[type="text"],
    input[type="number"],
    select {
        display: block;
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border-radius: 6px;
        border: 1px solid #ccc;
        box-sizing: border-box;
        font-size: 14px;
    }

    input[type="submit"] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 12px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 16px;
        transition: 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }

    .back-button {
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }

    .back-button button {
        padding: 10px 20px;
        font-size: 16px;
        background-color: #3498db;
        color: white;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.3s ease;
    }

    .back-button button:hover {
        background-color: #2980b9;
    }
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
        
        <div class="back-button">
    <a href="adminDashboard.jsp" class="back-button">
        <button type="button">Back to Dashboard</button>
    </a>
</div>
    </form>

</body>
</html>