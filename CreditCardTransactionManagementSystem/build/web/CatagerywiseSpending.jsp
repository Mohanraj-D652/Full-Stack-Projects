<%-- CategorywiseSpending.jsp --%>
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

    // 2) First pass: find maximum total per category
    double maxTotal = 0.0;
    try {
        con = DBConnection.getConnection();
        String baseSql =
            "SELECT cat.category_name, SUM(t.amount) AS total_amount " +
            "FROM transactions t " +
            "JOIN categories cat ON t.category_id = cat.category_id " +
            "JOIN credit_cards c ON t.card_id = c.card_id " +
            "WHERE c.user_id = ? " +
            "GROUP BY cat.category_id, cat.category_name";

        ps = con.prepareStatement(baseSql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();
        while (rs.next()) {
            double total = rs.getDouble("total_amount");
            if (total > maxTotal) {
                maxTotal = total;
            }
        }
        rs.close();
        ps.close();
    } catch (Exception e) {
        // keep maxTotal = 0 on error
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Category-wise Spending</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background: #fafafa;
        }
        h2 {
            margin-bottom: 8px;
        }
        .note {
            font-size: 13px;
            color: #b00020;
            font-weight: bold;
            margin-bottom: 16px;
        }
        table {
            width: 70%;
            border-collapse: collapse;
            background: #ffffff;
            border-radius: 4px;
            overflow: hidden;
        }
        th, td {
            padding: 10px 14px;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            text-align: left;
            background: #f4f4f4;
        }
        .category-col {
            width: 40%;
        }
        .alert-col {
            width: 35%;
            font-size: 13px;
            color: #555;
        }
        .amount-col {
            width: 25%;
            text-align: right;
        }
        .high-text {
            color: #b00020;
            font-weight: bold;
        }
    </style>
    <link rel="stylesheet" href="css/stylespending.css">
</head>
<body>

<h2>Category wise Spending</h2>
<p class="note">
    Alert: The category with the highest spending is highlighted in red.
</p>
<div class="table-wrapper">
<table>
    <tr>
        <th class="category-col">Category</th>
        <th class="alert-col">Alert</th>
        <th class="amount-col">Total</th>
    </tr>

<%
    try {
        // 3) Second pass: fetch rows again and mark the highest one
        String sql =
            "SELECT cat.category_name, SUM(t.amount) AS total_amount " +
            "FROM transactions t " +
            "JOIN categories cat ON t.category_id = cat.category_id " +
            "JOIN credit_cards c ON t.card_id = c.card_id " +
            "WHERE c.user_id = ? " +
            "GROUP BY cat.category_id, cat.category_name " +
            "ORDER BY cat.category_name";

        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
            String cname = rs.getString("category_name");
            double total = rs.getDouble("total_amount");

            boolean isHighest = (total == maxTotal && maxTotal > 0);
%>
    <tr>
        <!-- Category column -->
        <td class="category-col">
            <% if (isHighest) { %>
                <span class="high-text"><%= cname %></span>
            <% } else { %>
                <%= cname %>
            <% } %>
        </td>

        <!-- Alert column -->
        <td class="alert-col">
            <% if (isHighest) { %>
                <span class="high-text">Highest spending category!</span>
            <% } %>
        </td>

        <!-- Total column (right aligned, red only for highest) -->
        <td class="amount-col">
            <% if (isHighest) { %>
                <span class="high-text">
                    ₹<%= String.format("%.2f", total) %>
                </span>
            <% } else { %>
                ₹<%= String.format("%.2f", total) %>
            <% } %>
        </td>
    </tr>
<%
        }
        if (!hasRows) {
%>
    <tr>
        <td colspan="3">No transactions found.</td>
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

</div>




<div class="page-actions">
    <a href="ViewReport.jsp" class="btn-secondary nav-history">Back</a>
    <a href="Dashboard.jsp" class="btn-secondary nav-dashboard">Dashboard</a>
</div>














</body>
</html>
