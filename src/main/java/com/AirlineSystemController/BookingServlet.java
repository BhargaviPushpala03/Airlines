package com.AirlineSystemController;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookFlightServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fareType = request.getParameter("fareType");
        int numTickets = Integer.parseInt(request.getParameter("numTickets"));
        String flighttype = request.getParameter("flight_type");
        String sourcecity = request.getParameter("source_city");
        String destinationcity = request.getParameter("destination_city");
       
        
        HttpSession session = request.getSession();
        
        int fare = 0;
        String flightId = request.getParameter("flightId");
        System.out.println("book: "+flightId);
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "Nanna@123");

            
            String query = "SELECT * FROM flight WHERE flightId = ? and cast(departure_time as DATE)>= CURDATE()";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if ("Business".equals(fareType)) {
                    fare = rs.getInt("business_fare");
                } else if ("Economy".equals(fareType)) {
                    fare = rs.getInt("economy_fare");
                }
            }

            
            int totalAmount = fare * numTickets;
            
            
            int availableSeats = rs.getInt("seats_Available");
            if(availableSeats>=numTickets) {
            	String query1 = "Insert into flight_bookings(flight_id,passenger_name,Seat_class,No_of_Passengers,flight_date,Total_amount,flight_type,source_city,destination_city,email) values(?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt1 = con.prepareStatement(query1);
                stmt1.setString(1,flightId);
                stmt1.setString(2,session.getAttribute("name").toString());
                stmt1.setString(3, fareType);
                stmt1.setInt(4, numTickets);
                stmt1.setDate(5, rs.getDate("departure_time"));
                stmt1.setDouble(6, totalAmount);
                stmt1.setString(7, flighttype);
                stmt1.setString(8, sourcecity);
                stmt1.setString(9, destinationcity);
                stmt1.setString(10,(String) session.getAttribute("email"));
                
                
                int res = stmt1.executeUpdate();
                if(res>0) {
                	request.setAttribute("fareType", fareType);
                    request.setAttribute("numTickets", numTickets);
                    request.setAttribute("totalAmount", totalAmount);
                    request.setAttribute("passenger_name",session.getAttribute("name").toString());
                    request.setAttribute("source_city", sourcecity);
                    request.setAttribute("destination_city", destinationcity);
                }
            	String updateQuery = "Update flight SET seats_Available = seats_Available-? Where flightId = ?";
            	PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            	updateStmt.setInt(1, numTickets);
            	updateStmt.setString(2, flightId);
            	updateStmt.executeUpdate();
            	updateStmt.close();
            	stmt1.close();
            }
            else {
            	request.setAttribute("errorMessage", "Not enough seats Available");
            	request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            
                response.sendRedirect("BookingConfirm.jsp?fareType=" + URLEncoder.encode(fareType, "UTF-8") +
                    "&numTickets=" + numTickets +
                    "&totalAmount=" + totalAmount +
                    "&passenger_name=" + URLEncoder.encode(session.getAttribute("name").toString(), "UTF-8") +
                    "&source_city=" + URLEncoder.encode(sourcecity, "UTF-8") +
                    "&destination_city=" + URLEncoder.encode(destinationcity, "UTF-8"));
            

                      	
            


           
            rs.close();
            
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}