import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class index extends HttpServlet {  // JDK 6 and above only
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
 
      Connection conn = null;
      Statement stmt = null;
      String sqlStr = "";
      int order_id = 0;
      ResultSet rset = null;
      double totalprice = 0.00;

      try {
         // Step 1: Create a database "Connection" object
         // For MySQL
         Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
         conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/onlineshop", "myuser", "12345");  // <<== Check
 
         // Step 2: Create a "Statement" object inside the "Connection"
         stmt = conn.createStatement();
 
            sqlStr = "SELECT * from order_data";
            rset = stmt.executeQuery(sqlStr);
             while(rset.next()) {
            ++order_id;
            }
            
         // Step 3: Execute a SQL SELECT query
         if (Integer.parseInt(request.getParameter("number1")) != 0) {
            order_id = order_id + 1;
            sqlStr = "UPDATE shoes SET qty = qty - " 
            + request.getParameter("number1") +  " where shoes_id = 1001";
            stmt.executeUpdate(sqlStr); // Send the query to the server

            totalprice = Integer.parseInt(request.getParameter("number1")) * 59.99;
            sqlStr = "INSERT into order_data values (" 
            + order_id + ",1001," + request.getParameter("number1") 
            + "," + totalprice + ")";
            stmt.executeUpdate(sqlStr);

            sqlStr = "INSERT into cust_data values (" + "'" + request.getParameter("cust_name")
            + "','" + request.getParameter("cust_email") 
            + "','" + request.getParameter("cust_phone") 
            + "'," + order_id + ")";
            stmt.executeUpdate(sqlStr);
         }

         if (Integer.parseInt(request.getParameter("number2")) != 0) {
            order_id = order_id + 1;
            sqlStr = "UPDATE shoes SET qty = qty - " 
            + request.getParameter("number2") +  " where shoes_id = 1002";
            stmt.executeUpdate(sqlStr); // Send the query to the server

            totalprice = Integer.parseInt(request.getParameter("number2")) * 79.99;
            sqlStr = "INSERT into order_data values (" 
            + order_id + ",1002," + request.getParameter("number2") 
            + "," + totalprice + ")";
            stmt.executeUpdate(sqlStr);

            sqlStr = "INSERT into cust_data values (" + "'" + request.getParameter("cust_name")
            + "','" + request.getParameter("cust_email") 
            + "','" + request.getParameter("cust_phone") 
            + "'," + order_id + ")";
            stmt.executeUpdate(sqlStr);
         }

         if (Integer.parseInt(request.getParameter("number3")) != 0) {
            order_id = order_id + 1;
              sqlStr = "UPDATE shoes SET qty = qty - " 
            + request.getParameter("number3") +  " where shoes_id = 1003";
            stmt.executeUpdate(sqlStr); // Send the query to the server

            totalprice = Integer.parseInt(request.getParameter("number3")) * 89.99;
            sqlStr = "INSERT into order_data values (" 
            + order_id + ",1003," + request.getParameter("number1") 
            + "," + totalprice + ")";
            stmt.executeUpdate(sqlStr);
            
            sqlStr = "INSERT into cust_data values (" + "'" + request.getParameter("cust_name")
            + "','" + request.getParameter("cust_email") 
            + "','" + request.getParameter("cust_phone") 
            + "'," + order_id + ")";
            stmt.executeUpdate(sqlStr);
         }

         // Step 4: Process the query result
         out.println("<h2>Stock Data</h2>");
         sqlStr = "SELECT * from shoes";
         rset = stmt.executeQuery(sqlStr);
         int count = 0;
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each row
            out.println("<p>" + rset.getInt("shoes_id")
                  + ", " + rset.getString("name")
                  + ", " + rset.getInt("qty") 
                  + ", " + rset.getDouble("price") 
                  + "</p>");
            ++count;
         }

         out.println("<h2>Order Data</h2>");
         sqlStr = "SELECT * from order_data";
         rset = stmt.executeQuery(sqlStr);
         count = 0;
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each row
            out.println("<p>" + rset.getInt("order_id")
                  + ", " + rset.getInt("shoes_id")
                  + ", " + rset.getInt("qty") 
                  + ", " + rset.getDouble("amtpaid") 
                  + "</p>");
            ++count;
         }

         out.println("<h2>Customer Data</h2>");
         sqlStr = "SELECT * from cust_data";
         rset = stmt.executeQuery(sqlStr);
         count = 0;
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each row
            out.println("<p>" + rset.getString("name")
                  + ", " + rset.getString("number") 
                  + ", " + rset.getString("email")
                  + ", " + rset.getInt("order_id") 
                  + "</p>");
            ++count;
         }
         out.println("<p>==== " + count + " records found ====</p>");
         out.println("</body></html>");


      } catch (SQLException ex) {
         ex.printStackTrace();
     } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
     } finally {
         out.close();
         try {
            // Step 5: Close the Statement and Connection
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
      }
   }
}