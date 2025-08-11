<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<style>
    body {
        margin: 0;
        padding: 0;
        font-family: 'Roboto', sans-serif;
        height: 100vh;
        background: linear-gradient(rgba(0,0,0,0.4), rgba(0,0,0,0.4)), 
                    url("<%= request.getContextPath() %>/image/icon.jpeg") no-repeat center center;
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
    }

    .close-btn {
        position: absolute;
        top: 20px;
        right: 30px;
        font-size: 22px;
        color: white;
        cursor: pointer;
        background-color: rgba(0,0,0,0.5);
        padding: 5px 10px;
        border-radius: 50%;
        text-decoration: none;
    }

    form {
        background-color: rgba(255, 255, 255, 0.9);
        padding: 30px 40px;
        border-radius: 12px;
        box-shadow: 0 8px 16px rgba(0,0,0,0.3);
        width: 320px;
        position: relative;
    }
   
    
    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 12px;
        margin: 8px 0 20px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
    }

    .password-container {
        position: relative;
    }

    .toggle-password {
        position: absolute;
        top: 12px;
        right: 12px;
        cursor: pointer;
        color: #888;
    }

    input[type="submit"] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 12px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        font-weight: bold;
        transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }

    .register-link {
        margin-top: 15px;
        text-align: center;
        font-size: 14px;
    }

    .register-link a {
        color: #4a90e2;
        text-decoration: none;
        font-weight: bold;
    }

    .register-link a:hover {
        text-decoration: underline;
    }
</style>
</head>

<body>

    <!-- Close button to go back -->
    

    <form action="login" method="post">
    <a href="landing.jsp" class="close-btn" title="Go to Home">✖</a>
        <h2 style="text-align:center; margin-bottom: 20px; color:#333;">Login</h2>

        Email:
        <input type="text" name="email" required>

        Password:
        <div class="password-container">
            <input type="password" name="password" id="passwordField" required>
            <i class="fa-solid fa-eye toggle-password" id="togglePassword"></i>
        </div>

        <input type="submit" value="Login">

        <div class="register-link">
            Don't have an account? <a href="register.jsp">Register</a>
        </div>
    </form>

    <script>
        const togglePassword = document.getElementById("togglePassword");
        const passwordField = document.getElementById("passwordField");

        togglePassword.addEventListener("click", () => {
            const type = passwordField.getAttribute("type") === "password" ? "text" : "password";
            passwordField.setAttribute("type", type);
            togglePassword.classList.toggle("fa-eye");
            togglePassword.classList.toggle("fa-eye-slash");
        });
    </script>

</body>
</html>
