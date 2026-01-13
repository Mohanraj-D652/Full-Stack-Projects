package ServletFiles;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.DBConnection;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@WebServlet("/MonthlyReport")
public class MonthlyReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");

        if (userId == null) {
            response.sendRedirect("LoginPage.jsp");
            return;
        }

        String monthYear = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MMMM-yyyy"));

        try (Connection con = DBConnection.getConnection()) {

            // 🔹 GET USER EMAIL
            PreparedStatement eps = con.prepareStatement(
                    "SELECT email FROM users WHERE user_id=?");
            eps.setInt(1, userId);
            ResultSet ers = eps.executeQuery();
            ers.next();
            String userEmail = ers.getString("email");

            // 🔹 CREATE PDF FILE
            String folder = System.getProperty("user.home") + "/CreditCardReports";
            new File(folder).mkdirs();
            String filePath = folder + "/" + userId + "_" + monthYear + ".pdf";

            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            doc.add(new Paragraph("MONTHLY CREDIT CARD REPORT", titleFont));
            doc.add(new Paragraph("User : " + userName));
            doc.add(new Paragraph("Month : " + monthYear));
            doc.add(new Paragraph(" "));

            // 🔹 TABLE STRUCTURE
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 4, 4, 4, 3, 3});

            Font headFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            String[] heads = {"Txn ID", "Card", "Category", "Merchant", "Amount", "Date"};

            for (String h : heads) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // 🔹 FETCH TRANSACTIONS
            PreparedStatement ps = con.prepareStatement(
                "SELECT t.transaction_id, c.card_name, c.card_last4, " +
                "cat.category_name, t.merchant_name, t.amount, t.transaction_date " +
                "FROM transactions t " +
                "JOIN credit_cards c ON t.card_id=c.card_id " +
                "JOIN categories cat ON t.category_id=cat.category_id " +
                "WHERE c.user_id=? AND MONTH(t.transaction_date)=MONTH(CURDATE()) " +
                "AND YEAR(t.transaction_date)=YEAR(CURDATE())");

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            Font rowFont = new Font(Font.FontFamily.HELVETICA, 10);
            double total = 0;

            while (rs.next()) {
                table.addCell(center(rs.getString("transaction_id"), rowFont));
                table.addCell(left(rs.getString("card_name") + " (****" +
                        rs.getString("card_last4") + ")", rowFont));
                table.addCell(center(rs.getString("category_name"), rowFont));
                table.addCell(left(rs.getString("merchant_name"), rowFont));
                table.addCell(right("₹ " + rs.getDouble("amount"), rowFont));
                table.addCell(center(rs.getDate("transaction_date").toString(), rowFont));
                total += rs.getDouble("amount");
            }

            doc.add(table);

            doc.add(new Paragraph("\nTOTAL MONTHLY SPENDING : ₹ " + total,
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));

            doc.close();

            // 🔹 SEND EMAIL
            String from = "arjunmohanraj143@gmail.com";
            String appPass = "cnjiucsmvafkikhq";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session mailSession = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, appPass);
                    }
                });

            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userEmail));
            msg.setSubject("Monthly Spending Report - " + monthYear);

            MimeBodyPart text = new MimeBodyPart();
            text.setText("Hi " + userName +
                    ",\n\nYour monthly credit card report is attached.");

            MimeBodyPart attach = new MimeBodyPart();
            attach.attachFile(new File(filePath));

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.addBodyPart(attach);
            msg.setContent(mp);

            Transport.send(msg);
            response.sendRedirect("Dashboard.jsp?mail=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }
    }

    private PdfPCell center(String text, Font f){
        PdfPCell c = new PdfPCell(new Phrase(text,f));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setPadding(5);
        return c;
    }
    private PdfPCell left(String text, Font f){
        PdfPCell c = new PdfPCell(new Phrase(text,f));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setPadding(5);
        return c;
    }
    private PdfPCell right(String text, Font f){
        PdfPCell c = new PdfPCell(new Phrase(text,f));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setPadding(5);
        return c;
    }
}
