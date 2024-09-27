<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>Reset Password</title>
    <link rel="icon" href="images/logo.jpg" type="image/x-icon">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-image: url('images/Flying.png');
            background-color: #f5f6fa;
            background-size: cover;
            background-position: center;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding-left: 30px;
        }
        .title {
            background-color: #34495e;
            color: #ecf0f1;
            padding: 30px 0;
            width: 100%;
            text-align: center;
            font-size: 28px;
            font-weight: bold;
            position: absolute;
            top: 0;
            left: 0;
        }
        .container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
            width: 90%;
            max-width: 400px;
            text-align: center;
            margin-top: 12%;
            box-sizing: border-box;
        }
        h2 {
            color: #2c3e50;
            font-weight: bold;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            color: #2c3e50;
            font-weight: bold;
        }
        input[type="email"], input[type="password"],input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            box-sizing: border-box;
            background-color: #ecf0f1;
        }
        
        .checkbox {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            font-size: 14px;
        }
        
        /* Style the checkbox to be consistent on different devices */
        .checkbox input[type="checkbox"] {
            margin-right: 10px;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #2980b9;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 10px;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #3498db;
        }
        .options {
            margin-top: 15px;
        }
        .options a {
            text-decoration: none;
            color: #2980b9;
            font-weight: bold;
            margin: 0 10px;
        }
        .options a:hover {
            color: #3498db;
        }

        /* Responsive styles */
        @media (max-width: 600px) {
            .container {
                padding: 20px;
                margin-top: 15%;
            }

            .title {
                font-size: 22px;
                padding: 20px 0;
            }

            input[type="submit"] {
                padding: 12px;
            }

            .options a {
                font-size: 14px;
            }

            .checkbox {
                font-size: 12px;
            }
        }
    </style>
<body>
    <div class="container">
        <h2>Reset Password</h2>
        <form action="ResetPasswordServlet" method="post">
            <label for="password">Enter new password:</label>
            <input type="password" name="password" id="password" required>
            <input type="submit" value="Reset Password">
        </form>
    </div>
</body>
</html>
