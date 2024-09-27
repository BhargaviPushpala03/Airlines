package com.AirlineSystemController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forgot")
public class ForgotServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    private static final String DB_URL = "jdbc:mysql://localhost:3306/project"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Nanna@123"; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        RequestDispatcher dispatcher = null;
        
        if (email != null && !email.trim().isEmpty() && newPassword != null && !newPassword.trim().isEmpty()) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
              
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                
                String hashedPassword = hashPassword(newPassword);
                
             
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                
              
                String sql = "UPDATE register_login SET passKey = ? WHERE email = ?";
                pstmt = conn.prepareStatement(sql);
                
                
                pstmt.setString(1, hashedPassword);
                pstmt.setString(2, email);
                
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                	request.setAttribute("status", "okay");
                	dispatcher = request.getRequestDispatcher("login.jsp");
    				dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("ForgotPassword.jsp"); 
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendRedirect("ForgotPassword.jsp"); 
            } finally {
                
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            response.sendRedirect("ForgotPassword.jsp"); 
        }
    }
    
   
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
