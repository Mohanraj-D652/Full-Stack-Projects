<%-- 
    Document   : CardWiseSpending
    Created on : 28 Dec 2025, 11:55:39 am
    Author     : arjun
--%>

<%-- CardSummary.jsp : Total spent per card --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.DBConnection"%>

<%
    // 1) Check login
    Integer userIdObj = (Integer) session.getAttribute("userId");
    if (userIdObj == null) {
        response.sendRedirect("LoginPage.jsp");
        return;
    }
    int userId = userIdObj;

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Card Summary</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 30px; }
        table { width: 60%; border-collapse: collapse; }
        th, td { padding: 10px 12px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background: #f4f4f4; }
        .amount { text-align: right; }
        .lastdigit { text-align: left; }
    </style>
    <link rel="stylesheet" href="css/stylespending.css">
</head>
<body>
<h2>Spending by Card</h2>
<!-- ✅ ADDED WRAPPER -->
<div class="table-wrapper">
    
<table>
    <tr>
        <th>Card Name</th>
        <th class="lastdigit">Last 4 Digit</th>
        <th class="amount">Total Spent</th>
    </tr>
<%
    try {
        con = DBConnection.getConnection();

        String sql =
            "SELECT c.card_name, c.card_last4, c.card_id, " +
            "       SUM(t.amount) AS total_amount " +
            "FROM transactions t " +
            "JOIN credit_cards c ON t.card_id = c.card_id " +
            "WHERE c.user_id = ? " +
            "GROUP BY c.card_id, c.card_name, c.card_last4";

        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
            String cardName = rs.getString("card_name");
            String last4    = rs.getString("card_last4");
            double total    = rs.getDouble("total_amount");
%>
    <tr>
        <td><%= cardName %></td>
        <td>XXXX-XXXX-XXXX-<%= last4 %></td>
        <td class="amount">₹<%= String.format("%.2f", total) %></td>
    </tr>
<%
        }
        if (!hasRows) {
%>
    <tr>
        <td colspan="3">No transactions found for your cards.</td>
    </tr>
<%
        }
    } catch (Exception e) {
%>
    <tr>
        <td colspan="3">Error: <%= e.getMessage() %></td>
    </tr>
<%
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
%>
</table>


<!-- TOP NAV -->
<nav class="main-nav">...</nav>

<!-- CARDS SUMMARY TABLE -->
<table class="spending-table">
    <!-- your card spending rows -->
</table>

</div>
<!-- ✅ WRAPPER END -->

<!-- BOTTOM ACTIONS (EXACTLY like AddCard.jsp) -->
<div class="page-actions">
    <a href="ViewReport.jsp" class="btn-secondary nav-cards">Back</a>
    <a href="Dashboard.jsp" class="btn-secondary nav-dashboard">Dashboard</a>
</div>










</body>
</html>
