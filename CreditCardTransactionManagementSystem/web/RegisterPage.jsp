<%-- 
    Document   : RegisterPage
    Created on : 27 Dec 2025, 6:33:09 pm
    Author     : arjun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="css/styleloginpage.css">
    </head>
    <body>
        <form action="RegisterPageurl" method="post">
    <h2 style="text-align:center; color:#3f72af;">Create Account</h2>

    <input type="text" name="UserName" placeholder="Full Name" required>

    <input type="email" name="UserEmail" placeholder="Email Address" required>

    <input type="password" name="UserPassword" placeholder="Password" required>

    <button type="submit" class="btn">Register</button>
</form>
        
     
    
        
        
        
    </body>
</html>
