/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ServletFiles;

import util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;  
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;



/**
 *
 * @author arjun
 */
public class LoginPage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String useremail = (String)request.getParameter("UserEmail");
        String userpassword = (String)request.getParameter("UserPassword");
        
        try (Connection myconnection = DBConnection.getConnection()) {
            
            
            String Search_Query = "select * from users where email = '"+useremail+"' and password = '"+userpassword+"' ";
            
            Statement stmt = myconnection.createStatement();
            ResultSet result = stmt.executeQuery(Search_Query);
            
            if(result.next()){
            
                HttpSession session = request.getSession();
                session.setAttribute("userId", result.getInt("user_id"));
                session.setAttribute("userName", result.getString("full_name"));
                
                response.sendRedirect("Dashboard.jsp");
            
            }
            else {
                 response.sendRedirect("LoginPage.jsp?msg=invalid");
            }
            
            
            myconnection.close();
            
            
            
        }
        catch(Exception ex){
        out.println(ex.getMessage());
        }
            
            
            
            
            
            
            
            
            
            
            
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
