<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>Booking Confirmation</title>
    <link rel="icon" href="images/logo.jpg" type="image/x-icon">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .confirmation-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 90%;
            width: 500px;
            text-align: center;
            font-size: 20px;
        }
        h1 {
            color: #2196F3; /* Changed to blue */
            font-size: 30px;
        }
        p {
            font-size: 20px;
            margin: 10px 0;
        }
        .total-amount {
            font-size: 25px;
            color: #333;
            font-weight: bold;
        }
        .li {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 20px;
            background-color: #2196F3; /* Changed to blue */
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .li:hover {
            background-color: #1976D2; /* Changed to blue */
        }
        .print-button {
            display: inline-block;
            margin-top: 10px;
            padding: 6px 16px;
            background-color: #4CAF50; /* Green color for print button */
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .print-button:hover {
            background-color: #45a049; /* Darker green on hover */
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .confirmation-container {
                padding: 15px;
                font-size: 18px;
            }
            h1 {
                font-size: 24px;
            }
            p {
                font-size: 18px;
            }
            .total-amount {
                font-size: 22px;
            }
        }

        @media (max-width: 480px) {
            .confirmation-container {
                padding: 10px;
                font-size: 16px;
            }
            h1 {
                font-size: 20px;
            }
            p {
                font-size: 16px;
            }
            .total-amount {
                font-size: 18px;
            }
            .li {
                padding: 8px 16px;
                font-size: 14px;
            }
            .print-button {
                padding: 8px 16px;
                font-size: 14px;
            }
        }

        /* Style for printable content */
        @media print {
            .no-print {
                display: none; /* Hides elements marked as no-print */
            }
            .print-content {
                display: block; /* Shows only the print content */
            }
        }
    </style>
    <script>
        function printTicket() {
            // Create a new window for printing
            var printWindow = window.open('', '_blank');
            printWindow.document.write(`
                <html>
                <head>
                   
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            text-align: center;
                        }
                        h1 {
                            color: #2196F3; /* Blue */
                        }
                        p {
                            font-size: 20px;
                        }
                        .total-amount {
                            font-size: 25px;
                            color: #333;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                    <h1>Booking Confirmed!</h1>
                    <p>Thank you for booking your flight.</p>
                    <p><strong>Passenger Name:</strong> ${param.passenger_name}</p>
                    <p><strong>Source City:</strong> ${param.source_city}</p>
                    <p><strong>Destination City:</strong> ${param.destination_city}</p>
                    <p><strong>Fare Type:</strong> ${param.fareType}</p>
                    <p><strong>Number of Tickets:</strong> ${param.numTickets}</p>
                    <p class="total-amount"><strong>Total Amount:</strong> $${param.totalAmount}</p>
                </body>
                </html>
            `);
            printWindow.document.close(); // Close the document for writing
            printWindow.print(); // Open the print dialog
            printWindow.close(); // Close the print window after printing
        }
    </script>
</head>
<body>
    <%@ include file="menu.jsp" %>
    <div class="confirmation-container">
        <h1>Booking Confirmed!</h1>
        <p>Thank you for booking your flight.</p>
        <p><strong>Passenger Name:</strong> ${param.passenger_name}</p>
        <p><strong>Source City:</strong> ${param.source_city}</p>
        <p><strong>Destination City:</strong> ${param.destination_city}</p>
        <p><strong>Fare Type:</strong> ${param.fareType}</p>
        <p><strong>Number of Tickets:</strong> ${param.numTickets}</p>
        <p class="total-amount"><strong>Total Amount:</strong> $${param.totalAmount}</p>
        <a href="index.jsp" class="li no-print">Back To Book Flight</a>
        <br>
        <a href="javascript:void(0);" class="print-button no-print" onclick="printTicket()">Download Ticket</a>
    </div>
</body>
</html>
