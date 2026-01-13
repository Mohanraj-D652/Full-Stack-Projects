<%-- 
    Document   : AddCreditCard
    Created on : 27 Dec 2025, 9:15:25 pm
    Author     : arjun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Credit Card</title>
        <link rel="stylesheet" href="css/styleaddcard.css">

    </head>
    <body>
        <h2>Add Credit Card</h2>
        <form action="AddCreditCardurl">
            <label for="CardName">Card Name</label>
            <input type="text" name="CardName" required><br><br>
            
            <label for="CardDigit">Card Last 4 Digit</label>
            <input type="number" name="CardDigit" pattern="[0-9]{4}" maxlength="4"  title="Enter exactly 4 digits (0-9)" required><br><br>

            
            <label for="CardCvv">Card Cvv</label>
            <input type="number" name="CardCvv" minlength="3" maxlength="3" required><br><br>
            
            
            <label for="CardExpiry">Card Expiry</label>
            <input type="text" name="CardExpiry" id="expiryDate" pattern="^(0[1-9]|1[0-2])/[0-9]{2}$" placeholder="Ex: 12/25" maxlength="5" required><br><br>
            
            
            
            
            
            
            
            
            
            <label for="CardLimit">Card Limit</label>
            <input type="number" name="CardLimit" required><br><br>

            <button type="submit" name="submitbutton">Save</button>
            
        </form>
        
        <a href="Mycards.jsp" class="btn-secondary nav-cards">View Cards</a>
        <a href="Dashboard.jsp" class="btn-secondary nav-dashboard">Back</a>

        
    </body>
</html>
