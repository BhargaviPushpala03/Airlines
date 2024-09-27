package com.AirlineSystemController;

import java.io.IOException;
//import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/project?allowPublicKeyRetrieval=true&useSSL=false"; 
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Nanna@123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the form data from the request
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contact = request.getParameter("contact");
        String userType = "user"; 

        Connection conn = null;
        PreparedStatement pstmt = null;
        RequestDispatcher dispatcher = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");


            String hashedPassword = hashPassword(password);
            System.out.println(hashedPassword);
           
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            
            String query1="select * from register_login where email=?";
            pstmt=conn.prepareStatement(query1);
            pstmt.setString(1, email);
            ResultSet res=pstmt.executeQuery();
            dispatcher = request.getRequestDispatcher("register.jsp");
            System.out.println(res.getFetchSize());
            if(res.next()) {
            	request.setAttribute("status", "Exist");
            	dispatcher.forward(request, response);
            	return ;
            }
            
            String sql = "INSERT INTO register_login (first_name, last_name, email, PassKey, contact, user_type,otp,otp_expiration) VALUES (?, ?, ?, ?, ?, ?,0,NOW())";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, hashedPassword); 
            pstmt.setString(5, contact);
            pstmt.setString(6, userType);

            
            int rows = pstmt.executeUpdate();
            
            response.setContentType("text/html");
            if(rows > 0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        } finally {
           
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes); 
    }
}