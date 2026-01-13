<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.DBConnection"%>

<%
    // Use built-in 'session' (don't redeclare)
    Integer userIdObj = (Integer) session.getAttribute("userId");
    if (userIdObj == null) {
        response.sendRedirect("LoginPage.jsp");
        return;
    }
    int userId = userIdObj;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    boolean hasCards = false;
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Cards</title>
    <style>
        body { font-family: Arial; padding: 20px; background: #f5f5f5; }
        .cards-container { display: flex; flex-wrap: wrap; gap: 16px; }
        .card {
            background: white;
            border-radius: 8px;
            padding: 16px;
            width: 260px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        .card-title { font-weight: bold; margin-bottom: 8px; }
        .card-number { letter-spacing: 2px; margin-bottom: 8px; }
        .card-actions button {
            padding: 6px 10px;
            margin-right: 6px;
            cursor: pointer;
        }
    </style>
    
    <link rel="stylesheet" href="css/stylemycards.css">
    
</head>
<body>
<h2>My Saved Cards</h2>

<div class="cards-container">
<%
    try {
        con = DBConnection.getConnection();
        String cardSql = "SELECT card_id, card_name, card_last4 FROM credit_cards WHERE user_id = ?";
        ps = con.prepareStatement(cardSql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        while (rs.next()) {
            hasCards = true;
            int cardId = rs.getInt("card_id");
            String cardName = rs.getString("card_name");
            String last4 = rs.getString("card_last4");
%>
    <div class="card">
        <div class="card-title"><%= cardName %></div>
        <div class="card-number">XXXX XXXX XXXX <%= last4 %></div>
        <div class="card-actions">
            
            
            

                
                
            <form action="DeleteCardsurl" method="post" style="display:inline;">
                <input type="hidden" name="cardId" value="<%= cardId %>">
                <button type="submit">Remove Card</button>
            </form>
        </div>
    </div>
<%
        }
    } catch(Exception e) {
        out.println("Error: " + e.getMessage());
    } finally {
        if (rs != null) try { rs.close(); } catch(Exception e) {}
        if (ps != null) try { ps.close(); } catch(Exception e) {}
        if (con != null) try { con.close(); } catch(Exception e) {}
    }
%>
</div>

<% if (!hasCards) { %>
<p class="no-cards-msg">No cards saved yet. <a href="AddCreditCard.jsp">&#10133;Add a card</a></p>
<% } %>



<nav class="main-nav">
    <a href="Dashboard.jsp" class="nav-link nav-dashboard">Back</a>
    <a href="AddTransactions.jsp" class="nav-link nav-add-transaction">Add Transaction</a>
    <a href="CatagerywiseSpending.jsp" class="nav-link nav-spending">Spending</a>
    <a href="TransactionHistory.jsp" class="nav-link nav-history">History</a>
    <a href="AddCreditCard.jsp" class="nav-link nav-add-card">+ Add Card</a>
    
</nav>
</body>
</html>
